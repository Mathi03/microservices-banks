package com.dev.bankservicemexico.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String numberAccount;

    @Column(nullable = false)
    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    @JsonIgnore
    private Client client;
}