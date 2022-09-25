package com.tasia.tong.domain.event.exception;

import com.tasia.tong.exceptions.domain.BaseDomainException;

public class EventRegisterException extends BaseDomainException {

    public EventRegisterException(String message) {
        super(message);
    }

    public EventRegisterException(String message, Throwable e) {
        super(message, e);
    }

    public EventRegisterException(String message, String bizMessage, Throwable e) {
        super(message, bizMessage, e);
    }

    public EventRegisterException(String message, String bizMessage) {
        super(message, bizMessage);
    }
}
