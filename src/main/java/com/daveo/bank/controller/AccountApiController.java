package com.daveo.bank.controller;

import com.daveo.bank.converter.AccountConverter;
import com.daveo.bank.dto.AccountDto;
import com.daveo.bank.entity.Account;
import com.daveo.bank.exception.AccountNotFoundException;
import com.daveo.bank.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
@Slf4j
public class AccountApiController {

    private final AccountService accountService;

    /**
     * List all the available accounts
     *
     * @return A list of the accounts
     */
    @GetMapping(value = "/account")
    public ResponseEntity<List<AccountDto>> listAccounts() {
        List<AccountDto> accounts = accountService.listAccounts();
        return ResponseEntity.ok(accounts);
    }

    /**
     * Get an account by its id
     *
     * @param accountId The id of the account
     * @return A list of the accounts
     */
    @GetMapping(value = "/account/{accountId}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable int accountId) {
        Account account = accountService.getAccount(accountId);
        return ResponseEntity.ok(AccountConverter.entityToDto(account));
    }
}
