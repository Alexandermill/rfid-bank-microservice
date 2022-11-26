package com.avantesb.rfidbankmicroservice.service;

import com.avantesb.rfidbankmicroservice.exceptions.EntityNotFoundException;
import com.avantesb.rfidbankmicroservice.model.dto.AccountBank;
import com.avantesb.rfidbankmicroservice.model.dto.ClientBank;
import com.avantesb.rfidbankmicroservice.model.dto.ClientWithAccountDTO;
import com.avantesb.rfidbankmicroservice.model.entity.ClientBankEntity;
import com.avantesb.rfidbankmicroservice.model.mapper.ClientMapper;
import com.avantesb.rfidbankmicroservice.model.mapper.ClientWithAccounsMapper;
import com.avantesb.rfidbankmicroservice.model.repository.ClientBankEntityRepository;
import com.avantesb.rfidbankmicroservice.service.client.AccountClient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientMapper clientMapper;
    private final ClientBankEntityRepository clientRepository;
    private final ClientWithAccounsMapper clientWithAccounsMapper;
    private final AccountClient accountFeignClient;

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

    public List<ClientWithAccountDTO> readAllClientsWithAccountNew(Pageable pageable) {
        List<ClientBankEntity> entities = clientRepository.findAll(pageable).getContent();
        List<ClientWithAccountDTO> dtoList = clientWithAccounsMapper.convertToDtoList(entities);

        List<Long> ids = entities
                .stream()
                .map(ClientBankEntity::getId)
                .collect(Collectors.toList());

        List<AccountBank> accounts = accountFeignClient.getAccountsByClientNew(ids);

        for(ClientWithAccountDTO client : dtoList){
            client.setAccounts(
                    accounts.stream()
                            .filter(a -> a.getClientId() == client.getId())
                            .collect(Collectors.toList())
            );
        }


        return dtoList;
    }
}
