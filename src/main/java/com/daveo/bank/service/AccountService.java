package com.daveo.bank.service;

import com.daveo.bank.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * The account service class.
 *
 * @author baki
 */
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository repository;


}
