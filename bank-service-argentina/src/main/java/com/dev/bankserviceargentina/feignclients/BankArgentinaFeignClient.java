package com.dev.bankserviceargentina.feignclients;

import com.dev.bankserviceargentina.entity.Transaction;
import com.dev.bankserviceargentina.resource.InfoAccountResource;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "bank-service-argentina", path = "/ARG/api")
public interface BankArgentinaFeignClient extends BankFeignClient{

    @GetMapping("/accounts/info/{numberAccount}")
    public InfoAccountResource findAccountNumber(@PathVariable("numberAccount") String numberAccount);

    @PostMapping("/transactions")
    public Transaction transferAccount(@RequestBody Map<String, Object> transaccionData);
}
