package com.tasia.tong.exceptions.api;

import com.tasia.tong.exceptions.enums.ErrorCode;

public class GateWayInvalidException extends BaseApiException {

    @Override
    public Integer getCode() {
        return ErrorCode.GATEWAY_ERROR.getCode();
    }

    public GateWayInvalidException() {

    }

    public GateWayInvalidException(String message) {
        super(message);
    }

    public GateWayInvalidException(String message, Throwable ex) {
        super(message, ex);
    }

    public GateWayInvalidException(Throwable cause) {
        super(cause);
    }
}
