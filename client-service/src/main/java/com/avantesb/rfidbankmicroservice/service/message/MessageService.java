package com.avantesb.rfidbankmicroservice.service.message;

import com.avantesb.rfidbankmicroservice.model.dto.AccountBank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Component
public class MessageService {

    @Autowired
    StreamBridge streamBridge;

    BlockingQueue<AccountBank> queue = new ArrayBlockingQueue(1000);


    @Bean
    public Consumer<List<AccountBank>> getAccount(){
        return accounts -> {
            accounts.forEach(a -> {
                try {
                    queue.put(a);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });

        };
    }

    public List<AccountBank> getAccountsQueue(){

        List<AccountBank> accountBanks = new ArrayList<>();

        for (int i = 0; i < queue.remainingCapacity(); i++) {
            try {
                accountBanks.add(queue.take());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (queue.isEmpty()){
                break;
            }
        }
        return accountBanks;
    }

    public void sendClientIdsToAccountService(List<Long> ids){
        streamBridge.send("getAccounts-out-0", MessageBuilder.withPayload(ids).build());
    }
}
