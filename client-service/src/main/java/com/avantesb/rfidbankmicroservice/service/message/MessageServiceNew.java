package com.avantesb.rfidbankmicroservice.service.message;

import com.avantesb.rfidbankmicroservice.model.dto.AccountBank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;

@Component
public class MessageServiceNew {

    @Autowired
    StreamBridge streamBridge;

    BlockingQueue<List<AccountBank>> queue = new ArrayBlockingQueue(100);


    @Bean
    public Consumer<List<AccountBank>> getAccount(){
        return accounts -> {
            try {
                queue.clear();
                queue.put(accounts);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("queue: size:"+ queue.size() +" "+ queue);
        };
    }

    public List<AccountBank> getAccountsQueue(){

        List<AccountBank> accountBanks;

            try {
                accountBanks = new ArrayList<>(queue.take());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        return accountBanks;
    }

   
    public void sendClientIdsToAccountService(List<Long> ids){
        streamBridge.send("sendClientID-out-0", MessageBuilder.withPayload(ids).build());
    }
}
