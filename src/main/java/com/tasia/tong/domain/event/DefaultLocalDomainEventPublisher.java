package com.tasia.tong.domain.event;

import javax.annotation.Resource;

public class DefaultLocalDomainEventPublisher implements IDomainEventPublisher {

    @Resource
    private EventBus eventBus;

    @Override
    public void publish(BaseDomainEvent event) {
        eventBus.dispatch(event);
    }
}
