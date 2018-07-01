package com.daveo.bank.converter;

import com.daveo.bank.dto.CustomerDto;
import com.daveo.bank.entity.Customer;

/**
 * Utility class to handle Customer entity and CustomerDto.
 *
 * @author baki
 */
public class CustomerConverter {

    /**
     * Convert an {@link Customer} object to a {@link CustomerDto} object.
     *
     * @param customer A {@link Customer} object
     * @return A {@link CustomerDto} object
     */
    public static CustomerDto entityToDto(Customer customer) {
        if (customer == null) {
            return null;
        }

        return CustomerDto.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .build();
    }

    private CustomerConverter() {
        throw new IllegalStateException("Utility class");
    }
}
