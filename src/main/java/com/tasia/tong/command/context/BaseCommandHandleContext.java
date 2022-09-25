package com.tasia.tong.command.context;

import com.tasia.tong.command.exception.CommandInvalidException;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;

@Data
public class BaseCommandHandleContext {

    /**
     * 指令的执行环境
     */
    private Map<String, Object> context;


    /**
     * 获取环境的值
     *
     * @param value
     * @return
     */
    public Object getValue(String value) {
        return getValue(value, null);
    }

    /**
     * 获取环境的值
     *
     * @param key
     * @return
     */
    private Object getValue(String key, Object defaultValue) {
        if (context == null) {
            return null;
        }
        return context.getOrDefault(key, defaultValue);
    }


    public static <T extends BaseCommandHandleContext> T of(Map<String, Object> contextMap,
            Class<T> contextClazz) {
        try {
            if (contextMap == null) {
                contextMap = new HashMap<>(16);
            }
            T context = contextClazz.newInstance();
            context.setContext(contextMap);
            return context;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new CommandInvalidException("指令执行环境创建失败" + contextClazz + " : " + contextMap);
        }
    }

    @Override
    public String toString() {
        if (context == null) {
            return "{}";
        }
        return context.toString();
    }
}
