package com.dev.bankserviceargentina.controller;

import com.dev.bankserviceargentina.entity.Account;
import com.dev.bankserviceargentina.resource.InfoAccountResource;
import com.dev.bankserviceargentina.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "${application.frontend}" )
@RestController
@RequestMapping("/${application.code}/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account cuenta) {
        return ResponseEntity.ok(accountService.createAccount(cuenta));
    }

    @GetMapping("/info/{numberAccount}")
    public ResponseEntity<InfoAccountResource> findAccountByNumber(@PathVariable String numberAccount) {
        return ResponseEntity.ok(accountService.findByNumberAccount(numberAccount));
    }

    @GetMapping
    public ResponseEntity<List<Account>> findAllAccounts() {
        return ResponseEntity.ok(accountService.findAllAccounts());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long id, @RequestBody Account account) {
        return ResponseEntity.ok(accountService.updateAccount(id, account));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Account>> findAccountsByClientId(@PathVariable Long clientId) {
        return ResponseEntity.ok(accountService.findAccountsByClientId(clientId));
    }
}