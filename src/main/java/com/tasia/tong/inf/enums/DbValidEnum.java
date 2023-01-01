package com.tasia.tong.inf.enums;

import com.tasia.tong.basetype.enums.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum DbValidEnum implements IBaseEnum<Integer, String> {

    /**
     * 数据合法标识
     */
    VALID(1, "合法"),
    INVALID(0, "删除")
    ;

    @Getter
    private final Integer code;

    @Getter
    private final String desc;
}
