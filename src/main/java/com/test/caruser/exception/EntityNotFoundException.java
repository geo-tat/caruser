package com.test.caruser.exception;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException {
   private final String message;

    public EntityNotFoundException(String message) {
        this.message = message;
    }
}
