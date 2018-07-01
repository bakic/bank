package com.daveo.bank.entity;

import com.daveo.bank.enums.TransactionType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TRANSACTION")
@Setter
@Getter
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;

    @ManyToOne
    private Account account;

    @Column(name = "TRANSACTION_DATE", nullable = false)
    private LocalDateTime transactionDate;

    @Column(name = "AMOUNT", nullable = false)
    private float amount;

    @Column(name = "TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType type;

}
