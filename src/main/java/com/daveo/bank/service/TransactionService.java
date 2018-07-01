package com.daveo.bank.service;

import com.daveo.bank.converter.TransactionConverter;
import com.daveo.bank.dto.TransactionDto;
import com.daveo.bank.entity.Account;
import com.daveo.bank.entity.Transaction;
import com.daveo.bank.enums.TransactionType;
import com.daveo.bank.exception.ArgumentsException;
import com.daveo.bank.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
     * List all the transactions related to an account.
     *
     * @param account    The account
     * @param pageNumber The page number
     * @param pageSize   The number of elements by page
     * @return A list of transactions
     */
    @Transactional(readOnly=true)
    public List<TransactionDto> listTransactions(final Account account, final int pageNumber, final int pageSize) {

        PageRequest request = PageRequest.of(pageNumber, pageSize, Sort.Direction.DESC, "id");
        Page<Transaction> pagedTransactions = repository.findByAccountIdPaged(account, request);

        return pagedTransactions.getContent().stream()
                .map(TransactionConverter::entityToDto)
                .collect(Collectors.toList());
    }

    /**
     * Make deposit in an account
     *
     * @param account The account
     * @param amount  The amount of money
     * @return A {@link TransactionDto} object
     */
    @Transactional
    public TransactionDto saveMoney(Account account, float amount) {
        checkInputs(account, amount);

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setType(TransactionType.DEPOSIT);

        Transaction transactionEntity = repository.save(transaction);
        account.getTransactions().add(transactionEntity);
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
    @Transactional
    public TransactionDto retrieveMoney(Account account, float amount) {
        checkInputs(account, amount);
        float currentBalance = account.getBalance();
        if (amount > currentBalance) {
            log.error("The amount to retrieve is bigger than the current balance");
            throw new ArgumentsException("The amount to retrieve is bigger than the current balance");
        }
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setType(TransactionType.WITHDRAWAL);
        Transaction transactionEntity = repository.save(transaction);
        account.getTransactions().add(transactionEntity);
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
