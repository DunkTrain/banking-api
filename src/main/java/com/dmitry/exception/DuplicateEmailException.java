package com.dmitry.exception;

public class DuplicateEmailException extends RuntimeException{

    public DuplicateEmailException(String email) {
        super("Email уже занят: " + email);
    }
}
