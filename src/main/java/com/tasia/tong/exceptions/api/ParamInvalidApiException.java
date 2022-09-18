package com.tasia.tong.exceptions.api;

import com.tasia.tong.exceptions.enums.ErrorCode;

public class ParamInvalidApiException extends BaseApiException {

    @Override
    public Integer getCode() {
        return ErrorCode.PARAM_INVALID.getCode();
    }

    public ParamInvalidApiException() {

    }

    public ParamInvalidApiException(String message) {
        super(message);
    }

    public ParamInvalidApiException(String message, Throwable ex) {
        super(message, ex);
    }

    public ParamInvalidApiException(Throwable cause) {
        super(cause);
    }
}
