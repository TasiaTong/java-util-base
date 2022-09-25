package com.tasia.tong.domain.event;

import com.tasia.tong.domain.event.BaseDomainEvent;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.stereotype.Component;

@Inherited
@Component
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventHandler {

    Class<? extends BaseDomainEvent>[] value() default {};
}
