package com.tasia.tong.utils;

import com.tasia.tong.basetype.enums.IBaseEnum;
import java.util.Objects;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EnumUtils {

    public <E extends Enum<?> & IBaseEnum> E codeOf(Class<E> enumClass, Object value) {
        if (value == null) {
            return null;
        }
        E[] enumConstants = enumClass.getEnumConstants();
        for (E e : enumConstants) {
            if (compareValueWithCode(value, e.getCode())) {
                return e;
            }
        }
        return null;
    }

    public <E extends Enum<?> & IBaseEnum> E nameOf(Class<E> enumClass, String name) {
        E[] enumConstants = enumClass.getEnumConstants();
        for (E e : enumConstants) {
            if (e.name().equalsIgnoreCase(name)) {
                return e;
            }
        }
        return null;
    }

    public <T1, T2, E extends Enum<?> & IBaseEnum<T1, T2>> T2 mapCodeToDesc(Class<E> enumClass, T1 t1) {
        E[] enumConstants = enumClass.getEnumConstants();
        for (E e : enumConstants) {
            if (Objects.equals(e.getCode(), t1)) {
                return e.getDesc();
            }
        }
        return null;
    }

    private boolean compareValueWithCode(Object value, Object code) {
        if (value instanceof Integer || value instanceof String || value instanceof Long) {
            boolean ok = value.equals(code);
            if (ok) {
                return true;
            }
        }
        return value.toString().equals(code.toString());
    }
}
