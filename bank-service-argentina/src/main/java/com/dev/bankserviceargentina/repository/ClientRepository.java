package com.dev.bankserviceargentina.repository;

import com.dev.bankserviceargentina.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Long> {
    Client findByEmail(String email);
    Client findByDni(String dni);
}