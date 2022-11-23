package com.avantesb.rfidbankmicroservice.controller;

import com.avantesb.rfidbankmicroservice.sevice.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/{account_number}")
    public ResponseEntity getAccountbank(@PathVariable("account_number") String accountNumber){
        log.info("Reading account by ID {}", accountNumber);
        return ResponseEntity.ok(accountService.readAccount(accountNumber));
    }

    @GetMapping("/clientid/{clientId}")
    public ResponseEntity getAccountsById(@PathVariable("clientId") Long clientId){
        log.info("Reading accounts by client_ID {}", clientId);
        return ResponseEntity.ok(accountService.getAccountsByClientId(clientId));
    }



    @GetMapping("/util-account/{account_name}")
    public ResponseEntity getUtilityAccount(@PathVariable("account_name") String providerName) {
        log.info("Reading utility account by ID {}", providerName);
        return ResponseEntity.ok(accountService.readUtilAccountByProviderName(providerName));
    }


}
