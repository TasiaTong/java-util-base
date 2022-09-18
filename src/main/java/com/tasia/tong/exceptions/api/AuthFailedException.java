package com.tasia.tong.exceptions.api;

import com.tasia.tong.exceptions.enums.ErrorCode;

public class AuthFailedException extends BaseApiException {

    @Override
    public Integer getCode() {
        return ErrorCode.AUTH_FAILED.getCode();
    }

    public AuthFailedException() {

    }

    public AuthFailedException(String message) {
        super(message);
    }

    public AuthFailedException(String message, Throwable e) {
        super(message, e);
    }

    public AuthFailedException(Throwable cause) {
        super(cause);
    }
}
