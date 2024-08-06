package com.dev.bankserviceperu.feignclients;

import com.dev.bankserviceperu.entity.Transaction;
import com.dev.bankserviceperu.resource.InfoAccountResource;

import java.util.Map;

public interface BankFeignClient {

    InfoAccountResource findAccountNumber(String numberAccountDestination);

    Transaction transferAccount(Map<String, Object> transaccionData);
}