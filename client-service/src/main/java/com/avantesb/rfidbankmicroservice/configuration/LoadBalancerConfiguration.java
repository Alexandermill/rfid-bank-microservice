package com.avantesb.rfidbankmicroservice.configuration;

import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

public class LoadBalancerConfiguration {

    @Bean
    public ServiceInstanceListSupplier instanceSupplier(ConfigurableApplicationContext context) {
        return ServiceInstanceListSupplier.builder()
                .withCaching()
                .withBlockingDiscoveryClient()
                .build(context);
    }

//    @Bean
//    public ServiceInstanceListSupplier supplier(DiscoveryClient discoveryClient){
//        return new ServiceInstanceListSupplier() {
//            @Override
//            public String getServiceId() {
//                return discoveryClient.getInstances("client-bank-service").get(0).getServiceId();
//            }
//
//            @Override
//            public Flux<List<ServiceInstance>> get() {
//                return Flux.just(discoveryClient.getInstances("account-bank-service"));
//            }
//        };
//    }

}
