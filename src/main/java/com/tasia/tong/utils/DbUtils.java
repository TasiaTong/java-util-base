package com.tasia.tong.utils;

import com.tasia.tong.inf.es.AbstractSearchStorage;
import java.util.function.Supplier;
import lombok.experimental.UtilityClass;
import org.elasticsearch.action.support.WriteRequest.RefreshPolicy;

@UtilityClass
public class DbUtils {

    public <T> T executeFromMaster(Supplier<T> supplier) {

        try {
            // TODO: 开启强制读主的
            return supplier.get();
        } finally {
            // TODO:可能还有清除操作
            ;
        }
    }

    public <T extends AbstractSearchStorage> void refreshImmediate(T t, Supplier<Void> supplier) {
        try {
            t.setRefreshPolicy(RefreshPolicy.IMMEDIATE);
            supplier.get();
        } finally {
            t.clearRefreshPolicy();
        }
    }
}
