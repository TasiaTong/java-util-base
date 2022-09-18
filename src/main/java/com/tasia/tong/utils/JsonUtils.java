package com.tasia.tong.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tasia.tong.utils.exceptions.JsonProcessException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import lombok.NonNull;

@Slf4j
@UtilityClass
public class JsonUtils {

    ObjectMapper mapper = new ObjectMapper();

    static {
        // 序列化时, 遇到空bean(无属性) 不会失败
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 序列化时, 遇到未知属性(在bean上找不到)不会失败
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 关闭自动属性探测(仅通过setter方法探测)
        mapper.configure(MapperFeature.AUTO_DETECT_FIELDS, false);
        // 序列化localDate为简单字符串格式
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public String toJsonString(Object obj) {
        if (obj == null) {
            return "";
        }
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("Json 序列化对象:{} 发生异常:{}", obj, e);
            throw new JsonProcessException(e);
        }
    }

    public <T> T parseSimpleObject(String jsonSting, Class<T> clazz) {
        if (StringUtils.isBlank(jsonSting)) {
            return null;
        }
        try {
            return mapper.readValue(jsonSting, clazz);
        } catch (IOException e) {
            log.error("Json反序列化异常:{} obj: {}", jsonSting, e);
            throw new JsonProcessException(e);
        }
    }

    public <T> T parseObject(String jsonString, TypeReference<T> typeReference) {
        if (StringUtils.isBlank(jsonString)) {
            return null;
        }

        try {
            return mapper.readValue(jsonString, typeReference);
        } catch (JsonProcessingException e) {
            log.error("Json反序列化异常:{} obj:{}", jsonString, e);
            throw new JsonProcessException(e);
        }
    }


    public <T> List<T> jsonToList(@NonNull String jsonString, Class<?> clazz) {
        try {
            return mapper.readValue(jsonString, getCollectionType(List.class, clazz));
        } catch (JsonProcessingException e) {
            log.error("Json反序列化异常:[{}] to class [{}]", jsonString, clazz.getName(), e);
            throw new JsonProcessException(e);
        }
    }

    public <K, V> Map<K, V> jsonStringToMap(String jsonString) {
        if (StringUtils.isBlank(jsonString)) {
            return new HashMap<>();
        }

        try {
            return mapper.readValue(jsonString, new TypeReference<Map<K, V>>() {
            });
        } catch (JsonProcessingException e) {
            log.error("Json反序列化失败:{} obj:{}", jsonString, e);
            throw new JsonProcessException(e);
        }
    }

    public <K, V> List<Map<K, V>> jsonStringToMapList(String jsonString) {
        if (StringUtils.isBlank(jsonString)) {
            return new ArrayList<>();
        }
        try {
            return mapper.readValue(jsonString, new TypeReference<List<Map<K, V>>>() {
            });
        } catch (JsonProcessingException e) {
            log.error("Json反序列化失败:{} obj:{}", jsonString, e);
            throw new JsonProcessException(e);
        }
    }

    /**
     * 获取泛型的Collection type
     *
     * @param collectionClass 泛型的Collection
     * @param elementClasses 实体Bean
     * @return JavaType
     */
    private JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }
}
