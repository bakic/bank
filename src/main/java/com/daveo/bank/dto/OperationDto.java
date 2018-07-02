package com.daveo.bank.dto;

import com.daveo.bank.enums.OperationType;
import lombok.*;

import java.time.LocalDateTime;

/**
 * The OperationRequest dto class.
 *
 * @author baki
 */
@Setter
@Getter
@Builder
public class OperationDto {
    private LocalDateTime operationDate;
    private float amount;
    private OperationType type;

}
