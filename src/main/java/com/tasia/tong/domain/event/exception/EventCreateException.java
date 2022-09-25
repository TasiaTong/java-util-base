package com.tasia.tong.domain.event.exception;

import com.tasia.tong.exceptions.domain.BaseDomainException;

public class EventCreateException extends BaseDomainException {

    public EventCreateException(String message) {
        super(message);
    }

    public EventCreateException(String message, Throwable e) {
        super(message, e);
    }

    public EventCreateException(String message, String bizMessage, Throwable e) {
        super(message, bizMessage, e);
    }

    public EventCreateException(String message, String bizMessage) {
        super(message, bizMessage);
    }
}
