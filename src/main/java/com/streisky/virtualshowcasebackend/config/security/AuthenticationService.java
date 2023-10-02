package com.streisky.virtualshowcasebackend.config.security;

import java.util.Optional;

import com.streisky.virtualshowcasebackend.entity.account.Account;
import com.streisky.virtualshowcasebackend.repository.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private AccountRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> optionalAccount = userRepository.findByLogin(username);

        if (optionalAccount.isEmpty()) {
            throw new UsernameNotFoundException("Invalid credentials.");
        }

        return optionalAccount.get();
    }
}
