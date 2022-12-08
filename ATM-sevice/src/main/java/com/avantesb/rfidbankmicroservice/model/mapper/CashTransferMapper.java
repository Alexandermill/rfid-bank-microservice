package com.avantesb.rfidbankmicroservice.model.mapper;

import com.avantesb.rfidbankmicroservice.model.dto.CashTransferDTO;
import com.avantesb.rfidbankmicroservice.model.entity.CashTransferEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CashTransferMapper extends BaseMapper<CashTransferEntity, CashTransferDTO> {


    @Override
    public CashTransferEntity convertToEntity(CashTransferDTO dto, Object... args) {
        CashTransferEntity entity = new CashTransferEntity();
        if(dto != null){
            BeanUtils.copyProperties(dto, entity);
        }

        return entity;
    }

    @Override
    public CashTransferDTO convertToDto(CashTransferEntity entity, Object... args) {

        CashTransferDTO dto = new CashTransferDTO();
        if(entity != null){
            BeanUtils.copyProperties(entity, dto);
        }
        return dto;
    }
}
