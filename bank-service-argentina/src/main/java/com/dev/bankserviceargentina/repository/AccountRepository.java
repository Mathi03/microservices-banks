package com.dev.bankserviceargentina.repository;

import com.dev.bankserviceargentina.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByNumberAccount(String numberAccount);
    List<Account> findByClientId(Long clientId);
}