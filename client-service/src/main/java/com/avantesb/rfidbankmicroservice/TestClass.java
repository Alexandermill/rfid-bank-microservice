package com.avantesb.rfidbankmicroservice;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.stereotype.Component;

@Component
public class TestClass {
    @Autowired
    DiscoveryClient discoveryClient;



    void ddd(){
        System.out.println("ddd");
    }

    public void dClient(){
        System.out.println(discoveryClient.getServices()+"\n"
                + discoveryClient.description()+"\n"
                +discoveryClient.getInstances("account-bank-service").get(0).getUri()+"\n"
                +discoveryClient.getInstances("account-bank-service").get(0).getUri().getPort()+"\n"
                +discoveryClient.getOrder());

    }




}
