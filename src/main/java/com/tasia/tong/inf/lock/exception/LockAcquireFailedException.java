package com.tasia.tong.inf.lock.exception;

public class LockAcquireFailedException extends RuntimeException {

    public LockAcquireFailedException(String message) {
        super(message);
    }

    public LockAcquireFailedException(String message, Throwable e) {
        super(message, e);
    }
}
