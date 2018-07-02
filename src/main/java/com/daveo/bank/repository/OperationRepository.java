package com.daveo.bank.repository;

import com.daveo.bank.entity.Account;
import com.daveo.bank.entity.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring data repository for {@link Operation}.
 *
 * @author baki
 */
@Repository
public interface OperationRepository extends JpaRepository<Operation, Integer>, PagingAndSortingRepository<Operation, Integer> {
    @Query("Select tr from  Operation tr where tr.account = :accountId")
    Page<Operation> findByAccountIdPaged(@Param("accountId") Account account, Pageable pageable);
}
