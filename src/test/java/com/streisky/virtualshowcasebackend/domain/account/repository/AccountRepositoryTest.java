package com.streisky.virtualshowcasebackend.domain.account.repository;

import java.util.Optional;

import com.streisky.virtualshowcasebackend.domain.account.entity.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void shouldFindExistingUser_AndNotFindNotExistingUser_Test() {
        Account account = new Account(1L, "admin", "password");
        accountRepository.save(account);

        Optional<Account> accountAdmin = accountRepository.findByLogin("admin");
        Optional<Account> accountUser = accountRepository.findByLogin("user");

        Assertions.assertTrue(accountAdmin.isPresent());
        Assertions.assertTrue(accountUser.isEmpty());
    }

}
