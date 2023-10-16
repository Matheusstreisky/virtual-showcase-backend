package com.streisky.virtualshowcasebackend.constants;

import com.streisky.virtualshowcasebackend.domain.account.dto.AccountDTO;
import com.streisky.virtualshowcasebackend.domain.account.entity.Account;

public class AccountTestsConstants {

    public static final String LOGIN = "Teste";
    public static final String PASSWORD = "123";
    public static final String ENCRYPTED_PASSWORD = "$2a$12$8oUjHn0DUJ/cuufvnr7zPuA65nGNE/V7INlOxki.wa5IhXOdpSQwq";
    public static final String INVALID_LOGIN = "Invalid login";
    public static final String INVALID_PASSWORD = "Invalid password";

    public static final Account ACCOUNT = new Account(1L, LOGIN, ENCRYPTED_PASSWORD);
    public static final AccountDTO ACCOUNT_DTO = new AccountDTO(LOGIN, PASSWORD);
}
