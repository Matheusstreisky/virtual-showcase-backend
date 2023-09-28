package com.streisky.virtualshowcasebackend.exception;

public class VirtualShowcaseNotFoundException extends Exception {

    public VirtualShowcaseNotFoundException(Long id) {
        super("No record was found with the ID " + id);
    }
}
