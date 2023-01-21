package com.avantesb.rfidbankmicroservice.service;

import com.avantesb.rfidbankmicroservice.model.TransactionStatus;
import com.avantesb.rfidbankmicroservice.model.dto.FundTransfer;
import com.avantesb.rfidbankmicroservice.model.dto.request.TransferRequest;
import com.avantesb.rfidbankmicroservice.model.dto.response.RestTransferResponse;
import com.avantesb.rfidbankmicroservice.model.dto.response.TransferResponse;
import com.avantesb.rfidbankmicroservice.model.entity.TransferEntity;
import com.avantesb.rfidbankmicroservice.model.mapper.TransferMapper;
import com.avantesb.rfidbankmicroservice.model.repository.TransferEntityRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FundTransferService {

    @Value("${myhost.name}")
    private String host;

    @Value("${server.port}")
    private String port;

    private final TransferEntityRepository fundTransferRepository;
    private final StreamBridge streamBridge;
    private final TransferMapper transferMapper;

    public RestTransferResponse initFundTransfer(TransferRequest request){

        TransferEntity entity = new TransferEntity();
        BeanUtils.copyProperties(request, entity);
        entity.setStatus(TransactionStatus.PENDING);
        entity.setTransferId(UUID.randomUUID());
        TransferEntity savedFundTransfer = fundTransferRepository.save(entity);
        request.setTransferId(savedFundTransfer.getTransferId().toString());

        Boolean send = streamBridge.send("sendTransfer-out-0", MessageBuilder
                    .withPayload(request)
                    .setHeader("Idempotency-Key", idempotencyKey(request))
                    .build());

        log.info("Sending fund transfer request ID: {}, status: {}", request.getTransferId(), send);

        return RestTransferResponse.builder().transferId(savedFundTransfer.getTransferId().toString())
                .message("STATUS: PENDING")
                .link("http://"+host+":"+port+"/api/v1/transfer/"+savedFundTransfer.getId())
                .build();

    }

    public String idempotencyKey(@NotNull TransferRequest request){
        String hash = ""
                + request.getTransferId()
                + request.getFromAccount()
                + request.getToAccount()
                + request.getAmmount();

        LocalDateTime time = LocalDateTime.now();
        int day = time.getDayOfYear();
        int hour = time.getHour();
        int minute = time.getMinute();

        String keyString = ""+hash.hashCode()+day+hour;
        UUID key = UUID.nameUUIDFromBytes(keyString.getBytes());
        System.out.println("request ID: "+request.getTransferId()+ " key: "+ key);
        return key.toString();
    }

    public void updateTransfer(TransferResponse response){

        log.info("Update Transfer ID: {} message: {} transaction ID: {}", response.getTransferId(),
                response.getMessage(),
                response.getTransactionId());

        TransferEntity updatedEntity = fundTransferRepository.findByTransferId(response.getTransferId())
                .orElseThrow(EntityNotFoundException::new);

        updatedEntity.setTransactionReference(response.getTransactionId());
        updatedEntity.setStatus(TransactionStatus.SUCCESS);

        if(response.getTransactionId() == null){
            updatedEntity.setStatus(TransactionStatus.FAILED);
        }

        fundTransferRepository.save(updatedEntity);
    }

    public List<FundTransfer> readTransactions(Pageable pageable){
        return transferMapper.convertToDtoList(fundTransferRepository.findAll(pageable).getContent());
    }


    public FundTransfer readTransferById(Long id) {
        return transferMapper.convertToDto(fundTransferRepository.findById(id).orElse(new TransferEntity()));
    }
}
