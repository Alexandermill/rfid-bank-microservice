package com.avantesb.rfidbankmicroservice.model.mapper;

import com.avantesb.rfidbankmicroservice.model.dto.FundTransfer;
import com.avantesb.rfidbankmicroservice.model.entity.TransferEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class TransferMapper extends BaseMapper<TransferEntity, FundTransfer>{


    @Override
    public TransferEntity convertToEntity(FundTransfer dto, Object... args) {

        TransferEntity entity = new TransferEntity();
        if(dto != null){
            BeanUtils.copyProperties(dto, entity);
        }
        return entity;
    }

    @Override
    public FundTransfer convertToDto(TransferEntity entity, Object... args) {

        FundTransfer dto = new FundTransfer();
        if(entity != null){
            BeanUtils.copyProperties(entity, dto);
        }
        return dto;
    }
}
