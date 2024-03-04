package com.test.caruser.exception;

import lombok.Getter;

@Getter
public class EmailNotUniqueException extends RuntimeException {
    private final String message;
    public EmailNotUniqueException(String message) {
        this.message = message;
    }
}
