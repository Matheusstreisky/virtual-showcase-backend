package com.streisky.virtualshowcasebackend.repository.account;

import com.streisky.virtualshowcasebackend.entity.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByLogin(String login);
}
