package com.avantesb.rfidbankmicroservice.model.mapper;

import com.avantesb.rfidbankmicroservice.model.dto.AccountBank;
import com.avantesb.rfidbankmicroservice.model.dto.ClientBank;
import com.avantesb.rfidbankmicroservice.model.dto.ClientWithAccountDTO;
import com.avantesb.rfidbankmicroservice.model.entity.AccountReferenceEntity;
import com.avantesb.rfidbankmicroservice.model.entity.ClientBankEntity;
import com.avantesb.rfidbankmicroservice.service.client.AccountFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ClientWithAccounsMapper extends BaseMapper<ClientBankEntity, ClientWithAccountDTO>{

    AccountFeignClient accountFeignClient;

    @Override
    public ClientBankEntity convertToEntity(ClientWithAccountDTO dto, Object... args) {
        ClientBankEntity entity = new ClientBankEntity();
        if(dto != null){
            BeanUtils.copyProperties(dto, entity);
            entity.setAccountsId(dto.getAccounts().stream()
                    .map(n -> new AccountReferenceEntity(n.getNumber(), entity))
                    .collect(Collectors.toList())
            );
        }

        return entity;
    }



    @Override
    public ClientWithAccountDTO convertToDto(ClientBankEntity entity, Object... args) {
        ClientWithAccountDTO dto = new ClientWithAccountDTO();


        if(entity != null){
            BeanUtils.copyProperties(entity, dto);
            List<AccountBank> accounts = accountFeignClient.getAccountsByClient(entity.getId());

        }
        return dto;
    }



}
