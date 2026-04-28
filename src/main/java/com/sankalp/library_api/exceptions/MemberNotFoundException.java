package com.sankalp.library_api.exceptions;

public class MemberNotFoundException extends RuntimeException{
    public MemberNotFoundException(Long id) {
        super("Could not find Member with ID: " + id);
    }
}
