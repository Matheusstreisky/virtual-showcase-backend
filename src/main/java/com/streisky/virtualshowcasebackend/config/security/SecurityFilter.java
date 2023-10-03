package com.streisky.virtualshowcasebackend.config.security;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import com.streisky.virtualshowcasebackend.config.security.token.TokenService;
import com.streisky.virtualshowcasebackend.domain.account.entity.Account;
import com.streisky.virtualshowcasebackend.domain.account.repository.AccountRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenJWT(request);

        if (Objects.nonNull(token)) {
            String subject = tokenService.getSubject(token);
            Optional<Account> optionalAccount = accountRepository.findByLogin(subject);

            if (optionalAccount.isPresent()) {
                Account account = optionalAccount.get();
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(account, null, account.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenJWT(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        return Objects.nonNull(authorizationHeader)
                ? authorizationHeader.replace("Bearer ", "")
                : null;
    }
}
