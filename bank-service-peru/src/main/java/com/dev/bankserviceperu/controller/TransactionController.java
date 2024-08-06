package com.dev.bankserviceperu.controller;

import com.dev.bankserviceperu.entity.Transaction;
import com.dev.bankserviceperu.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "${application.frontend}" )
@RestController
@RequestMapping("/${application.code}/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> doneTransaction(@RequestBody Map<String, Object> transaccionData) throws Exception {
            String numberaccount = (String) transaccionData.get("numberAccount");
            String tipo = (String) transaccionData.get("type");
            BigDecimal monto = new BigDecimal((String) transaccionData.get("amount"));

            return ResponseEntity.ok(transactionService.doneTransaction(numberaccount, tipo, monto ));
    }

    @GetMapping("/account/{numberAccountOrigin}")
    public ResponseEntity<List<Transaction>> getTransactionsByNumberAccountOrigin(@PathVariable String numberAccountOrigin) {
        return ResponseEntity.ok(transactionService.getTransactionsByNumberAccountOrigin(numberAccountOrigin));
    }

    @PostMapping("/transfer")
    public ResponseEntity<Boolean> transferAnotherAccount(@RequestBody Map<String, Object> transaccionData) throws Exception {
            String numberAccountOrigin = (String) transaccionData.get("numberAccountOrigin");
            String numberAccountDestination = (String) transaccionData.get("numberAccountDestination");
            BigDecimal amount = new BigDecimal((String) transaccionData.get("amount"));

            return ResponseEntity.ok(transactionService.transferAnotherAccount(numberAccountOrigin, numberAccountDestination, amount ));
    }
}