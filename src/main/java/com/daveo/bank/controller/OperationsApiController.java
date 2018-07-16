package com.daveo.bank.controller;

import com.daveo.bank.dto.OperationDto;
import com.daveo.bank.dto.OperationRequest;
import com.daveo.bank.dto.OperationResponse;
import com.daveo.bank.entity.Account;
import com.daveo.bank.exception.AccountNotFoundException;
import com.daveo.bank.service.AccountService;
import com.daveo.bank.service.OperationService;
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
public class OperationsApiController {

    private final OperationService operationService;
    private final AccountService accountService;

    /**
     * Savr money in the account.
     *
     * @param accountId   The account id
     * @param operationRequest The amount
     * @return The operationRequest
     */
    @PostMapping(value = "/deposit/{accountId}")
    public ResponseEntity<OperationDto> deposit(@PathVariable int accountId, @RequestBody OperationRequest operationRequest) {

        Account account = accountService.getAccount(accountId);
        OperationDto operationDto = operationService.saveMoney(account, operationRequest.getAmount());

        return ResponseEntity.ok(operationDto);
    }

    /**
     * Retrieve money from savings.
     *
     * @param accountId   The account id
     * @param operationRequest The amount
     * @return The operationRequest
     */
    @PostMapping(value = "/retrieve/{accountId}")
    public ResponseEntity<OperationDto> retrieve(@PathVariable int accountId, @RequestBody OperationRequest operationRequest) {

        Account account = accountService.getAccount(accountId);
        OperationDto operationDto = operationService.retrieveMoney(account, operationRequest.getAmount());

        return ResponseEntity.ok(operationDto);
    }

    /**
     * List the history of the operations.
     *
     * @param accountId The account id
     * @param noPage    The number of the page
     * @param size      The number of elements per page
     * @return A list of operations
     */
    @GetMapping(value = "/history")
    public ResponseEntity<OperationResponse> history(@RequestParam int accountId,
                                                     @RequestParam(required = false, defaultValue = "0") int noPage,
                                                     @RequestParam(required = false, defaultValue = "5") int size) {

        Account account = accountService.getAccount(accountId);

        OperationResponse operations = operationService.listOperation(account, noPage, size);
        return ResponseEntity.ok(operations);
    }
}
