package com.daveo.bank;

import com.daveo.bank.converter.AccountConverter;
import com.daveo.bank.dto.AccountDto;
import com.daveo.bank.dto.TransactionDto;
import com.daveo.bank.entity.Account;
import com.daveo.bank.entity.Transaction;
import com.daveo.bank.enums.TransactionType;
import com.daveo.bank.exception.ArgumentsException;
import com.daveo.bank.repository.TransactionRepository;
import com.daveo.bank.service.AccountService;
import com.daveo.bank.service.TransactionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTests {

    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private AccountService accountService;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    public void save_money_should_succeed() {

        Account account = new Account();
        account.setName("Account1");
        account.setBalance(0f);

        float amount = 5;

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setType(TransactionType.DEPOSIT);

        AccountDto accountDto = AccountConverter.entityToDto(account);

        //Mocks
        Mockito.when(transactionRepository.save(Mockito.any(Transaction.class))).thenReturn(transaction);
        Mockito.when(accountService.addToBalance(Mockito.eq(account), Mockito.eq(amount), Mockito.any(TransactionType.class)))
                .thenReturn(accountDto);

        TransactionDto transactionDto = transactionService.saveMoney(account, amount);

        assertThat(transactionDto).isNotNull();
        assertThat(transactionDto)
                .extracting(TransactionDto::getAmount, TransactionDto::getType)
                .containsExactly(amount, TransactionType.DEPOSIT);

        Mockito.verify(accountService).addToBalance(account, amount, TransactionType.DEPOSIT);
    }

    @Test
    public void save_money_no_account_should_fail() {

        Account account = null;
        float amount = 5;

        assertThatThrownBy(() -> transactionService.saveMoney(account, amount))
                .isInstanceOf(ArgumentsException.class);
    }

    @Test
    public void save_money_negative_amount_should_fail() {

        Account account = new Account();
        float amount = -5;

        assertThatThrownBy(() -> transactionService.saveMoney(account, amount))
                .isInstanceOf(ArgumentsException.class);
    }

    @Test
    public void retrieve_money_should_succeed() {

        Account account = new Account();
        account.setName("Account1");
        account.setBalance(20f);

        float amount = 5;
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setType(TransactionType.WITHDRAWAL);

        AccountDto accountDto = AccountConverter.entityToDto(account);

        //Mocks
        Mockito.when(transactionRepository.save(Mockito.any(Transaction.class))).thenReturn(transaction);
        Mockito.when(accountService.addToBalance(Mockito.eq(account), Mockito.eq(amount), Mockito.any(TransactionType.class)))
                .thenReturn(accountDto);

        TransactionDto transactionDto = transactionService.retrieveMoney(account, amount);

        assertThat(transactionDto).isNotNull();
        assertThat(transactionDto)
                .extracting(TransactionDto::getAmount, TransactionDto::getType)
                .containsExactly(amount, TransactionType.WITHDRAWAL);

        Mockito.verify(accountService).addToBalance(account, amount, TransactionType.WITHDRAWAL);
    }

    @Test
    public void retrieve_money_not_enough_balance_should_fail() {

        Account account = new Account();
        account.setName("Account1");
        account.setBalance(10f);

        float amount = 20;

        assertThatThrownBy(() -> transactionService.retrieveMoney(account, amount))
                .isInstanceOf(ArgumentsException.class);
    }

    @Test
    public void retrieve_money_no_account_should_fail() {

        Account account = null;
        float amount = 5;

        assertThatThrownBy(() -> transactionService.retrieveMoney(account, amount))
                .isInstanceOf(ArgumentsException.class);
    }

    @Test
    public void retrieve_money_negative_amount_should_fail() {

        Account account = new Account();
        float amount = -5;

        assertThatThrownBy(() -> transactionService.retrieveMoney(account, amount))
                .isInstanceOf(ArgumentsException.class);
    }

    @Test
    public void list_transactions_should_succeed(){

        Account account = new Account();
        Transaction transaction1 = new Transaction();
        transaction1.setId(1);
        transaction1.setAmount(5f);
        Transaction transaction2 = new Transaction();
        transaction2.setId(2);
        transaction2.setAmount(10f);

        Page<Transaction> transactionsPage = new PageImpl<>(Arrays.asList(transaction1, transaction2));

        Mockito.when(transactionRepository.findByAccountIdPaged(Mockito.any(Account.class), Mockito.any(Pageable.class)))
                .thenReturn(transactionsPage);

        List<TransactionDto> transactions = transactionService.listTransactions(account, 0, 5);

        assertThat(transactions).isNotNull();
        assertThat(transactions.size()).isEqualTo(2);

        assertThat(transactions.get(0))
                .extracting(TransactionDto::getAmount)
                .containsExactly(5f);

        assertThat(transactions.get(1))
                .extracting(TransactionDto::getAmount)
                .containsExactly(10f);
    }


    @Test
    public void list_transactions_empty_should_succeed(){

        Account account = new Account();
        Page<Transaction> transactionsPage = Page.empty();

        Mockito.when(transactionRepository.findByAccountIdPaged(Mockito.any(Account.class), Mockito.any(Pageable.class)))
                .thenReturn(transactionsPage);

        List<TransactionDto> transactions = transactionService.listTransactions(account, 1, 5);

        assertThat(transactions).isNotNull();
        assertThat(transactions.size()).isEqualTo(0);
    }

}
