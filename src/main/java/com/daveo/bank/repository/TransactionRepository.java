package com.daveo.bank.repository;

import com.daveo.bank.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring data repository for {@link Transaction}.
 *
 * @author baki
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer>, PagingAndSortingRepository<Transaction, Integer> {
}
