package com.daveo.bank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Account not found exception.
 *
 * @author baki
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(int accountId) {
        super("could not find account '" + accountId + "'.");
    }
}
