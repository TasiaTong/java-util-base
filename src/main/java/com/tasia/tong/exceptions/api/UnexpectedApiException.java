package com.tasia.tong.exceptions.api;

import com.tasia.tong.exceptions.enums.ErrorCode;

public class UnexpectedApiException extends BaseApiException {

    public UnexpectedApiException(String message) {
        super(message);
    }

    public UnexpectedApiException(String message, Exception e) {
        super(message, e);
    }

    @Override
    public Integer getCode() {
        return ErrorCode.API_UNEXPECTED_ERROR.getCode();
    }

    public UnexpectedApiException() {

    }

    public UnexpectedApiException(Throwable cause) {
        super(cause);
    }
}
