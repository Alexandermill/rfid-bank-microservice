package com.avantesb.rfidbankmicroservice.service.client;

import com.avantesb.rfidbankmicroservice.configuration.LoadBalancerConfiguration;
import com.avantesb.rfidbankmicroservice.model.dto.AccountBank;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
@FeignClient("account-bank-service")
@LoadBalancerClient(value = "account-bank-service", configuration = LoadBalancerConfiguration.class)
public interface AccountClient {

    @RequestMapping(value = "/api/v1/accounts/clientid/{clientId}", method = RequestMethod.GET)
    List<AccountBank> getAccountsByClient(@PathVariable("clientId") Long clientId);

    @RequestMapping(value = "/api/v1/accounts/clients", method = RequestMethod.POST, produces = "application/json")
    List<AccountBank> getAccountsByClientNew(@RequestBody List<Long> clientIds);
}
