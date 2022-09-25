package com.tasia.tong.domain.event;

import com.google.common.collect.Lists;
import com.tasia.tong.domain.event.exception.EventRegisterException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 事件处理中心
 * 用来注册和管理事件与事件处理器之间的关联关系
 */
@Slf4j
@Component
public class EventHub {

    private static final String HANDLE_METHOD_NAME = "handle";

    private final Map<Class<? extends IEvent>, List<IEventHandler<? extends IEvent>>> eventToHandlerRegistryMap =
            new HashMap<>(16);

    @Autowired(required = false)
    private List<IEventHandler<? extends IEvent>> eventHandlers;

    @PostConstruct
    private void init() {
        ListUtils.emptyIfNull(eventHandlers).stream()
                .forEach(this::register);
    }

    public List<IEventHandler<? extends IEvent>> getEventHandler(Class<? extends IEvent> eventClazz) {
        List<IEventHandler<? extends IEvent>> eventHandlers = findHandler(eventClazz);
        if (CollectionUtils.isEmpty(eventHandlers)) {
            throw new EventRegisterException("事件类 " + eventClazz + " 没有注册成功");
        }
        return eventHandlers;
    }

    private List<IEventHandler<? extends IEvent>> findHandler(Class<? extends IEvent> eventClazz) {
        return eventToHandlerRegistryMap.get(eventClazz);
    }

    private void register(IEventHandler<? extends IEvent> eventHandler) {
        List<Class<? extends IEvent>> eventClassList = getEventFromEventHandler(eventHandler.getClass());

    }

    private List<Class<? extends IEvent>> getEventFromEventHandler(
            Class<? extends IEventHandler> eventExecutorClazz) {
        EventHandler annotation = eventExecutorClazz.getAnnotation(EventHandler.class);

        if (annotation != null) {
            Class<? extends BaseDomainEvent>[] values = annotation.value();
            if (values.length != 0) {
                return Lists.newArrayList(values);
            }
        }
        List<Class<? extends IEvent>> eventClassList = getEventClass(eventExecutorClazz);
        if (eventClassList == null) {
            eventClassList = getEventClass(eventExecutorClazz.getSuperclass());
        }
        if (eventClassList != null) {
            return eventClassList;
        }
        throw new EventRegisterException("Event param in " + eventExecutorClazz + " " +
                HANDLE_METHOD_NAME + "() is not detected");
    }

    private List<Class<? extends IEvent>> getEventClass(Class<?> clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (isExecuteMethod(method)) {
                return Collections.singletonList(checkAndGetEventParamType(method));
            }
        }
        return null;
    }

    private Class<? extends IEvent> checkAndGetEventParamType(Method method) {
        Class<? extends IEvent>[] executeParams = (Class<? extends IEvent>[]) method.getParameterTypes();
        if (executeParams.length == 0) {
            throw new EventRegisterException("Execute method in " + method.getDeclaringClass() + " should at least have one parameter");
        }
        if (!IEvent.class.isAssignableFrom(executeParams[0])) {
            throw new EventRegisterException("Execute method in " + method.getDeclaringClass() + "should be the subClass of Event");
        }
        return executeParams[0];
    }

    private boolean isExecuteMethod(Method method) {
        return HANDLE_METHOD_NAME.equals(method.getName())
                && !method.isBridge();
    }
}
