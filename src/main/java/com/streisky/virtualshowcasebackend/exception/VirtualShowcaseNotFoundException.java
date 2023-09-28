package com.streisky.virtualshowcasebackend.exception;

public class VirtualShowcaseNotFoundException extends RuntimeException {

    public VirtualShowcaseNotFoundException(Long id) {
        super("No record was found with the ID " + id);
    }
}
