package com.daveo.bank.converter;

import com.daveo.bank.dto.TransactionDto;
import com.daveo.bank.entity.Transaction;

/**
 * Utility class to handle Transaction entity and TransactionDto.
 *
 * @author baki
 */
public class TransactionConverter {

    /**
     * Convert an {@link Transaction} object to a {@link TransactionDto} object.
     *
     * @param transaction A {@link Transaction} object
     * @return A {@link TransactionDto} object
     */
    public static TransactionDto entityToDto(Transaction transaction) {
        if (transaction == null) {
            return null;
        }
        return TransactionDto.builder()
                .amount(transaction.getAmount())
                .transactionDate(transaction.getTransactionDate())
                .type(transaction.getType())
                .build();
    }

    private TransactionConverter() {
        throw new IllegalStateException("Utility class");
    }
}
