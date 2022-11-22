package com.avantesb.rfidbankmicroservice.model.mapper;

import com.avantesb.rfidbankmicroservice.model.dto.UtilAccountBank;
import com.avantesb.rfidbankmicroservice.model.entity.UtilityAccountEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class UtilAccountMapper extends BaseMapper<UtilityAccountEntity, UtilAccountBank>{

    @Override
    public UtilityAccountEntity convertToEntity(UtilAccountBank dto, Object... args) {
        UtilityAccountEntity entity = new UtilityAccountEntity();
        if(dto != null){
            BeanUtils.copyProperties(entity, dto);
        }
        return entity;

    }

    @Override
    public UtilAccountBank convertToDto(UtilityAccountEntity entity, Object... args) {
        UtilAccountBank dto = new UtilAccountBank();
        if(entity != null){
            BeanUtils.copyProperties(entity, dto);
        }
        return dto;

    }


}
