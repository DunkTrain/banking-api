package com.dmitry.exception;

public class DuplicatePhoneException extends RuntimeException {
    public DuplicatePhoneException(String phone) {
        super("Phone уже занят " + phone);
    }
}
