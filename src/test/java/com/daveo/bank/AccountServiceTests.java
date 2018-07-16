package com.daveo.bank;

import com.daveo.bank.dto.AccountDto;
import com.daveo.bank.entity.Account;
import com.daveo.bank.enums.OperationType;
import com.daveo.bank.exception.AccountNotFoundException;
import com.daveo.bank.exception.ArgumentsException;
import com.daveo.bank.repository.AccountRepository;
import com.daveo.bank.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

        Account accountResult = accountService.getAccount(id);

        assertThat(accountResult).isNotNull();
        assertThat(accountResult)
                .extracting(Account::getId, Account::getBalance, Account::getName)
                .containsExactly(id, balance, accountName);

    }

    @Test
    public void get_account_by_id_should_fail() {

        int id = 1;

        Mockito.when(repository.findById(Mockito.eq(id))).thenThrow(new AccountNotFoundException(id));

        assertThatThrownBy(() -> accountService.getAccount(id))
                .isInstanceOf(AccountNotFoundException.class);
    }

    @Test
    public void add_to_balance_deposit_should_succeed() {
        int id = 1;
        String accountName = "Account1";
        float balance = 10;
        float amount = 2;
        float balanceResult = 12;
        OperationType deposit = OperationType.DEPOSIT;
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
        OperationType withdrawal = OperationType.WITHDRAWAL;
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

    @Test
    public void list_accounts_should_succeed() {

        Account account1 = new Account();
        account1.setId(1);
        account1.setName("Account1");
        account1.setBalance(20f);

        Account account2 = new Account();
        account2.setId(2);
        account2.setName("Account2");
        account2.setBalance(40f);

        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(account1, account2));

        List<AccountDto> accountDtos = accountService.listAccounts();

        assertThat(accountDtos).isNotNull();
        assertThat(accountDtos.size()).isEqualTo(2);

        assertThat(accountDtos.get(0))
                .extracting(AccountDto::getId, AccountDto::getName, AccountDto::getBalance)
                .containsExactly(1, "Account1", 20f);
        assertThat(accountDtos.get(1))
                .extracting(AccountDto::getId, AccountDto::getName, AccountDto::getBalance)
                .containsExactly(2, "Account2", 40f);
    }

    @Test
    public void list_accounts_empty_should_succeed() {

        Mockito.when(repository.findAll()).thenReturn(new ArrayList<>());

        List<AccountDto> accountDtos = accountService.listAccounts();

        assertThat(accountDtos).isNotNull();
        assertThat(accountDtos.size()).isEqualTo(0);

    }
}
