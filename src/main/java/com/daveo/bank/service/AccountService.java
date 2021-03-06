package com.daveo.bank.service;

import com.daveo.bank.converter.AccountConverter;
import com.daveo.bank.dto.AccountDto;
import com.daveo.bank.entity.Account;
import com.daveo.bank.enums.OperationType;
import com.daveo.bank.exception.AccountNotFoundException;
import com.daveo.bank.exception.ArgumentsException;
import com.daveo.bank.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The account service class.
 *
 * @author baki
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private final AccountRepository repository;

    /**
     * List all the accounts.
     * @return A list of the accounts
     */
    @Transactional(readOnly = true)
    public List<AccountDto> listAccounts() {
        return repository.findAll().stream()
                .map(AccountConverter::entityToDto)
                .collect(Collectors.toList());
    }

    /**
     * Get the account by its id.
     *
     * @param id The identifier of the ccount
     * @return A {@link Account} object
     */
    @Transactional
    public Account getAccount(final int id) {
        return repository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
    }

    /**
     * Update the balance value of the account by increasing or decreasing it.
     *
     * @param account The account
     * @param amount  The amount
     * @return The {@link AccountDto} object
     */
    @Transactional
    public AccountDto addToBalance(final Account account, final float amount, final OperationType operationType) {

        float oldBalance = account.getBalance();
        float newBalance;
        if (OperationType.DEPOSIT.equals(operationType)) {
            newBalance = oldBalance + amount;
        } else if (OperationType.WITHDRAWAL.equals(operationType)) {
            newBalance = oldBalance - amount;
        } else {
            log.error("The operation type '{}' is not defined, the balance will not change", operationType);
            throw new ArgumentsException("OperationRequest type not defined");
        }

        account.setBalance(newBalance);
        Account accountEntity = repository.save(account);

        return AccountConverter.entityToDto(accountEntity);
    }

}
