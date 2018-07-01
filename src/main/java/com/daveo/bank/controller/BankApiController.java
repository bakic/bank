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

/**
 * The endpoints class.
 *
 * @author baki
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
@Slf4j
public class BankApiController {

    private final TransactionService transactionService;
    private final AccountService accountService;

    @PostMapping(value = "/deposit/{accountId}")
    public ResponseEntity<TransactionDto> deposit(@PathVariable int accountId, @RequestBody Transaction transaction){

        Account account = accountService.getAccount(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));
        TransactionDto transactionDto = transactionService.saveMoney(account, transaction.getAmount());

        return ResponseEntity.ok(transactionDto);
    }

    @PostMapping(value = "/retrieve/{accountId}")
    public ResponseEntity<TransactionDto> retrieve(@PathVariable int accountId, @RequestBody Transaction transaction){

        Account account = accountService.getAccount(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));
        TransactionDto transactionDto = transactionService.retrieveMoney(account, transaction.getAmount());

        return ResponseEntity.ok(transactionDto);
    }
}
