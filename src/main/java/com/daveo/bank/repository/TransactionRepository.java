package com.daveo.bank.repository;

import com.daveo.bank.entity.Account;
import com.daveo.bank.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring data repository for {@link Transaction}.
 *
 * @author baki
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer>, PagingAndSortingRepository<Transaction, Integer> {
    @Query("Select tr from  Transaction tr where tr.account = :accountId")
    Page<Transaction> findByAccountIdPaged(@Param("accountId") Account account, Pageable pageable);
}
