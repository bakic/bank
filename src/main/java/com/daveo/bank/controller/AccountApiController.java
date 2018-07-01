package com.daveo.bank.controller;

import com.daveo.bank.dto.AccountDto;
import com.daveo.bank.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
     * LList all the available accounts
     *
     * @return A list of the accounts
     */
    @GetMapping(value = "/account")
    public ResponseEntity<List<AccountDto>> listAccounts() {
        List<AccountDto> accounts = accountService.listAccounts();
        return ResponseEntity.ok(accounts);
    }
}
