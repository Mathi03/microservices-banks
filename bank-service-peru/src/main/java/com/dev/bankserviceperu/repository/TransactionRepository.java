package com.dev.bankserviceperu.repository;

import com.dev.bankserviceperu.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByNumberAccountOrigin(String numberAccountOrigin);
}