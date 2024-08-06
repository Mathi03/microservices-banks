package com.dev.bankservicemexico.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(nullable = false)
    private String numberAccountOrigin;

    @Column(nullable = false)
    private String numberAccountDestination;

    // @ManyToOne
    // @JoinColumn(name = "account_id")
    // private Account account;
}