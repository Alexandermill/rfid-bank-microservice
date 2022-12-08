package com.avantesb.rfidbankmicroservice.model.mapper;

import com.avantesb.rfidbankmicroservice.model.dto.AtmDTO;
import com.avantesb.rfidbankmicroservice.model.entity.AtmEntity;
import org.springframework.beans.BeanUtils;

public class AtmMapper extends BaseMapper<AtmEntity, AtmDTO>{

    @Override
    public AtmEntity convertToEntity(AtmDTO dto, Object... args) {

        AtmEntity entity = new AtmEntity();
        if(dto != null){
            BeanUtils.copyProperties(dto, entity);
        }
        return entity;
    }

    @Override
    public AtmDTO convertToDto(AtmEntity entity, Object... args) {

        AtmDTO dto = new AtmDTO();

        if(entity != null){
            BeanUtils.copyProperties(entity, dto);
        }
        return dto;
    }
}
