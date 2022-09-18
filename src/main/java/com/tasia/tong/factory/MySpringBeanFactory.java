package com.tasia.tong.factory;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class MySpringBeanFactory implements ApplicationContextAware {

    private static ApplicationContext context;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        synchronized (this) {
            if (MySpringBeanFactory.context == null) {
                MySpringBeanFactory.context = applicationContext;
            }
        }
    }
}
