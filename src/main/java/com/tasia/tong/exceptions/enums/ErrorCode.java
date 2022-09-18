package com.tasia.tong.exceptions.enums;

import com.tasia.tong.basetype.enums.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ErrorCode implements IBaseEnum<Integer, String> {

    /**
     *
     */
    API_UNEXPECTED_ERROR(1001, "Api未知错误"),
    AUTH_FAILED(2001, "鉴权失败"),
    PARAM_INVALID(3001, "参数非法"),
    GATEWAY_ERROR(4001, "外部调用错误"),
    GATEWAY_TIMEOUT_ERROR(4002, "调用超时, 请重试"),
    GATEWAY_BUSINESS_ERROR(4003, "上游业务异常"),
    BUSINESS_ERROR(5001, "业务异常")
    ;

    @Getter
    private final Integer code;

    @Getter
    private final String desc;
}
