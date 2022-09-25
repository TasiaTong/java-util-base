package com.tasia.tong.exceptions.domain;

import lombok.Getter;

public class BaseDomainException extends RuntimeException {

    @Getter
    private String bizMessage;

    public BaseDomainException(String message) {
        super(message);
    }

    public BaseDomainException(String message, Throwable e) {
        super(message, e);
    }

    public BaseDomainException(String message, String bizMessage, Throwable e) {
        super(message, e);
        this.bizMessage = bizMessage;
    }

    public BaseDomainException(String message, String bizMessage) {
        super(message);
        this.bizMessage = bizMessage;
    }
}
