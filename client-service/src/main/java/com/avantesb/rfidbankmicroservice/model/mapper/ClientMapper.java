package com.avantesb.rfidbankmicroservice.model.mapper;

import com.avantesb.rfidbankmicroservice.model.dto.ClientBank;
import com.avantesb.rfidbankmicroservice.model.entity.AccountReferenceEntity;
import com.avantesb.rfidbankmicroservice.model.entity.ClientBankEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ClientMapper extends BaseMapper<ClientBankEntity, ClientBank>{



    @Override
    public ClientBankEntity convertToEntity(ClientBank dto, Object... args) {
        ClientBankEntity entity = new ClientBankEntity();
        if(dto != null){
            BeanUtils.copyProperties(dto, entity);
            entity.setAccountsId(dto.getAccountNumbers().stream()
                    .map(n -> new AccountReferenceEntity(n, entity))
                    .collect(Collectors.toList())
            );
        }

        return entity;
    }

    @Override
    public ClientBank convertToDto(ClientBankEntity entity, Object... args) {
        ClientBank dto = new ClientBank();


        if(entity != null){
            BeanUtils.copyProperties(entity, dto);
            dto.setAccountNumbers(entity.getAccountsId().stream().map(a -> a.getNumber())
                    .collect(Collectors.toList()));

        }
        return dto;
    }
}
