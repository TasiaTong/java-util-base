package com.tasia.tong.exceptions.api;

import com.tasia.tong.exceptions.enums.ErrorCode;

public class GateWayTimeoutException extends BaseApiException {

    @Override
    public Integer getCode() {
        return ErrorCode.GATEWAY_TIMEOUT_ERROR.getCode();
    }

    public GateWayTimeoutException() {

    }

    public GateWayTimeoutException(String message) {
        super(message);
    }

    public GateWayTimeoutException(String message, Throwable ex) {
        super(message, ex);
    }

    public GateWayTimeoutException(Throwable cause) {
        super(cause);
    }

}
