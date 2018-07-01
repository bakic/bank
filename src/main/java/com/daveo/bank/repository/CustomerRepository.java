package com.daveo.bank.repository;

import com.daveo.bank.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring data repository for {@link Customer}.
 *
 * @author baki
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
