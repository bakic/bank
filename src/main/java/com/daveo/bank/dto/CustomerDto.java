package com.daveo.bank.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * The customer dto class.
 *
 * @author baki
 */
@Setter
@Getter
@Builder
public class CustomerDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
}
