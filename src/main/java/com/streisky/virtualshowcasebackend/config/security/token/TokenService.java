package com.streisky.virtualshowcasebackend.config.security.token;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.streisky.virtualshowcasebackend.domain.account.entity.Account;
import com.streisky.virtualshowcasebackend.config.security.token.exception.TokenCreationFailedException;
import com.streisky.virtualshowcasebackend.config.security.token.exception.TokenInvalidException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    @Value("${api.security.jwt.secret}")
    private String secret;

    @Value("${api.security.expiration-date.time}")
    private Long time;

    @Value("${api.security.expiration-date.zone}")
    private String zone;

    private static final String ISSUER = "Virtual Showcase";

    public String generate(Authentication authentication) {
        try {
            Account account = (Account) authentication.getPrincipal();
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(account.getLogin())
                    .withIssuedAt(getCurrentDate())
                    .withExpiresAt(getExpirationDate())
                    .sign(getAlgorithm());
        } catch (JWTCreationException e) {
            throw new TokenCreationFailedException();
        }
    }

    public String getSubject(String tokenJWT) {
        try {
            return JWT.require(getAlgorithm())
                    .withIssuer(ISSUER)
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException e) {
            throw new TokenInvalidException();
        }
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(secret);
    }

    private Instant getCurrentDate() {
        return LocalDateTime.now().toInstant(ZoneOffset.of(zone));
    }

    private Instant getExpirationDate() {
        return getCurrentDate().plusSeconds(time);
    }
}
