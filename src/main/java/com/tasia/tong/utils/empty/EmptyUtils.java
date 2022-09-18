package com.tasia.tong.utils.empty;

import java.util.Set;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EmptyUtils {

    public boolean isEmpty(IEmptyable var) {
        return var == null || var.isEmpty();
    }

    public <T extends IEmptyable> T getEmptyInstance(T targetObject) {
        EmptyInstanceFactory factory = new EmptyInstanceFactory(targetObject);
        return (T) factory.createObject();
    }

    public <T extends IEmptyable> T getEmptyInstance(T targetObject, Set<String> ignoreMethods) {
        EmptyInstanceFactory factory = new EmptyInstanceFactory(targetObject, ignoreMethods);
        return (T) factory.createObject();
    }
}
