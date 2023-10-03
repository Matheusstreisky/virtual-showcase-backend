package com.streisky.virtualshowcasebackend.config.validation.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(Long id) {
        super("No record was found with the ID " + id);
    }
}
