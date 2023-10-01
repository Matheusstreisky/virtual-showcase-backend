package com.streisky.virtualshowcasebackend.config.validation.exception.handler;

import java.util.List;

import com.streisky.virtualshowcasebackend.config.validation.exception.dto.ErrorDTO;
import com.streisky.virtualshowcasebackend.config.validation.exception.dto.ValidationErrorDTO;
import com.streisky.virtualshowcasebackend.config.security.token.exception.TokenCreationFailedException;
import com.streisky.virtualshowcasebackend.config.security.token.exception.TokenInvalidException;
import com.streisky.virtualshowcasebackend.exception.VirtualShowcaseNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(VirtualShowcaseNotFoundException.class)
    public ErrorDTO handleNotFoundError(VirtualShowcaseNotFoundException ex) {
        return new ErrorDTO(ex.getMessage());
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ValidationErrorDTO> handleValidationError(MethodArgumentNotValidException ex) {
        List<FieldError> errors = ex.getFieldErrors();
        return errors.stream().map(ValidationErrorDTO::new).toList();
    }

    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public ErrorDTO handleAuthenticationError() {
        return new ErrorDTO("Authentication failed.");
    }

    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ErrorDTO handleAccessDeniedError() {
        return new ErrorDTO("Access denied.");
    }

    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    @ExceptionHandler({TokenCreationFailedException.class, TokenInvalidException.class})
    public ErrorDTO handleTokenJWTError(Exception ex) {
        return new ErrorDTO(ex.getMessage());
    }

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorDTO handleUnexpectedError(Exception ex) {
        return new ErrorDTO("Error: " + ex.getMessage());
    }
}
