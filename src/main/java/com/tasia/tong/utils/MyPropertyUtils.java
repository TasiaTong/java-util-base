package com.tasia.tong.utils;

import com.google.common.base.CaseFormat;
import com.tasia.tong.utils.exceptions.PropertyAccessException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.util.CollectionUtils;

@Slf4j
@UtilityClass
public class MyPropertyUtils {

    private static final String PROPERTY_DB_FIELD_JOINER = "_";

    public String objectFieldToDbCodeWithReentrancy(String code) {
        if (code.contains(PROPERTY_DB_FIELD_JOINER)) {
            return code;
        } else {
            return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, code);
        }
    }

    public String dbCodeToObjectFieldWithReentrancy(String field) {
        if (field.contains(PROPERTY_DB_FIELD_JOINER)) {
            return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, field);
        } else {
            return field;
        }
    }

    public String objectFieldToDbCode(String code) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, code);
    }

    public String dbCodeToObjectField(String field) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, field);
    }

    public Map<String, Object> getPropertiesOfObject(List<String> propertyFields, Object obj) {
        if (CollectionUtils.isEmpty(propertyFields)) {
            return new HashMap<>();
        }
        Map<String, Object> propertiesMap = new HashMap<>(propertyFields.size());
        for (String propertyFiled : propertyFields) {
            try {
                Object propertyValue = PropertyUtils
                        .getSimpleProperty(obj, propertyFiled);
                propertiesMap.put(propertyFiled, propertyValue);
            } catch (IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                log.error("在对对象: {} 获取属性: {} 时发生错误", obj, propertyFiled, e);
                throw new PropertyAccessException(obj + " " + propertyFiled, e);
            }
        }
        return propertiesMap;
    }

    public void copyPropertyToObject(Map<String, Object> propertyFieldToValueMap, Object obj) {
        if (MapUtils.isEmpty(propertyFieldToValueMap)) {
            return;
        }

        for (Map.Entry<String, Object> entry : propertyFieldToValueMap.entrySet()) {
            String propertyField = entry.getKey();
            Object propertyValue = entry.getValue();
            try {
                PropertyUtils.setProperty(obj, propertyField, propertyValue);
            } catch (IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                log.error("在对对象: {} 设置属性: {} 时发生错误", obj, propertyField, e);
                throw new PropertyAccessException(obj + " " + propertyField, e);
            }
        }
    }

    public void copyPropertyToObjectIgnoreException(Map<String, Object> propertyFieldToValueMap, Object obj) {
        if (MapUtils.isEmpty(propertyFieldToValueMap)) {
            return;
        }

        for (Map.Entry<String, Object> entry : propertyFieldToValueMap.entrySet()) {
            String propertyField = entry.getKey();
            Object propertyValue = entry.getValue();
            try {
                PropertyUtils.setProperty(obj, propertyField, propertyValue);
            } catch (IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                log.error("在对对象: {} 设置属性: {} 时发生错误", obj, propertyField, e);
            }
        }
    }

    public <T> T parseObjectFromPropertyMap(Map<String, Object> propertyFieldToValueMap, Class<T> clazz) {
        try {
            T object = clazz.newInstance();
            List<String> propertyNames = getPropertyNames(object);
            for (String name : propertyNames) {
                if (propertyFieldToValueMap.containsKey(name)) {
                    PropertyUtils.setProperty(object, name, propertyFieldToValueMap.get(name));
                }
            }
            return object;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            log.error("在对Map: {} 转换为对象: {} 时发生错误", propertyFieldToValueMap, clazz);
            throw new PropertyAccessException(propertyFieldToValueMap.toString() + "转换为" + clazz + "失败", e);
        }
    }

    private <T> List<String> getPropertyNames(T obj) {
        PropertyDescriptor[] propertyDescriptors = PropertyUtils
                .getPropertyDescriptors(obj);
        return Arrays.stream(propertyDescriptors)
                .map(PropertyDescriptor::getName)
                .filter(name -> !"class".equalsIgnoreCase(name))
                .collect(Collectors.toList());
    }

}
