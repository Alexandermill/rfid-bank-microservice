package com.avantesb.rfidbankmicroservice.controller;

import com.avantesb.rfidbankmicroservice.model.dto.request.TransferRequest;
import com.avantesb.rfidbankmicroservice.service.FundTransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/transfer")
public class TransferController {

    private final FundTransferService fundTransferService;
    private final StreamBridge streamBridge;

    @PostMapping
    public ResponseEntity fundTransfer(@RequestBody TransferRequest request){
        log.info("Got fund transfer request from API {}", request.toString());

        return ResponseEntity.ok(fundTransferService.initFundTransfer(request));
    }

    @GetMapping
    public ResponseEntity readTransactions(Pageable pageable){
        log.info("Reading fund transfers from core");
        return ResponseEntity.ok(fundTransferService.readTransactions(pageable));
    }

    @GetMapping("{id}")
    public ResponseEntity getTransferByID(@PathVariable("id") Long id){
        return ResponseEntity.ok(fundTransferService.readTransferById(id));
    }

    @PostMapping("/test")
    public String testTransfer(
            @RequestParam("transferId") Long transferId,
            @RequestParam("from") String from,
            @RequestParam("to") String to,
            @RequestParam("ammount") BigDecimal ammount,
            @RequestParam("day") int day,
            @RequestParam("hour") int hour,
            @RequestParam("key") String ikey
            ){
        String hash = "" + from + to + ammount;

        String keyString = ""+hash.hashCode()+day+hour;
        String key = UUID.nameUUIDFromBytes(keyString.getBytes()).toString();

        if(ikey != null && !ikey.isEmpty()) key = ikey;


        TransferRequest request = new TransferRequest(transferId, from, to, ammount);
        streamBridge.send("sendTransfer-out-0", MessageBuilder
                .withPayload(request)
                .setHeader("Idempotency-Key", key)
                .build());

        return "send";
    }

}
