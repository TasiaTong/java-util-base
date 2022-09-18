package com.tasia.tong.basetype.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum OrderDirectionEnum implements IBaseEnum<String, String> {

    /**
     *
     */
    ASC("ASC", "升序"),

    DESC("DESC", "降序");

    @Getter
    private final String code;

    @Getter
    private final String desc;
}
