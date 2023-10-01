package com.streisky.virtualshowcasebackend.exception;

public class TokenInvalidException extends RuntimeException {

    public TokenInvalidException() {
        super("Invalid or expired JWT token.");
    }
}
