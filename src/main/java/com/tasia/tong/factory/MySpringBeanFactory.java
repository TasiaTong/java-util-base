package com.tasia.tong.factory;

import java.util.Map;
import javax.validation.constraints.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class MySpringBeanFactory implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext)
            throws BeansException {
        synchronized (this) {
            if (MySpringBeanFactory.context == null) {
                MySpringBeanFactory.context = applicationContext;
            }
        }
    }

    /**
     * 创建一个空的bean对象, 用于prototype的空对象创建
     * @param requiredClass
     * @return
     * @param <T>
     */
    @Deprecated
    public static <T> T createEmptyBean(Class<T> requiredClass) {
        return context.getBean(requiredClass);
    }

    /**
     *
     * @param requiredClass
     * @return
     * @param <T>
     */
    public static <T> T getProxy(Class<T> requiredClass){
        return context.getBean(requiredClass);
    }


    public static <T> T getProxy(String name) {
        return (T) context.getBean(name);
    }

    public static <T> Map<String, T> getProxyMap(Class<T> requiredCLass) throws BeansException {
        return context.getBeansOfType(requiredCLass);
    }

    public static void publishEvent(Object message) {
        context.publishEvent(message);
    }
}
