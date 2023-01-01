package com.tasia.tong.inf.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InnerCache<T> {

    private Cache<String, T> cache;

    public void init(int expire, int size) {
        cache = CacheBuilder.newBuilder()
                .maximumSize(size)
                .expireAfterAccess(expire, TimeUnit.SECONDS)
                .build();
    }

    public static String createCacheKey(String prefix, String... keys) {
        return prefix + "_" + String.join("_", keys);
    }

    public T get(String cacheKey, Supplier<T> supplier) {
        T obj = cache.getIfPresent(cacheKey);
        if (obj != null) {
            log.info("[本地缓存] 访问, 直接命中缓存, cacheKey: {}, cacheValue:{}", cacheKey, obj);
            return obj;
        }
        obj = supplier.get();
        log.info("[本地缓存]访问, 命中存储, cacheKey: {}, cacheValue:{}", cacheKey, obj);
        if (obj != null) {
            cache.put(cacheKey, obj);
        }
        return obj;
    }

    public void invalidKey(String cacheKey) {
        cache.invalidate(cacheKey);
    }
}
