package com.tasia.tong.domain.event;

import java.util.Objects;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 事件总线, 通过时间总线分发事件
 *
 */
@Slf4j
@Component
public class EventBus {

    @Resource
    private EventHub eventHub;

    /**
     * 分发事件
     *
     * @param event
     * @return
     */
    public boolean dispatch(IEvent event) {
        for (IEventHandler handler : eventHub.getEventHandler(event.getClass())) {
            try {
               handler.handle(event);
            } catch (Exception e) {
                log.error("事件分发器进行事件分发时出错, 处理器: {}, 事件: {}, 错误详情: ", handler, event, e);
                return false;
            }
        }
        return true;
    }

    /**
     * 分发事件
     *
     * @param event
     * @return
     */
    public boolean dispatch(IEvent event, Class<? extends IEventHandler> handlerClazz) {
        for (IEventHandler handler : eventHub.getEventHandler(event.getClass())) {
            try {
                if (Objects.equals(handlerClazz, handler.getClass())) {
                    handler.handle(event);
                }
            } catch (Exception e) {
                log.error("事件分发器进行事件分发时出错, 处理器: {}, 事件: {}, 错误详情: ", handler, event, e);
                return false;
            }
        }
        return true;
    }

}
