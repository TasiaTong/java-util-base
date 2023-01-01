package com.tasia.tong.inf.pagefetcher;

import com.tasia.tong.basetype.PageData;
import com.tasia.tong.concurrent.ExecutorFactory;
import com.tasia.tong.utils.FutureUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Future;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

@Slf4j
abstract
public class ConcurrentPageFetcher<T> {

    private static final Integer DEFAULT_PAGE_SIZE = 50;

    private static final Integer DEFAULT_TIMEOUT_CONCURRENCY = 10 * 1000;

    @Resource
    private ExecutorFactory executorFactory;

    public static class BasePageFetcherParam {

    }

    public List<T> fetchPageDataSerial(BasePageFetcherParam param) {
        PageData<T> response = queryPageData(param, 0);
        int total = Math.toIntExact(response.getTotal());
        if (total <= 0) {
            return Collections.emptyList();
        }
        List<T> dataOfAllPage = new ArrayList<>();
        dataOfAllPage.addAll(response.getData());

        if (total <= getPageSize()) {
            return dataOfAllPage;
        }
        int offset = getPageSize();
        while (total >= 0) {
            PageData<T> pageData = queryPageData(param, offset);
            if (Objects.nonNull(pageData)) {
                dataOfAllPage.addAll(pageData.getData());
            }
            total = total - getPageSize();
            offset = offset + getPageSize();
        }
        return dataOfAllPage;
    }

    public List<PageData<T>> concurrentFetchLeftPageData(int total, BasePageFetcherParam param) {
        int offset = getPageSize();
        List<Future<PageData<T>>> futures = new ArrayList<>();
        while (total >= 0) {
            int finalOffset = offset;
//            futures.add(FutureUtils.createFuture(executorFactory.getExecutor(), () -> queryPageData(param, finalOffset)));
            total = total - getPageSize();
            offset = offset + getPageSize();
        }

        try {
            return FutureUtils.getResultOfFutures(futures, null, getTimeout());
        } catch (Exception e) {
            log.error("[远程调用] 并发调用获取分页时异常", e);
            return Collections.emptyList();
        }
    }

    protected Integer getTimeout() {
        return DEFAULT_TIMEOUT_CONCURRENCY;
    }

    private Integer getPageSize() {
        return DEFAULT_PAGE_SIZE;
    }

    /**
     * 请求单次页数据
     *
     * @param param
     * @param offset
     * @return
     */
    protected abstract PageData<T> queryPageData(BasePageFetcherParam param, int offset);
}
