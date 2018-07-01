package com.daveo.bank.dto;

import com.daveo.bank.enums.TransactionType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
    private Float amount;
    private TransactionType type;
}
