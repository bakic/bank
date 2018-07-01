package com.daveo.bank.dto;

import com.daveo.bank.enums.TransactionType;
import lombok.*;

import java.time.LocalDateTime;

/**
 * The Transaction dto class.
 *
 * @author baki
 */
@Setter
@Getter
@Builder
public class TransactionDto {
    private LocalDateTime transactionDate;
    private float amount;
    private TransactionType type;

}
