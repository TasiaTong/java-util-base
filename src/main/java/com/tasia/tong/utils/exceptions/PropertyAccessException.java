package com.tasia.tong.utils.exceptions;

public class PropertyAccessException extends RuntimeException {

    public PropertyAccessException(String message) {
        super(message);
    }

    public PropertyAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
