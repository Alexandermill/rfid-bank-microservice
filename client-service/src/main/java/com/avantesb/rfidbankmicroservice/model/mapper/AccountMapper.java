package com.avantesb.rfidbankmicroservice.model.mapper;

import com.avantesb.rfidbankmicroservice.model.dto.AccountBank;
import com.avantesb.rfidbankmicroservice.model.entity.AccountEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper extends BaseMapper<AccountEntity, AccountBank> {


    @Override
    public AccountEntity convertToEntity(AccountBank dto, Object... args) {
        AccountEntity entity = new AccountEntity();

        if(dto != null){
            BeanUtils.copyProperties(dto, entity, "client");
        }
        return entity;
    }

    @Override
    public AccountBank convertToDto(AccountEntity entity, Object... args) {
        AccountBank dto = new AccountBank();

        if(entity != null){
            BeanUtils.copyProperties(entity, dto, "client");
        }
        return dto;

    }
}
