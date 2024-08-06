package com.dev.bankserviceperu.service;


import com.dev.bankserviceperu.entity.Account;
import com.dev.bankserviceperu.entity.Transaction;
import com.dev.bankserviceperu.feignclients.BankArgentinaFeignClient;
import com.dev.bankserviceperu.feignclients.BankFeignClient;
import com.dev.bankserviceperu.feignclients.BankMexicoFeignClient;
import com.dev.bankserviceperu.feignclients.BankPeruFeignClient;
import com.dev.bankserviceperu.repository.AccountRepository;
import com.dev.bankserviceperu.repository.TransactionRepository;
import com.dev.bankserviceperu.resource.InfoAccountResource;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransactionService extends AbstractClient {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BankPeruFeignClient bankPeruFeignClient;

    @Autowired
    private BankMexicoFeignClient bankMexicoFeignClient;

    @Autowired
    private BankArgentinaFeignClient bankArgentinaFeignClient;

    protected TransactionService(RestTemplate restTemplate) {
        super(restTemplate);
    }

    public Transaction doneTransaction(String numberAccount, String type, BigDecimal amount) throws Exception {
        Account account = accountRepository.findByNumberAccount(numberAccount);
        if (account == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta no encontrada");
        }

        if (type.equalsIgnoreCase("retiro") && account.getBalance().compareTo(amount) < 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Saldo insuficiente");
        }

        BigDecimal newBalance = type.equalsIgnoreCase("deposito") ?
                account.getBalance().add(amount) : account.getBalance().subtract(amount);

        account.setBalance(newBalance);
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setType(type);
        transaction.setAmount(amount);
        transaction.setDateTime(LocalDateTime.now());
        transaction.setNumberAccountOrigin(numberAccount);
        transaction.setNumberAccountDestination(account.getNumberAccount());

        return transactionRepository.save(transaction);
    }

    public Boolean transferAnotherAccount(String numberAccountOrigin, String numberAccountDestination, BigDecimal amount) throws Exception {
        boolean isInternal = numberAccountDestination.startsWith(codeCountry);

        if (isInternal) {
            accountRepository.findByNumberAccount(numberAccountDestination);
        } else {
            transferToExternalBank(numberAccountDestination, amount);
        }

        // Verificar y debitar del banco de origen
        Transaction transaccionRetiro = doneTransaction(numberAccountOrigin, "retiro", amount);

        // Registrar la transferencia como una transacción
        registerTransferTransaction(numberAccountOrigin, numberAccountDestination, amount);

        if (isInternal) {
            doneTransaction(numberAccountDestination, "deposito", amount);
        }

        return true;
    }

    private void transferToExternalBank(String numberAccountDestination, BigDecimal amount) {
        BankFeignClient feignClient = getFeignClientByCountryCode(numberAccountDestination);
        if (feignClient == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Banco destino desconocido");
        }

        try {
            InfoAccountResource resInfo = feignClient.findAccountNumber(numberAccountDestination);
            // System.out.println("InfoAccountResource: " + resInfo);

            Map<String, Object> transactionData = new HashMap<>();
            transactionData.put("numberAccount", numberAccountDestination);
            transactionData.put("type", "deposito");
            transactionData.put("amount", amount.toString());
            Transaction resTransaction = feignClient.transferAccount(transactionData);
            // System.out.println("resTransaction: " + resTransaction);
        } catch (FeignException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error realizar operacion");
        }

    }

    private BankFeignClient getFeignClientByCountryCode(String numberAccountDestination) {
        if (numberAccountDestination.startsWith("ARG")) {
            return bankArgentinaFeignClient;
        } else if (numberAccountDestination.startsWith("MEX")) {
            return bankMexicoFeignClient;
        } else if (numberAccountDestination.startsWith("PER")) {
            return bankPeruFeignClient;
        }
        return null;
    }

    private void registerTransferTransaction(String numberAccountOrigin, String numberAccountDestination, BigDecimal amount) {
        Transaction transactionTransfer = new Transaction();
        transactionTransfer.setType("transferencia");
        transactionTransfer.setAmount(amount);
        transactionTransfer.setDateTime(LocalDateTime.now());
        transactionTransfer.setNumberAccountOrigin(numberAccountOrigin);
        transactionTransfer.setNumberAccountDestination(numberAccountDestination);
        transactionRepository.save(transactionTransfer);
    }

    public List<Transaction> getTransactionsByNumberAccountOrigin(String numberAccountOrigin) {
        Account account = accountRepository.findByNumberAccount(numberAccountOrigin);
        if (account == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta no encontrada");
        }
        return transactionRepository.findByNumberAccountOrigin(account.getNumberAccount());
    }
}
