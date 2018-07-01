package com.daveo.bank.service;

import com.daveo.bank.converter.TransactionConverter;
import com.daveo.bank.dto.TransactionDto;
import com.daveo.bank.entity.Account;
import com.daveo.bank.entity.Transaction;
import com.daveo.bank.enums.TransactionType;
import com.daveo.bank.exception.ArgumentsException;
import com.daveo.bank.exception.FunctionalException;
import com.daveo.bank.exception.TechnicalException;
import com.daveo.bank.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * The Transaction service class.
 *
 * @author baki
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {

    private final TransactionRepository repository;
    private final AccountService accountService;

    /**
     * Make deposit in an account
     *
     * @param account The account
     * @param amount  The amount of money
     * @return A {@link TransactionDto} object
     */
    public TransactionDto saveMoney(Account account, float amount) {
        checkInputs(account, amount);
        Transaction transaction = Transaction.builder()
                .account(account)
                .amount(amount)
                .transactionDate(LocalDateTime.now())
                .type(TransactionType.DEPOSIT)
                .build();
        Transaction transactionEntity = repository.save(transaction);
        accountService.addToBalance(account, amount, TransactionType.DEPOSIT);
        return TransactionConverter.entityToDto(transactionEntity);
    }

    /**
     * Retrieve money from an account
     *
     * @param account The account
     * @param amount  The amount of money
     * @return A {@link TransactionDto} object
     */
    public TransactionDto retrieveMoney(Account account, float amount) {
        checkInputs(account, amount);
        float currentBalance = account.getBalance();
        if(amount > currentBalance){
            log.error("The amount to retrieve is bigger than the current balance");
            throw new ArgumentsException("The amount to retrieve is bigger than the current balance");
        }
        Transaction transaction = Transaction.builder()
                .account(account)
                .amount(amount)
                .transactionDate(LocalDateTime.now())
                .type(TransactionType.WITHDRAWAL)
                .build();
        Transaction transactionEntity = repository.save(transaction);
        accountService.addToBalance(account, amount, TransactionType.WITHDRAWAL);
        return TransactionConverter.entityToDto(transactionEntity);
    }

    /**
     * Check if the input parameters are well defined.
     *
     * @param account The account
     * @param amount  The amount
     */
    private void checkInputs(Account account, float amount) {
        if (account == null) {
            log.error("No account provided");
            throw new ArgumentsException("No Account provided");
        }
        if (amount <= 0) {
            log.error("The amount to add is a negative value");
            throw new ArgumentsException("The amount is not valid");
        }
    }
}
