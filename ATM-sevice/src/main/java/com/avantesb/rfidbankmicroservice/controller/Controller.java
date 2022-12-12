package com.avantesb.rfidbankmicroservice.controller;

import com.avantesb.rfidbankmicroservice.model.dto.request.CashTransferRequest;
import com.avantesb.rfidbankmicroservice.service.CashTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/atm")
public class Controller {

    private final CashTransferService cashTransferService;

    @Autowired
    public Controller(CashTransferService cashTransferService) {
        this.cashTransferService = cashTransferService;
    }

    @GetMapping()
    public ResponseEntity getAtms(Pageable pageable){
        return ResponseEntity.ok("d");

    }
    public ResponseEntity atmToAmmountTransfer(){

        return ResponseEntity.ok("");
    }

    @PostMapping("/deposit")
    public ResponseEntity shutUpAndTakeMyMoney(@RequestBody CashTransferRequest request){


        return ResponseEntity.ok(cashTransferService.cash(request));
    }

    @PostMapping("/withdrawal")
    public ResponseEntity shutUpAndGiveMyMoney(@RequestBody CashTransferRequest request){
        request.setAmmount(request.getAmmount().negate());

        return ResponseEntity.ok(cashTransferService.cash(request));
    }


}
