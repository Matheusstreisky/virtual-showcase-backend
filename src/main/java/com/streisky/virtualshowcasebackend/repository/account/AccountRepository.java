package com.streisky.virtualshowcasebackend.repository.account;

import java.util.Optional;

import com.streisky.virtualshowcasebackend.entity.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByLogin(String login);
}
