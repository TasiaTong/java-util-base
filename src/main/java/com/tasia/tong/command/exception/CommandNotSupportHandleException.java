package com.tasia.tong.command.exception;

import com.tasia.tong.exceptions.domain.BaseDomainException;

public class CommandNotSupportHandleException extends BaseDomainException {

    public CommandNotSupportHandleException(String message) {
        super(message);
    }

    public CommandNotSupportHandleException(String message, Throwable e) {
        super(message, e);
    }

    public CommandNotSupportHandleException(String message, String bizMessage, Throwable e) {
        super(message, bizMessage, e);
    }

    public CommandNotSupportHandleException(String message, String bizMessage) {
        super(message, bizMessage);
    }
}
