package com.dev.bankservicemexico.feignclients;

import com.dev.bankservicemexico.entity.Transaction;
import com.dev.bankservicemexico.resource.InfoAccountResource;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "bank-service-mexico", path = "/MEX/api")
public interface BankMexicoFeignClient extends BankFeignClient {

    @GetMapping("/accounts/info/{numberAccount}")
    public InfoAccountResource findAccountNumber(@PathVariable("numberAccount") String numberAccount);

    @PostMapping("/transactions")
    public Transaction transferAccount(@RequestBody Map<String, Object> transaccionData);
}
