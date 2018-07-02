package com.daveo.bank.service;

import com.daveo.bank.converter.OperationConverter;
import com.daveo.bank.dto.OperationDto;
import com.daveo.bank.dto.OperationResponse;
import com.daveo.bank.entity.Account;
import com.daveo.bank.entity.Operation;
import com.daveo.bank.enums.OperationType;
import com.daveo.bank.exception.ArgumentsException;
import com.daveo.bank.repository.OperationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The OperationRequest service class.
 *
 * @author baki
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OperationService {

    private final OperationRepository repository;
    private final AccountService accountService;

    /**
     * List all the operations related to an account.
     *
     * @param account    The account
     * @param pageNumber The page number
     * @param pageSize   The number of elements by page
     * @return A list of operations
     */
    @Transactional(readOnly=true)
    public OperationResponse listOperation(final Account account, final int pageNumber, final int pageSize) {

        PageRequest request = PageRequest.of(pageNumber, pageSize, Sort.Direction.DESC, "id");
        Page<Operation> pagedOperations = repository.findByAccountIdPaged(account, request);

        List<OperationDto> operations =  pagedOperations.getContent().stream()
                .map(OperationConverter::entityToDto)
                .collect(Collectors.toList());

        return new OperationResponse(pagedOperations.getTotalElements(), operations);
    }

    /**
     * Make deposit in an account
     *
     * @param account The account
     * @param amount  The amount of money
     * @return A {@link OperationDto} object
     */
    @Transactional
    public OperationDto saveMoney(Account account, float amount) {
        checkInputs(account, amount);

        Operation operation = new Operation();
        operation.setAccount(account);
        operation.setAmount(amount);
        operation.setOperationDate(LocalDateTime.now());
        operation.setType(OperationType.DEPOSIT);

        Operation operationEntity = repository.save(operation);
        account.getOperations().add(operationEntity);
        accountService.addToBalance(account, amount, OperationType.DEPOSIT);
        return OperationConverter.entityToDto(operationEntity);
    }

    /**
     * Retrieve money from an account
     *
     * @param account The account
     * @param amount  The amount of money
     * @return A {@link OperationDto} object
     */
    @Transactional
    public OperationDto retrieveMoney(Account account, float amount) {
        checkInputs(account, amount);
        float currentBalance = account.getBalance();
        if (amount > currentBalance) {
            log.error("The amount to retrieve is bigger than the current balance");
            throw new ArgumentsException("The amount to retrieve is bigger than the current balance");
        }
        Operation operation = new Operation();
        operation.setAccount(account);
        operation.setAmount(amount);
        operation.setOperationDate(LocalDateTime.now());
        operation.setType(OperationType.WITHDRAWAL);
        Operation operationEntity = repository.save(operation);
        account.getOperations().add(operationEntity);
        accountService.addToBalance(account, amount, OperationType.WITHDRAWAL);
        return OperationConverter.entityToDto(operationEntity);
    }

    /**
     * Check if the input parameters are well defined.
     *
     * @param account The account
     * @param amount  The amount
     */
    private void checkInputs(Account account, float amount) {
        if (account == null) {
            log.error("No account provided");
            throw new ArgumentsException("No Account provided");
        }
        if (amount <= 0) {
            log.error("The amount to add is a negative value");
            throw new ArgumentsException("The amount is not valid");
        }
    }
}
