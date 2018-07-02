package com.daveo.bank.converter;

import com.daveo.bank.dto.OperationDto;
import com.daveo.bank.entity.Operation;

/**
 * Utility class to handle OperationRequest entity and OperationDto.
 *
 * @author baki
 */
public class OperationConverter {

    /**
     * Convert an {@link Operation} object to a {@link OperationDto} object.
     *
     * @param operation A {@link Operation} object
     * @return A {@link OperationDto} object
     */
    public static OperationDto entityToDto(Operation operation) {
        if (operation == null) {
            return null;
        }
        return OperationDto.builder()
                .amount(operation.getAmount())
                .operationDate(operation.getOperationDate())
                .type(operation.getType())
                .build();
    }

    private OperationConverter() {
        throw new IllegalStateException("Utility class");
    }
}
