package com.tasia.tong.exceptions.api;

import com.tasia.tong.exceptions.enums.ErrorCode;

public class BusinessErrorApiException extends BaseApiException {

    @Override
    public Integer getCode() {
        return ErrorCode.BUSINESS_ERROR.getCode();
    }

    public BusinessErrorApiException() {

    }

    public BusinessErrorApiException(String message) {
        super(message);
    }

    public BusinessErrorApiException(String message, Throwable ex) {
        super(message, ex);
    }

    public BusinessErrorApiException(Throwable cause) {
        super(cause);
    }
}
