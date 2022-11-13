package com.avantesb.rfidbankmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class InternetBankingFundTransferServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(InternetBankingFundTransferServiceApplication.class, args);
    }
}
