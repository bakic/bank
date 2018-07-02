package com.daveo.bank;

import com.daveo.bank.converter.AccountConverter;
import com.daveo.bank.dto.AccountDto;
import com.daveo.bank.dto.OperationDto;
import com.daveo.bank.dto.OperationResponse;
import com.daveo.bank.entity.Account;
import com.daveo.bank.entity.Operation;
import com.daveo.bank.enums.OperationType;
import com.daveo.bank.exception.ArgumentsException;
import com.daveo.bank.repository.OperationRepository;
import com.daveo.bank.service.AccountService;
import com.daveo.bank.service.OperationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.*;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(MockitoJUnitRunner.class)
public class OperationServiceTests {

    @Mock
    private OperationRepository operationRepository;
    @Mock
    private AccountService accountService;

    @InjectMocks
    private OperationService operationService;

    @Test
    public void save_money_should_succeed() {

        Account account = new Account();
        account.setName("Account1");
        account.setBalance(0f);

        float amount = 5;

        Operation operation = new Operation();
        operation.setAccount(account);
        operation.setAmount(amount);
        operation.setType(OperationType.DEPOSIT);

        AccountDto accountDto = AccountConverter.entityToDto(account);

        //Mocks
        Mockito.when(operationRepository.save(Mockito.any(Operation.class))).thenReturn(operation);
        Mockito.when(accountService.addToBalance(Mockito.eq(account), Mockito.eq(amount), Mockito.any(OperationType.class)))
                .thenReturn(accountDto);

        OperationDto operationDto = operationService.saveMoney(account, amount);

        assertThat(operationDto).isNotNull();
        assertThat(operationDto)
                .extracting(OperationDto::getAmount, OperationDto::getType)
                .containsExactly(amount, OperationType.DEPOSIT);

        Mockito.verify(accountService).addToBalance(account, amount, OperationType.DEPOSIT);
    }

    @Test
    public void save_money_no_account_should_fail() {

        Account account = null;
        float amount = 5;

        assertThatThrownBy(() -> operationService.saveMoney(account, amount))
                .isInstanceOf(ArgumentsException.class);
    }

    @Test
    public void save_money_negative_amount_should_fail() {

        Account account = new Account();
        float amount = -5;

        assertThatThrownBy(() -> operationService.saveMoney(account, amount))
                .isInstanceOf(ArgumentsException.class);
    }

    @Test
    public void retrieve_money_should_succeed() {

        Account account = new Account();
        account.setName("Account1");
        account.setBalance(20f);

        float amount = 5;
        Operation operation = new Operation();
        operation.setAccount(account);
        operation.setAmount(amount);
        operation.setType(OperationType.WITHDRAWAL);

        AccountDto accountDto = AccountConverter.entityToDto(account);

        //Mocks
        Mockito.when(operationRepository.save(Mockito.any(Operation.class))).thenReturn(operation);
        Mockito.when(accountService.addToBalance(Mockito.eq(account), Mockito.eq(amount), Mockito.any(OperationType.class)))
                .thenReturn(accountDto);

        OperationDto operationDto = operationService.retrieveMoney(account, amount);

        assertThat(operationDto).isNotNull();
        assertThat(operationDto)
                .extracting(OperationDto::getAmount, OperationDto::getType)
                .containsExactly(amount, OperationType.WITHDRAWAL);

        Mockito.verify(accountService).addToBalance(account, amount, OperationType.WITHDRAWAL);
    }

    @Test
    public void retrieve_money_not_enough_balance_should_fail() {

        Account account = new Account();
        account.setName("Account1");
        account.setBalance(10f);

        float amount = 20;

        assertThatThrownBy(() -> operationService.retrieveMoney(account, amount))
                .isInstanceOf(ArgumentsException.class);
    }

    @Test
    public void retrieve_money_no_account_should_fail() {

        Account account = null;
        float amount = 5;

        assertThatThrownBy(() -> operationService.retrieveMoney(account, amount))
                .isInstanceOf(ArgumentsException.class);
    }

    @Test
    public void retrieve_money_negative_amount_should_fail() {

        Account account = new Account();
        float amount = -5;

        assertThatThrownBy(() -> operationService.retrieveMoney(account, amount))
                .isInstanceOf(ArgumentsException.class);
    }

    @Test
    public void list_operations_should_succeed(){

        Account account = new Account();
        Operation operation1 = new Operation();
        operation1.setId(1);
        operation1.setAmount(5f);
        Operation operation2 = new Operation();
        operation2.setId(2);
        operation2.setAmount(10f);

        Page<Operation> operationsPage = new PageImpl<>(Arrays.asList(operation1, operation2));

        Mockito.when(operationRepository.findByAccountIdPaged(Mockito.any(Account.class), Mockito.any(Pageable.class)))
                .thenReturn(operationsPage);

        OperationResponse operationsResponse = operationService.listOperation(account, 0, 5);

        assertThat(operationsResponse).isNotNull();
        assertThat(operationsResponse.getOperations()).isNotNull();
        assertThat(operationsResponse.getOperations().size()).isEqualTo(2);

        assertThat(operationsResponse.getOperations().get(0))
                .extracting(OperationDto::getAmount)
                .containsExactly(5f);

        assertThat(operationsResponse.getOperations().get(1))
                .extracting(OperationDto::getAmount)
                .containsExactly(10f);
    }

    @Test
    public void list_operations_empty_should_succeed(){

        Account account = new Account();
        Page<Operation> operationsPage = Page.empty();

        Mockito.when(operationRepository.findByAccountIdPaged(Mockito.any(Account.class), Mockito.any(Pageable.class)))
                .thenReturn(operationsPage);

        OperationResponse operationsResponse = operationService.listOperation(account, 1, 5);

        assertThat(operationsResponse).isNotNull();
        assertThat(operationsResponse.getTotal()).isEqualTo(0);
    }

}
