package com.avantesb.rfidbankmicroservice.service;

import com.avantesb.rfidbankmicroservice.model.TransactionStatus;
import com.avantesb.rfidbankmicroservice.model.dto.FundTransfer;
import com.avantesb.rfidbankmicroservice.model.dto.request.TransferRequest;
import com.avantesb.rfidbankmicroservice.model.dto.response.TransferResponse;
import com.avantesb.rfidbankmicroservice.model.dto.response.RestTransferResponse;
import com.avantesb.rfidbankmicroservice.model.entity.TransferEntity;
import com.avantesb.rfidbankmicroservice.model.mapper.TransferMapper;
import com.avantesb.rfidbankmicroservice.model.repository.TransferEntityRepository;
import com.avantesb.rfidbankmicroservice.service.client.CoreFeignClient;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FundTransferService {

    @Value("${myhost.name}")
    private String host;

    @Value("${server.port}")
    private String port;

    private final TransferEntityRepository fundTransferRepository;
    private final CoreFeignClient coreFeignClient;
    private final StreamBridge streamBridge;
    private final TransferMapper transferMapper;

    public RestTransferResponse initFundTransfer(TransferRequest request){



        TransferEntity entity = new TransferEntity();
        BeanUtils.copyProperties(request, entity);
        entity.setStatus(TransactionStatus.PENDING);
        TransferEntity optFundTransfer = fundTransferRepository.save(entity);
        request.setTransferId(optFundTransfer.getId());

        Boolean send = streamBridge.send("sendTransfer-out-0", MessageBuilder.withPayload(request).build());

        log.info("Sending fund transfer request {}, status: {}" + request.toString(), send);

        return RestTransferResponse.builder().transferId(optFundTransfer.getId())
                .message("STATUS: PENDING")
                .link("http://"+host+":"+port+"/api/v1/transfer/"+optFundTransfer.getId())
                .build();

//        return FundTransferResponse.builder().message("Fund Transfer Pending")
//                .transferId(optFundTransfer.getId())
//                .build();

    }

    public void updateTransfer(TransferResponse response){

        log.info("Transfer ID: {} message: {} transaction ID: {}", response.getTransferId(),
                response.getMessage(),
                response.getTransactionId());

        TransferEntity updatedEntity = fundTransferRepository.findById(response.getTransferId())
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
