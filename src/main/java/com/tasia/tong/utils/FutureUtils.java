package com.tasia.tong.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Supplier;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FutureUtils {

    public <T> Future<T> createFuture(ExecutorService executor, Supplier<T> supplier) {
        return executor.submit(supplier::get);
    }

    public <T> List<T> getResultOfFutures(List<Future<T>> futures, T defaultV, int timeout) throws InterruptedException {
        // TODO:
        List<T> result = new ArrayList<>();

        return result;
    }
}
