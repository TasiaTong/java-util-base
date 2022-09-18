package com.tasia.tong.utils.exceptions;

public class NumberInvalidException extends RuntimeException {

    public NumberInvalidException(String message) {
        super(message);
    }

    public NumberInvalidException(String message, Exception e) {
        super(message, e);
    }

    public NumberInvalidException(Throwable e) {
        super(e);
    }
}
