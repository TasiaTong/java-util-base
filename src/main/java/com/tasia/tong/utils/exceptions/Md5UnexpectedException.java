package com.tasia.tong.utils.exceptions;

public class Md5UnexpectedException extends RuntimeException {

    public Md5UnexpectedException(String message) {
        super(message);
    }

    public Md5UnexpectedException(String message, Exception e) {
        super(message, e);
    }

    public Md5UnexpectedException(Throwable e) {
        super(e);
    }
}
