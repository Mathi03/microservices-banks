package com.dev.bankserviceperu.service;

import com.dev.bankserviceperu.entity.Account;
import com.dev.bankserviceperu.entity.Client;
import com.dev.bankserviceperu.entity.Transaction;
import com.dev.bankserviceperu.repository.AccountRepository;
import com.dev.bankserviceperu.repository.ClientRepository;
import com.dev.bankserviceperu.repository.TransactionRepository;
import com.dev.bankserviceperu.resource.InfoAccountResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public InfoAccountResource findByNumberAccount(String numberAccount) {
        Account account = accountRepository.findByNumberAccount(numberAccount);
        if (account == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta no encontrada");
        }
        InfoAccountResource accountResource = new InfoAccountResource();
        accountResource.setAccountNumber(account.getNumberAccount());
        accountResource.setNameClient(account.getClient().getName() + " " + account.getClient().getSurname());
        return accountResource;
    }

    public List<Account> findAllAccounts() {
        return accountRepository.findAll();
    }

    public Account updateAccount(Long id, Account accountDetails) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        account.setNumberAccount(accountDetails.getNumberAccount());
        account.setBalance(accountDetails.getBalance());
        account.setClient(accountDetails.getClient());

        return accountRepository.save(account);
    }

    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    public List<Account> findAccountsByClientId(Long clientId) {
        return accountRepository.findByClientId(clientId);
    }
}
