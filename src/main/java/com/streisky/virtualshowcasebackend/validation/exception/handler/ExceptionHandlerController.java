package com.streisky.virtualshowcasebackend.validation.exception.handler;

import java.util.List;

import com.streisky.virtualshowcasebackend.validation.exception.dto.ValidationErrorDTO;
import com.streisky.virtualshowcasebackend.exception.VirtualShowcaseNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(VirtualShowcaseNotFoundException.class)
    public ResponseEntity<Void> handleNotFoundError() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationErrorDTO>> handleBadRequestError(MethodArgumentNotValidException ex) {
        List<FieldError> errors = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(errors.stream().map(ValidationErrorDTO::new).toList());
    }
}
