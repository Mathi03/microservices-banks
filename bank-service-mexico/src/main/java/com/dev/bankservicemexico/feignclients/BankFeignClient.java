package com.dev.bankservicemexico.feignclients;

import com.dev.bankservicemexico.entity.Transaction;
import com.dev.bankservicemexico.resource.InfoAccountResource;

import java.util.Map;

public interface BankFeignClient {

    InfoAccountResource findAccountNumber(String numberAccountDestination);

    Transaction transferAccount(Map<String, Object> transaccionData);
}