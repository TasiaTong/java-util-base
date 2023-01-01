package com.tasia.tong.inf.lock;

import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.collections4.ListUtils;

public interface ILockable {

    /**
     * 用来作为分布式锁的分隔符
     */
    public static final String JOIN_SPLITTER = "_";

    /**
     * 获取key的组成部分
     * @return
     */
    List<String> generateLockPartKeys();


    /**
     *
     * @return
     */
    default String getLockKey() {
        return ListUtils.emptyIfNull(generateLockPartKeys()).stream()
                .collect(Collectors.joining(JOIN_SPLITTER));
    }
}
