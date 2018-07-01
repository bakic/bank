package com.daveo.bank.controller;

import com.daveo.bank.dto.Transaction;
import com.daveo.bank.dto.TransactionDto;
import com.daveo.bank.entity.Account;
import com.daveo.bank.exception.AccountNotFoundException;
import com.daveo.bank.service.AccountService;
import com.daveo.bank.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The endpoints class.
 *
 * @author baki
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
@Slf4j
public class OperationsApiController {

    private final TransactionService transactionService;
    private final AccountService accountService;

    /**
     * Savr money in the account.
     *
     * @param accountId   The account id
     * @param transaction The amount
     * @return The transaction
     */
    @PostMapping(value = "/deposit/{accountId}")
    public ResponseEntity<TransactionDto> deposit(@PathVariable int accountId, @RequestBody Transaction transaction) {

        Account account = accountService.getAccount(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));
        TransactionDto transactionDto = transactionService.saveMoney(account, transaction.getAmount());

        return ResponseEntity.ok(transactionDto);
    }

    /**
     * Retrieve money from savings.
     *
     * @param accountId   The account id
     * @param transaction The amount
     * @return The transaction
     */
    @PostMapping(value = "/retrieve/{accountId}")
    public ResponseEntity<TransactionDto> retrieve(@PathVariable int accountId, @RequestBody Transaction transaction) {

        Account account = accountService.getAccount(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));
        TransactionDto transactionDto = transactionService.retrieveMoney(account, transaction.getAmount());

        return ResponseEntity.ok(transactionDto);
    }

    /**
     * List the history of the transactions.
     *
     * @param accountId The account id
     * @param noPage    The number of the page
     * @param size      The number of elements per page
     * @return A list of transactions
     */
    @GetMapping(value = "/history")
    public ResponseEntity<List<TransactionDto>> history(@RequestParam int accountId,
                                                        @RequestParam(required = false, defaultValue = "0") int noPage,
                                                        @RequestParam(required = false, defaultValue = "5") int size) {
        Account account = accountService.getAccount(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));

        List<TransactionDto> transactions = transactionService.listTransactions(account, noPage, size);
        return ResponseEntity.ok(transactions);
    }
}
