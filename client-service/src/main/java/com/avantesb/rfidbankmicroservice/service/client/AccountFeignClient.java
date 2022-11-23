package com.avantesb.rfidbankmicroservice.service.client;

import com.avantesb.rfidbankmicroservice.configuration.CustomFeignClientConfiguration;
import com.avantesb.rfidbankmicroservice.model.dto.AccountBank;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "transferClient", url = "${account-banking-service}", configuration = CustomFeignClientConfiguration.class)
public interface AccountFeignClient {

    @RequestMapping(path = "/api/v1/accounts/clientid/{clientId}", method = RequestMethod.GET)
    List<AccountBank> getAccountsByClient(@PathVariable("clientId") Long clientId);
}
