package com.avantesb.rfidbankmicroservice.service;

import com.avantesb.rfidbankmicroservice.model.TransactionStatus;
import com.avantesb.rfidbankmicroservice.model.dto.request.CardTransferRequest;
import com.avantesb.rfidbankmicroservice.model.dto.request.CashTransferRequest;
import com.avantesb.rfidbankmicroservice.model.dto.response.CardTransferResponse;
import com.avantesb.rfidbankmicroservice.model.entity.CardEntity;
import com.avantesb.rfidbankmicroservice.model.entity.CardTransfer;
import com.avantesb.rfidbankmicroservice.model.repo.CardRepository;
import com.avantesb.rfidbankmicroservice.model.repo.CardTransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final CardTransactionRepository cardTransactionRepository;

    public Boolean cardExist(String cardNumber) {

        return cardRepository.existsCardEntityByCardNumber(cardNumber);
    }

    public CardTransferRequest cardTransfer(CashTransferRequest request) {

        CardEntity card = cardRepository.findByCardNumber(request.getCardNumber())
                .orElseThrow(() -> {
                    log.info("Card {} in CashTransferRequest ID: {} not found", request.getCardNumber(), request.getTransferId());
                    return new EntityNotFoundException("card not found");
                });

        CardTransfer cardTransfer = new CardTransfer();
        BeanUtils.copyProperties(request, cardTransfer);
        cardTransfer.setAccountNumber(card.getAccountNumber());
        cardTransfer.setStatus(TransactionStatus.PENDING); // потом доделать обновление статуса

        CardTransfer cardTransferInDB = cardTransactionRepository.save(cardTransfer);
        log.info("Created Transfer ID: {} card: {} ammount: {}",
                cardTransferInDB.getTransferId(),
                cardTransferInDB.getCardNumber(),
                cardTransferInDB.getAmmount());

        return CardTransferRequest.builder()
                .accountNumber(cardTransferInDB.getAccountNumber())
                .transferId(cardTransferInDB.getTransferId())
                .ammount(cardTransferInDB.getAmmount())
                .build();
    }

    public void updateTransfer(CardTransferResponse response) {

        log.info("Update Transfer ID: {} message: {} transaction ID: {}", response.getTransferId(),
                response.getMessage(),
                response.getTransactionId());

        CardTransfer updatedEntity = cardTransactionRepository.findById(response.getTransferId())
                .orElseThrow(EntityNotFoundException::new);

        updatedEntity.setReferenceId(response.getTransactionId());
        updatedEntity.setStatus(TransactionStatus.SUCCESS);

        if (response.getTransactionId() == null) {
            updatedEntity.setStatus(TransactionStatus.FAILED);
        }

        cardTransactionRepository.save(updatedEntity);
    }


}
