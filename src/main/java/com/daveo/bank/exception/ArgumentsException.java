package com.daveo.bank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when arguments are missing or incorrect.
 *
 * @author baki
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ArgumentsException extends RuntimeException {

    public ArgumentsException(String message) {
        super(message);
    }
}
