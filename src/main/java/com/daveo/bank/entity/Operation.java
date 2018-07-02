package com.daveo.bank.entity;

import com.daveo.bank.enums.OperationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "OPERATION")
@Setter
@Getter
@NoArgsConstructor
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;

    @ManyToOne
    private Account account;

    @Column(name = "OPERATION_DATE")
    private LocalDateTime operationDate;

    @Column(name = "AMOUNT", nullable = false)
    private float amount;

    @Column(name = "TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private OperationType type;

}
