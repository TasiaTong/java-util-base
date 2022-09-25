package com.tasia.tong.domain.event;

/**
 *
 * @param <T> 事件类型
 */
public interface IEventHandler<T extends IEvent> {

    /**
     * 处理事件
     * @param e 事件
     */
    void handle(T e);
}
