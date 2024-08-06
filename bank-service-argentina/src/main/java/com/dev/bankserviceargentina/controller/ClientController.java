package com.dev.bankserviceargentina.controller;

import com.dev.bankserviceargentina.entity.Client;
import com.dev.bankserviceargentina.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "${application.frontend}" )
@RestController
@RequestMapping("/${application.code}/api/clients")
public class ClientController {

    @Autowired
    private ClientService clienteService;

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client cliente) {
        return ResponseEntity.ok(clienteService.createClient(cliente));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Client> findClientByEmail(@PathVariable String email) {
        return ResponseEntity.ok(clienteService.findClientByEmail(email));
    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<Client> findClientByDni(@PathVariable String dni) {
        return ResponseEntity.ok(clienteService.findClientByDni(dni));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> findClientById(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.findClientById(id));
    }

    @GetMapping
    public ResponseEntity<List<Client>> findAllClients() {
        return ResponseEntity.ok(clienteService.findAllClients());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client clientDetails) {
        Client updatedClient = clienteService.updateClient(id, clientDetails);
        return ResponseEntity.ok(updatedClient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clienteService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}