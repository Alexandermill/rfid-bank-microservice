package com.avantesb.rfidbankmicroservice;

import com.avantesb.rfidbankmicroservice.model.dto.AccountBank;
import com.avantesb.rfidbankmicroservice.sevice.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.function.Function;

@SpringBootApplication
@EnableDiscoveryClient
public class AccountServiceApp {

    public static void main(String[] args) {

        SpringApplication.run(AccountServiceApp.class, args);
        
    }

    @Autowired
    AccountService accountService;

    @Bean
    Function<List<Long>, List<AccountBank>> getAccounts(){
        return input -> {
            System.out.println("ids: "+ input);
            System.out.println("accounts: " + accountService.getAccountsByClientId(input));
            return accountService.getAccountsByClientId(input);

        };
    }
    
}
