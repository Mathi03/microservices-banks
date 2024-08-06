package com.dev.bankserviceperu.feignclients;

import com.dev.bankserviceperu.entity.Transaction;
import com.dev.bankserviceperu.resource.InfoAccountResource;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "bank-service-argentina", path = "/ARG/api")
public interface BankArgentinaFeignClient extends BankFeignClient{

    @GetMapping("/accounts/info/{numberAccount}")
    public InfoAccountResource findAccountNumber(@PathVariable("numberAccount") String numberAccount);

    @PostMapping("/transactions")
    public Transaction transferAccount(@RequestBody Map<String, Object> transaccionData);
}
