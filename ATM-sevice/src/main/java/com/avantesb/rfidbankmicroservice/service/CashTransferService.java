package com.avantesb.rfidbankmicroservice.service;

import com.avantesb.rfidbankmicroservice.model.TransactionStatus;
import com.avantesb.rfidbankmicroservice.model.dto.request.CashTransferRequest;
import com.avantesb.rfidbankmicroservice.model.dto.response.CashTransferResponse;
import com.avantesb.rfidbankmicroservice.model.dto.response.RestCashTransferResponse;
import com.avantesb.rfidbankmicroservice.model.entity.CashTransferEntity;
import com.avantesb.rfidbankmicroservice.model.repo.CashTransferRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class CashTransferService {

    @Value("${myhost.name}")
    private String host;

    @Value("${server.port}")
    private String port;

    private final CashTransferRepository cashTransferRepository;
    private final StreamBridge streamBridge;

    public CashTransferService(CashTransferRepository cashTransferRepository, StreamBridge streamBridge) {
        this.cashTransferRepository = cashTransferRepository;
        this.streamBridge = streamBridge;
    }

    public RestCashTransferResponse cash(CashTransferRequest request){

        CashTransferEntity entity = new CashTransferEntity();
        entity.setAmmount(request.getAmmount());
        entity.setStatus(TransactionStatus.PENDING);

        CashTransferEntity cashTransfer = cashTransferRepository.save(entity);
        request.setTransferId(cashTransfer.getId());

        streamBridge.send("sendTransfer-out-0", MessageBuilder
                .withPayload(request)
                .setHeader("Idempotency-Key", idempotencyKey(request))
                .build());


        return RestCashTransferResponse.builder()
                .transferId(request.getTransferId())
                .message("STATUS: PENDING")
                .link("http://"+host+":"+port+"/api/v1/transfer/"+cashTransfer.getId())
                .build();
    }

    public void updateTransfer(CashTransferResponse response){

        log.info("Update Transfer ID: {} message: {} transaction ID: {}", response.getTransferId(),
                response.getMessage(),
                response.getTransactionId());

        CashTransferEntity updatedEntity = cashTransferRepository.findById(response.getTransferId())
                .orElseThrow(EntityNotFoundException::new);

        updatedEntity.setTransactionReference(response.getTransactionId());
        updatedEntity.setStatus(TransactionStatus.SUCCESS);

        if(response.getTransactionId() == null){
            updatedEntity.setStatus(TransactionStatus.FAILED);
        }

        cashTransferRepository.save(updatedEntity);
    }

    public String idempotencyKey(@NotNull CashTransferRequest request){
        String hash = ""
                + request.getTransferId()
                + request.getAmmount();

        LocalDateTime time = LocalDateTime.now();

        String keyString = ""+hash.hashCode() + time.getDayOfYear() + time.getHour();
        UUID key = UUID.nameUUIDFromBytes(keyString.getBytes());
        System.out.println("request ID: "+request.getTransferId()+ " key: "+ key);
        return key.toString();
    }

}
