package com.daveo.bank.repository;

import com.daveo.bank.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The Customer repository interface.
 *
 * @author baki
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
