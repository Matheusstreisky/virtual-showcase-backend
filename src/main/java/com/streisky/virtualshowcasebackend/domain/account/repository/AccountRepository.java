package com.streisky.virtualshowcasebackend.domain.account.repository;

import java.util.Optional;

import com.streisky.virtualshowcasebackend.domain.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByLogin(String login);
}
