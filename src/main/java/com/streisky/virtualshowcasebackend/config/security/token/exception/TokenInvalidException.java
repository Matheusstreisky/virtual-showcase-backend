package com.streisky.virtualshowcasebackend.config.security.token.exception;

public class TokenInvalidException extends RuntimeException {

    public TokenInvalidException() {
        super("Invalid or expired JWT token.");
    }
}
