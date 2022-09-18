package com.tasia.tong.utils.exceptions;

public class FutureCreateException extends RuntimeException {

    public FutureCreateException(String message) {
        super(message);
    }

    public FutureCreateException(String message, Exception e) {
        super(message, e);
    }

    public FutureCreateException(Throwable e) {
        super(e);
    }
}
