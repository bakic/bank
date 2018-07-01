package com.daveo.bank.service;

import com.daveo.bank.converter.AccountConverter;
import com.daveo.bank.dto.AccountDto;
import com.daveo.bank.entity.Account;
import com.daveo.bank.enums.TransactionType;
import com.daveo.bank.exception.ArgumentsException;
import com.daveo.bank.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
    public Optional<Account> getAccount(final int id) {
        Optional<Account> account = repository.findById(id);
        if (!account.isPresent()) {
            return Optional.empty();
        }
        return Optional.of(account.get());
    }

    /**
     * Update the balance value of the account by increasing or decreasing it.
     *
     * @param account The account
     * @param amount  The amount
     * @return The {@link AccountDto} object
     */
    public AccountDto addToBalance(final Account account, final float amount, final TransactionType transactionType) {

        float oldBalance = account.getBalance();
        float newBalance;
        if (TransactionType.DEPOSIT.equals(transactionType)) {
            newBalance = oldBalance + amount;
        } else if (TransactionType.WITHDRAWAL.equals(transactionType)) {
            newBalance = oldBalance - amount;
        } else {
            log.error("The transaction type '{}' is not defined, the balance will not change", transactionType);
            throw new ArgumentsException("Transaction type not defined");
        }

        account.setBalance(newBalance);
        Account accountEntity = repository.save(account);

        return AccountConverter.entityToDto(accountEntity);
    }

}
