package com.daveo.bank;

import com.daveo.bank.dto.AccountDto;
import com.daveo.bank.entity.Account;
import com.daveo.bank.enums.TransactionType;
import com.daveo.bank.exception.ArgumentsException;
import com.daveo.bank.repository.AccountRepository;
import com.daveo.bank.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTests {

    @Mock
    private AccountRepository repository;

    @InjectMocks
    private AccountService accountService;

    @Test
    public void get_account_by_id_should_succeed() {

        int id = 10;
        String accountName = "Account1";
        float balance = 10;

        Account account = new Account();
        account.setId(id);
        account.setName(accountName);
        account.setBalance(balance);

        Mockito.when(repository.findById(Mockito.eq(id))).thenReturn(Optional.of(account));

        Optional<Account> accountResult = accountService.getAccount(id);

        assertThat(accountResult.isPresent()).isTrue();
        assertThat(accountResult.get())
                .extracting(Account::getId, Account::getBalance, Account::getName)
                .containsExactly(id, balance, accountName);

    }

    @Test
    public void get_account_by_id_should_fail() {

        int id = 1;

        Mockito.when(repository.findById(Mockito.eq(id))).thenReturn(Optional.empty());

        Optional<Account> accountResult = accountService.getAccount(id);
        assertThat(accountResult.isPresent()).isFalse();
    }

    @Test
    public void add_to_balance_deposit_should_succeed() {
        int id = 1;
        String accountName = "Account1";
        float balance = 10;
        float amount = 2;
        float balanceResult = 12;
        TransactionType deposit = TransactionType.DEPOSIT;
        Account account = new Account();
        account.setId(id);
        account.setName(accountName);
        account.setBalance(balance);

        Mockito.when(repository.save(Mockito.any(Account.class))).thenReturn(account);

        AccountDto accountDto = accountService.addToBalance(account, amount, deposit);

        assertThat(accountDto).isNotNull();
        assertThat(accountDto)
                .extracting(AccountDto::getBalance, AccountDto::getId, AccountDto::getName)
                .containsExactly(balanceResult, id, accountName);
    }

    @Test
    public void add_to_balance_withdraw_should_succeed() {
        int id = 1;
        String accountName = "Account1";
        float balance = 10;
        float amount = 2;
        float balanceResult = 8;
        TransactionType withdrawal = TransactionType.WITHDRAWAL;
        Account account = new Account();
        account.setId(id);
        account.setName(accountName);
        account.setBalance(balance);

        Mockito.when(repository.save(Mockito.any(Account.class))).thenReturn(account);

        AccountDto accountDto = accountService.addToBalance(account, amount, withdrawal);

        assertThat(accountDto).isNotNull();
        assertThat(accountDto)
                .extracting(AccountDto::getBalance, AccountDto::getId, AccountDto::getName)
                .containsExactly(balanceResult, id, accountName);
    }

    @Test
    public void add_to_balance_wrong_operation_should_fail() {
        int id = 1;
        String accountName = "Account1";
        float balance = 10;
        float amount = 2;
        Account account = new Account();
        account.setId(id);
        account.setName(accountName);
        account.setBalance(balance);

        assertThatThrownBy(() -> accountService.addToBalance(account, amount, null))
                .isInstanceOf(ArgumentsException.class);
    }

}
