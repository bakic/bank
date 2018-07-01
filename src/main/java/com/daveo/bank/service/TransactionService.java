package com.daveo.bank.service;

import com.daveo.bank.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * The Transaction service class.
 *
 * @author baki
 */
@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository repository;


}
