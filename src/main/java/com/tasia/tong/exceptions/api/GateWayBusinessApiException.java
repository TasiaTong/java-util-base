package com.tasia.tong.exceptions.api;

import com.tasia.tong.exceptions.enums.ErrorCode;

public class GateWayBusinessApiException extends BaseApiException {

    @Override
    public Integer getCode() {
        return ErrorCode.GATEWAY_BUSINESS_ERROR.getCode();
    }

    public GateWayBusinessApiException() {

    }

    public GateWayBusinessApiException(String message) {
        super(message);
    }

    public GateWayBusinessApiException(String message, Throwable ex) {
        super(message, ex);
    }

    public GateWayBusinessApiException(Throwable cause) {
        super(cause);
    }
}
