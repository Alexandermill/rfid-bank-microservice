package com.avantesb.rfidbankmicroservice.controller;

import com.avantesb.rfidbankmicroservice.model.request.FundTransferRequest;
import com.avantesb.rfidbankmicroservice.model.request.UtilityPaymentRequest;
import com.avantesb.rfidbankmicroservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/fund-transfer")
    public ResponseEntity fundTransfer(@RequestBody FundTransferRequest request){

        log.info("Fund transfer initiated in core bank from {}", request.toString());

        return ResponseEntity.ok(transactionService.fundTransfer(request));

    }

    @PostMapping("/util-payment")
    public ResponseEntity utilPayment(@RequestBody UtilityPaymentRequest utilityPaymentRequest) {

        log.info("Utility transfer initiated in core bank from {}", utilityPaymentRequest.toString());

        return ResponseEntity.ok(transactionService.utilPayment(utilityPaymentRequest));
    }

}
