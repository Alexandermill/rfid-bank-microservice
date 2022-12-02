package com.avantesb.rfidbankmicroservice.model.mapper;

import com.avantesb.rfidbankmicroservice.model.dto.ClientWithAccountDTO;
import com.avantesb.rfidbankmicroservice.model.entity.AccountReferenceEntity;
import com.avantesb.rfidbankmicroservice.model.entity.ClientBankEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ClientWithAccounsMapper extends BaseMapper<ClientBankEntity, ClientWithAccountDTO>{

//    private final AccountFeignClient accountFeignClient;

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

        }
        return dto;
    }



}
