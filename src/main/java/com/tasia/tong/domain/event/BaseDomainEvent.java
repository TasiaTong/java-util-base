package com.tasia.tong.domain.event;

import com.tasia.tong.factory.MySpringBeanFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseDomainEvent implements IEvent {

    public IDomainEventPublisher getCustomDomainEventPublisher() {
        return null;
    }

    public void publish() {
        IDomainEventPublisher publisher = getCustomDomainEventPublisher();
        if (publisher == null) {
            publisher = MySpringBeanFactory.getProxy(DefaultLocalDomainEventPublisher.class);
        }
        log.info("[模块 领域事件][功能 领域事件发布] 即将发布领域事件: {}, 发布器: {}", this, publisher);
    }
}
