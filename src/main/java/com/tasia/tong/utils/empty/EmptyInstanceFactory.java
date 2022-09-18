package com.tasia.tong.utils.empty;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.apache.commons.collections.SetUtils;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

public class EmptyInstanceFactory implements MethodInterceptor {

    private Set<String> ignoreMethodNames = new HashSet<>();

    private static final String CHECK_EMPTY_METHOD_NAME = "isEmpty";

    private static final String TO_STRING_METHOD_NAME = "toSting";

    private Object targetObject;

    EmptyInstanceFactory(Object targetObject) {
        this.targetObject = targetObject;
    }

    EmptyInstanceFactory(Object targetObject, Set<String> ignoreMethodNames) {
        this.targetObject = targetObject;
        this.ignoreMethodNames = ignoreMethodNames;
    }

    Object createObject() {
        return Enhancer.create(this.targetObject.getClass(), this);
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy)
            throws Throwable {

        if (!(o instanceof IEmptyable)) {
            throw new UnsupportedOperationException("仅支持实现IEmptyable接口的对象");
        }
        if (Objects.equals(CHECK_EMPTY_METHOD_NAME, method.getName())) {
            return Boolean.TRUE;
        }
        if (Objects.equals(TO_STRING_METHOD_NAME, method.getName())) {
            return "EmptyInstance";
        }
        if (ignoreMethodNames.contains(method.getName())) {
            return methodProxy.invokeSuper(o, objects);
        }
        throw new UnsupportedOperationException("不支持的操作");
    }
}
