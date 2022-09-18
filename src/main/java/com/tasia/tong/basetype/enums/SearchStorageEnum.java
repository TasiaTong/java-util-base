package com.tasia.tong.basetype.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@AllArgsConstructor
public enum SearchStorageEnum implements IBaseEnum<String, String> {

    /**
     *
     */
    ES("es", "ES", "*"),
    MYSQL("mysql", "MYSQL", "%")
    ;

    @Getter
    private final String code;

    @Getter
    private final String desc;

    @Getter
    private final String wildCard;

    public String wrapWildCard(String value) {
        return getWildCard() + transferStarSign(value) + getWildCard();
    }

    private String transferStarSign(String name) {
        if (StringUtils.isBlank(name)) {
            return StringUtils.EMPTY;
        }
        return name.replace("*", "\\*").replace("?", "\\?");
    }
}
