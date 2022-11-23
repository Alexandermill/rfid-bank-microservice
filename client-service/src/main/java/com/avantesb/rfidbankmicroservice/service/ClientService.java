package com.avantesb.rfidbankmicroservice.service;

import com.avantesb.rfidbankmicroservice.exceptions.EntityNotFoundException;
import com.avantesb.rfidbankmicroservice.model.dto.ClientBank;
import com.avantesb.rfidbankmicroservice.model.dto.ClientWithAccountDTO;
import com.avantesb.rfidbankmicroservice.model.entity.ClientBankEntity;
import com.avantesb.rfidbankmicroservice.model.mapper.ClientMapper;
import com.avantesb.rfidbankmicroservice.model.mapper.ClientWithAccounsMapper;
import com.avantesb.rfidbankmicroservice.model.repository.ClientBankEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientMapper clientMapper;
    private final ClientBankEntityRepository clientRepository;
    private final ClientWithAccounsMapper clientWithAccounsMapper;

    public List<ClientBank> readAllClients(Pageable pageable){
        return clientMapper.convertToDtoList(clientRepository.findAll(pageable).getContent());
    }

    public ClientBank readClient(String number){
        ClientBankEntity clientBankEntity = clientRepository.findByIdentificationNumber(number).orElseThrow(EntityNotFoundException::new);
        return clientMapper.convertToDto(clientBankEntity);
    }

    public ClientWithAccountDTO readClientWithAccount(Long clientId) {
        return clientWithAccounsMapper.convertToDto(clientRepository.getById(clientId));
        }

    public List<ClientWithAccountDTO> readAllClientsWithAccount(Pageable pageable) {
        return clientWithAccounsMapper.convertToDtoList(clientRepository.findAll(pageable).getContent());
    }

}
