package com.daveo.bank.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * The accountDto class.
 *
 * @author baki
 */
@Setter
@Getter
@Builder
public class AccountDto {
    private Integer id;
    private String name;
    private Float balance;
    private Integer customerId;
}
