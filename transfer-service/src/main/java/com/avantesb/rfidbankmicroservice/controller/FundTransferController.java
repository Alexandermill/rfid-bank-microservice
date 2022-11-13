package com.avantesb.rfidbankmicroservice.controller;

import com.avantesb.rfidbankmicroservice.model.dto.request.FundTransferRequest;
import com.avantesb.rfidbankmicroservice.service.FundTransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/transfer")
public class FundTransferController {

    private final FundTransferService fundTransferService;

    @PostMapping
    public ResponseEntity fundTransfer(@RequestBody FundTransferRequest request){
        log.info("Got fund transfer request from API {}", request.toString());

        return ResponseEntity.ok(fundTransferService.fundTransfer(request));
    }

    @GetMapping
    public ResponseEntity readTransactions(Pageable pageable){
        log.info("Reading fund transfers from core");
        return ResponseEntity.ok(fundTransferService.readTransactions(pageable));
    }

}
