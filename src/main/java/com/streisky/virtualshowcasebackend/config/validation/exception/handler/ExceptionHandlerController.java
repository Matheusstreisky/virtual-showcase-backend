package com.streisky.virtualshowcasebackend.config.validation.exception.handler;

import java.util.List;

import com.streisky.virtualshowcasebackend.config.security.token.exception.TokenCreationFailedException;
import com.streisky.virtualshowcasebackend.config.security.token.exception.TokenInvalidException;
import com.streisky.virtualshowcasebackend.config.validation.exception.ResourceNotFoundException;
import com.streisky.virtualshowcasebackend.config.validation.exception.dto.ErrorDTO;
import com.streisky.virtualshowcasebackend.config.validation.exception.dto.ValidationErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorDTO handleNotFoundError(ResourceNotFoundException ex) {
        return new ErrorDTO(ex.getMessage());
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ValidationErrorDTO> handleValidationError(MethodArgumentNotValidException ex) {
        List<FieldError> errors = ex.getFieldErrors();
        return errors.stream().map(ValidationErrorDTO::new).toList();
    }

    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    @ExceptionHandler({TokenCreationFailedException.class, TokenInvalidException.class})
    public ErrorDTO handleTokenJWTError(Exception ex) {
        return new ErrorDTO(ex.getMessage());
    }

}
