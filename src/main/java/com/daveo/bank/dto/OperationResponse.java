package com.daveo.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Operation response class.
 *
 * @author baki
 */
@Data
@AllArgsConstructor
public class OperationResponse {
    private long total;
    private List<OperationDto> operations;
}
