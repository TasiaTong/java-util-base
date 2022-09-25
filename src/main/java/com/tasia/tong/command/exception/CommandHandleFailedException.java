package com.tasia.tong.command.exception;

import com.tasia.tong.exceptions.domain.BaseDomainException;

public class CommandHandleFailedException extends BaseDomainException {

    public CommandHandleFailedException(String message) {
        super(message);
    }

    public CommandHandleFailedException(String message, Throwable e) {
        super(message, e);
    }

    public CommandHandleFailedException(String message, String bizMessage, Throwable e) {
        super(message, bizMessage, e);
    }

    public CommandHandleFailedException(String message, String bizMessage) {
        super(message, bizMessage);
    }
}
