package com.tasia.tong.command.exception;

import com.tasia.tong.exceptions.domain.BaseDomainException;

public class CommandInvalidException extends BaseDomainException {

    public CommandInvalidException(String message) {
        super(message);
    }

    public CommandInvalidException(String message, Throwable e) {
        super(message, e);
    }

    public CommandInvalidException(String message, String bizMessage, Throwable e) {
        super(message, bizMessage, e);
    }

    public CommandInvalidException(String message, String bizMessage) {
        super(message, bizMessage);
    }
}
