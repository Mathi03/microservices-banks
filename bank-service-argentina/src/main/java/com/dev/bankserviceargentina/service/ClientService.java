package com.dev.bankserviceargentina.service;

import com.dev.bankserviceargentina.entity.Client;
import com.dev.bankserviceargentina.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    public Client findClientByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    public Client findClientByDni(String dni) {
        return clientRepository.findByDni(dni);
    }

    public Client findClientById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    public List<Client> findAllClients() {
        return (List<Client>) clientRepository.findAll();
    }

    public Client updateClient(Long id, Client clientDetails) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        client.setName(clientDetails.getName());
        client.setDni(clientDetails.getDni());
        client.setEmail(clientDetails.getEmail());
        client.setAddress(clientDetails.getAddress());
        client.setPhone(clientDetails.getPhone());
        client.setNationality(clientDetails.getNationality());

        return clientRepository.save(client);
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}