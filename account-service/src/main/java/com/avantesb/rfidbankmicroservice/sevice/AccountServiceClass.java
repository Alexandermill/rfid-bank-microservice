package com.avantesb.rfidbankmicroservice.sevice;

import com.avantesb.rfidbankmicroservice.exceptions.EntityNotFoundException;
import com.avantesb.rfidbankmicroservice.model.dto.AccountBank;
import com.avantesb.rfidbankmicroservice.model.dto.UtilAccountBank;
import com.avantesb.rfidbankmicroservice.model.entity.AccountEntity;
import com.avantesb.rfidbankmicroservice.model.entity.UtilityAccountEntity;
import com.avantesb.rfidbankmicroservice.model.mapper.AccountMapper;
import com.avantesb.rfidbankmicroservice.model.mapper.UtilAccountMapper;
import com.avantesb.rfidbankmicroservice.model.repository.AccountEntityRepository;
import com.avantesb.rfidbankmicroservice.model.repository.UtilityAccountEntityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccountServiceClass {

    private AccountMapper accountMapper;
    private UtilAccountMapper utilAccountMapper;

    private final AccountEntityRepository accountRepository;
    private final UtilityAccountEntityRepository utilAccountRepository;

    public AccountBank readAccount(String accountNumber){
        AccountEntity accountEntity = accountRepository.findByNumber(accountNumber).orElseThrow(EntityNotFoundException::new);
        return accountMapper.convertToDto(accountEntity);
    }

    public UtilAccountBank readUtilAccountByProviderName(String providerName){
        UtilityAccountEntity utilityAccountEntity = utilAccountRepository.findByProviderName(providerName)
                .orElseThrow(EntityNotFoundException::new);
        return utilAccountMapper.convertToDto(utilityAccountEntity);
    }

    public UtilAccountBank readUtilAccountById(Long id){
        UtilityAccountEntity utilityAccountEntity = utilAccountRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return utilAccountMapper.convertToDto(utilityAccountEntity);
    }


    public List<AccountBank> getAccountsByClientId(Long clientId) {
        return accountMapper.convertToDtoList(accountRepository.findByClientId(clientId));
    }

    public List<AccountBank> getAccountsByClientId(List<Long> clientIds) {
        List<AccountEntity> entities = clientIds.stream()
                .map(accountRepository::findByClientId).toList()
                    .stream()
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());

        return  accountMapper.convertToDtoList(entities);
    }
}