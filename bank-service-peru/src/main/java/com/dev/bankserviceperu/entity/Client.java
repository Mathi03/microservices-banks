package com.dev.bankserviceperu.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false, unique = true)
    private String dni;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = true)
    private String address;

    @Column(nullable = true)
    private int phone;

    @Column(nullable = true)
    private String nationality;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Account> accounts;
    }