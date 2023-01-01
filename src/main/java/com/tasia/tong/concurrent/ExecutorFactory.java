package com.tasia.tong.concurrent;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

@Slf4j
@EnableScheduling
@Service
@ToString(callSuper = true, exclude = {"executorTraceWrapper"})
public class ExecutorFactory {

    private static final AtomicInteger NEXT_ID = new AtomicInteger();

    private int corePoolSize;

    private int maxPoolSize;

    private int queueSize;

    private int keepAliveTime;

    private ThreadPoolExecutor executor;

    public Object getExecutor() {
        return null;
    }

    // TODO：


}
