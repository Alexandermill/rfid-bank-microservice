package com.avantesb.rfidbankmicroservice.service.client;

import com.avantesb.rfidbankmicroservice.configuration.CustomFeignClientConfiguration;
import com.avantesb.rfidbankmicroservice.model.dto.request.FundTransferRequest;
import com.avantesb.rfidbankmicroservice.model.dto.response.FundTransferResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "transferClient", url = "${account-banking-service}", configuration = CustomFeignClientConfiguration.class)
public interface CoreFeignClient {

//    @RequestMapping(path = "/api/v1/account/bank-account/{account_number}", method = RequestMethod.GET)
//    AccountResponse readAccount(@PathVariable("account_number") String accountNumber);

    @RequestMapping(path = "/api/v1/transaction/fund-transfer", method = RequestMethod.POST)
    FundTransferResponse fundTransfer(@RequestBody FundTransferRequest request);
}
