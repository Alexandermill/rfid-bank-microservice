package com.avantesb.rfidbankmicroservice.service;

import com.avantesb.rfidbankmicroservice.exceptions.EntityNotFoundException;
import com.avantesb.rfidbankmicroservice.model.dto.AccountBank;
import com.avantesb.rfidbankmicroservice.model.dto.ClientWithAccountDTO;
import com.avantesb.rfidbankmicroservice.model.entity.ClientBankEntity;
import com.avantesb.rfidbankmicroservice.model.mapper.ClientWithAccounsMapper;
import com.avantesb.rfidbankmicroservice.model.repository.ClientBankEntityRepository;
import com.avantesb.rfidbankmicroservice.service.message.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientBankEntityRepository clientRepository;
    private final ClientWithAccounsMapper clientWithAccounsMapper;
    private final MessageService messageService;



    public ClientWithAccountDTO readClient(String number){
        ClientBankEntity clientBankEntity = clientRepository.findByIdentificationNumber(number).orElseThrow(EntityNotFoundException::new);

        messageService.sendClientIdsToAccountService(List.of(clientBankEntity.getId()));

        List<AccountBank> accounts = messageService.getAccountsQueue();

        ClientWithAccountDTO clientDto = clientWithAccounsMapper.convertToDto(clientBankEntity);
        clientDto.setAccounts(accounts);
        return clientDto;
    }


    public List<ClientWithAccountDTO> readAllClientsWithAccounts(Pageable pageable) {
        List<ClientBankEntity> entities = clientRepository.findAll(pageable).getContent();
        List<ClientWithAccountDTO> dtoList = clientWithAccounsMapper.convertToDtoList(entities);

        List<Long> ids = entities
                .stream()
                .map(ClientBankEntity::getId)
                .collect(Collectors.toList());

        messageService.sendClientIdsToAccountService(ids);

        List<AccountBank> accounts = messageService.getAccountsQueue();
        System.out.println("in service accounts: " + accounts);

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
