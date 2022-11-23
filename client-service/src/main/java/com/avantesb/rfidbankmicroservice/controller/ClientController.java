package com.avantesb.rfidbankmicroservice.controller;

import com.avantesb.rfidbankmicroservice.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping("/{identification}")
    public ResponseEntity readUser(@PathVariable("identification") String identification){
        return ResponseEntity.ok(clientService.readClient(identification));
    }

    @GetMapping
    public ResponseEntity readAllClients( Pageable pageable){
        return ResponseEntity.ok(clientService.readAllClients(pageable));
    }

    @GetMapping("/id/{clientId}")
    public ResponseEntity readUserWithAccount(@PathVariable("clientId") Long clientId){
        return ResponseEntity.ok(clientService.readClientWithAccount(clientId));
    }

    @GetMapping("/clients")
    public ResponseEntity readAllClientsWithAccount( Pageable pageable){
        return ResponseEntity.ok(clientService.readAllClientsWithAccount(pageable));
    }

    
}
