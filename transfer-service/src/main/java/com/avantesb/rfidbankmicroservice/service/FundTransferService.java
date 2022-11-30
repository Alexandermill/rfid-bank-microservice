package com.avantesb.rfidbankmicroservice.service;

import com.avantesb.rfidbankmicroservice.model.TransactionStatus;
import com.avantesb.rfidbankmicroservice.model.dto.FundTransfer;
import com.avantesb.rfidbankmicroservice.model.dto.request.FundTransferRequest;
import com.avantesb.rfidbankmicroservice.model.dto.response.FundTransferResponse;
import com.avantesb.rfidbankmicroservice.model.entity.FundTransferEntity;
import com.avantesb.rfidbankmicroservice.model.mapper.FundTransferMapper;
import com.avantesb.rfidbankmicroservice.model.repository.FundTransferEntityRepository;
import com.avantesb.rfidbankmicroservice.service.client.CoreFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FundTransferService {

    private final FundTransferEntityRepository fundTransferRepository;
    private final CoreFeignClient coreFeignClient;
    private final FundTransferMapper fundTransferMapper;

    public FundTransferResponse fundTransfer(FundTransferRequest request){

        log.info("Sending fund transfer request {}" + request.toString());

        FundTransferEntity entity = new FundTransferEntity();
        BeanUtils.copyProperties(request, entity);
        entity.setStatus(TransactionStatus.PENDING);
        FundTransferEntity optFundTransfer = fundTransferRepository.save(entity);

        FundTransferResponse fundTransferResponse = coreFeignClient.fundTransfer(request);

        if(fundTransferResponse.getTransactionId() == null){
            optFundTransfer.setStatus(TransactionStatus.FAILED);
        } else {
            optFundTransfer.setTransactionReference(fundTransferResponse.getTransactionId());
            optFundTransfer.setStatus(TransactionStatus.SUCCESS);
            fundTransferResponse.setMessage("Fund Transfer Successfully Completed");
        }

        fundTransferRepository.save(optFundTransfer);


        return fundTransferResponse;

    }

    public List<FundTransfer> readTransactions(Pageable pageable){
        return fundTransferMapper.convertToDtoList(fundTransferRepository.findAll(pageable).getContent());
    }



}
