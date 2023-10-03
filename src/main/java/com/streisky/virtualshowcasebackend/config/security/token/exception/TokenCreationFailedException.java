package com.streisky.virtualshowcasebackend.config.security.token.exception;

public class TokenCreationFailedException extends RuntimeException {

    public TokenCreationFailedException() {
        super("Error generating JWT token.");
    }
}
