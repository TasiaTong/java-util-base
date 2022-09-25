package com.tasia.tong.domain.event;

public interface IEvent {

    default String idempotentKey() {
        return null;
    }
}
