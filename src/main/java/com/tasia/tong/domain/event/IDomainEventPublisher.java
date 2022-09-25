package com.tasia.tong.domain.event;

/**
 * 领域事件发布接口
 *
 */
public interface IDomainEventPublisher {

    /**
     * 发布领域事件
     *
     * @param event
     */
    void publish(BaseDomainEvent event);
}
