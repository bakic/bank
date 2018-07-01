package com.daveo.bank.converter;

import com.daveo.bank.dto.AccountDto;
import com.daveo.bank.entity.Account;

/**
 * Utility class to handle Account entity and AccountDto.
 *
 * @author baki
 */
public class AccountConverter {

    /**
     * Convert an {@link Account} object to a {@link AccountDto} object.
     *
     * @param account A {@link Account} object
     * @return A {@link AccountDto} object
     */
    public static AccountDto entityToDto(Account account) {
        if (account == null) return null;
        return AccountDto.builder()
                .id(account.getId())
                .name(account.getName())
                .balance(account.getBalance())
                .build();
    }

    private AccountConverter() {
        throw new IllegalStateException("Utility class");
    }
}
