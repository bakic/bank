package com.daveo.bank.service;

import com.daveo.bank.entity.Customer;
import com.daveo.bank.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The customer service class.
 *
 * @author baki
 */
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;

    /**
     * List all the customers.
     *
     * @return A list of customers
     */
    @Transactional(readOnly = true)
    public List<Customer> listCustomers() {
        return repository.findAll();
    }

}
