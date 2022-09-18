package com.tasia.tong.basetype.enums;

public interface IBaseEnum<T1, T2> {

    /**
     * 获取对应的code
     *
     * @return
     */
    T1 getCode();

    /**
     * 获取枚举的描述信息
     *
     * @return
     */
    default T2 getDesc() {
        return null;
    }
}
