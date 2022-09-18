package com.tasia.tong.utils.exceptions;

public class JsonProcessException extends RuntimeException {

    public JsonProcessException(String message) {
        super(message);
    }

    public JsonProcessException(String message, Exception e) {
        super(message, e);
    }

    public JsonProcessException(Throwable e) {
        super(e);
    }
}
