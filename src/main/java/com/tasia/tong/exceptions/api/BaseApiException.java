package com.tasia.tong.exceptions.api;

abstract
public class BaseApiException extends RuntimeException {

    abstract public Integer getCode();

    public BaseApiException() {

    }

    public BaseApiException(String message) {
        super(message);
    }

    public BaseApiException(String message, Throwable e) {
        super(message, e);
    }

    public BaseApiException(Throwable cause) {
        super(cause);
    }
}
