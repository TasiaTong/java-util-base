package spock.supoort.annotation;

import groovy.lang.Closure;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.spockframework.runtime.extension.ExtensionAnnotation;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@ExtensionAnnotation(MyDbUnitExtension.class)
public @interface MyDbUnit {
    Class<? extends Closure> content() default Closure.class;

    String xmlLocation() default "";

    String csvLocation() default "";
}
