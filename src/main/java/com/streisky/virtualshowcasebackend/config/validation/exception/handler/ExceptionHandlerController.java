package com.streisky.virtualshowcasebackend.config.validation.exception.handler;

import java.util.List;

import com.streisky.virtualshowcasebackend.config.validation.exception.dto.ErrorDTO;
import com.streisky.virtualshowcasebackend.config.validation.exception.dto.ValidationErrorDTO;
import com.streisky.virtualshowcasebackend.exception.VirtualShowcaseNotFoundException;
import org.springframework.http.HttpStatus;
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
    public List<ValidationErrorDTO> handleBadRequestError(MethodArgumentNotValidException ex) {
        List<FieldError> errors = ex.getFieldErrors();
        return errors.stream().map(ValidationErrorDTO::new).toList();
    }
}
