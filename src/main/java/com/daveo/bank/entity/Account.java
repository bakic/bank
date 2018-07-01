package com.daveo.bank.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ACCOUNT")
@Setter
@Getter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DATE_OPENED")
    private LocalDateTime dateOpened;

    @Column(name = "BALANCE")
    private Float balance;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;
}
