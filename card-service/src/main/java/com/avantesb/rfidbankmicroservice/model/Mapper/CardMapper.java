package com.avantesb.rfidbankmicroservice.model.Mapper;

import com.avantesb.rfidbankmicroservice.model.dto.CardDTO;
import com.avantesb.rfidbankmicroservice.model.entity.CardEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CardMapper extends BaseMapper<CardEntity, CardDTO> {
    @Override
    public CardEntity convertToEntity(CardDTO dto, Object... args) {
        CardEntity entity = new CardEntity();
        if(dto != null){
            BeanUtils.copyProperties(dto, entity);
        }
        return entity;
    }

    @Override
    public CardDTO convertToDto(CardEntity entity, Object... args) {
        CardDTO dto = new CardDTO();
        if(entity != null){
            BeanUtils.copyProperties(entity, dto);
        }
        return dto;
    }
}
