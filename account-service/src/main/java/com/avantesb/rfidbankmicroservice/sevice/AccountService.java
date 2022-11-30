package com.avantesb.rfidbankmicroservice.sevice;

import com.avantesb.rfidbankmicroservice.model.dto.AccountBank;
import com.avantesb.rfidbankmicroservice.model.dto.UtilAccountBank;

import java.util.List;


public interface AccountService {

    AccountBank readAccount(String accountNumber);
    UtilAccountBank readUtilAccountByProviderName(String providerName);
    UtilAccountBank readUtilAccountById(Long id);
    List<AccountBank> getAccountsByClientId(Long clientId);
    List<AccountBank> getAccountsByClientId(List<Long> clientIds);
}
