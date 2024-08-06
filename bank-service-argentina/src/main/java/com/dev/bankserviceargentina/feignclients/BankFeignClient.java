package com.dev.bankserviceargentina.feignclients;

import com.dev.bankserviceargentina.entity.Transaction;
import com.dev.bankserviceargentina.resource.InfoAccountResource;

import java.util.Map;

public interface BankFeignClient {

    InfoAccountResource findAccountNumber(String numberAccountDestination);

    Transaction transferAccount(Map<String, Object> transaccionData);
}