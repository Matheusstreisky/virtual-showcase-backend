package com.streisky.virtualshowcasebackend.validation.exception.dto;

public record ErrorDTO(String message) {

    public ErrorDTO(String message) {
        this.message = message;
    }
}
