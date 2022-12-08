package com.avantesb.rfidbankmicroservice.controller;

import com.avantesb.rfidbankmicroservice.model.request.CashTransferRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/atm")
public class Controller {

    @GetMapping()
    public ResponseEntity getAtms(Pageable pageable){
        return ResponseEntity.ok("d");
    }

    public ResponseEntity atmToAmmountTransfer(){
        return ResponseEntity.ok("");
    }

    public ResponseEntity shutUpAndTakeMyMoney(@RequestBody CashTransferRequest request){
        return ResponseEntity.ok("");
    }

    public ResponseEntity shutUpAndGiveMyMoney(@RequestBody CashTransferRequest request){
        return ResponseEntity.ok("");
    }


}
