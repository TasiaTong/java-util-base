package com.tasia.tong.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;

@UtilityClass
public class MiscUtils {

    public static final String LIST_SPLITTER = ",";

    public void sleep(int mill) {
        try {
            TimeUnit.MILLISECONDS.sleep(mill);
        } catch (InterruptedException e) {
        }
    }

    public void sleepWithSecond(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
        }
    }

    /**
     * 慎用
     * @param obj
     * @param clazz
     * @return
     * @param <T>
     */
    public <T> T deepCopy(T obj, Class<T> clazz) {
        return JsonUtils.parseSimpleObject(JsonUtils.toJsonString(obj), clazz);
    }

    public String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public <T, R> String convertListToString(List<T> list, Function<T, R> func) {
        return ListUtils.emptyIfNull(list).stream()
                .map(func)
                .map(String::valueOf)
                .collect(Collectors.joining(LIST_SPLITTER));
    }

    public <T> String convertListToString(List<T> list) {
        return convertListToString(list, Object::toString);
    }

    public <T> List<T> convertStringToList(String string, Function<String, T> func) {
        if (StringUtils.isBlank(string)) {
            return new ArrayList<>();
        }
        return Arrays.stream(string.split(LIST_SPLITTER))
                .map(func)
                .collect(Collectors.toList());
    }

    /**
     * list对象转换
     * @param list
     * @param func
     * @return
     * @param <T>
     * @param <R>
     */
    public <T, R> List<R> transformCollections(final Collection<T> list, Function<T, R> func) {
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        int size = list.size();
        List<R> result = new ArrayList<>(size);
        for (T t : list) {
            if (t != null) {
                R element = func.apply(t);
                result.add(element);
            }
        }
        return result;
    }
}
