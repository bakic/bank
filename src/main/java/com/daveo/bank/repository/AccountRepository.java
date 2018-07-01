package com.daveo.bank.repository;

import com.daveo.bank.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring data repository for {@link Account}.
 *
 * @author baki
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
}
