package com.tasia.tong.inf.es;

import java.io.IOException;
import javax.naming.directory.SearchResult;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.WriteRequest.RefreshPolicy;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

@Slf4j
abstract
public class BaseSearchStorageProxy<T extends BaseSearchStorageIndexPO> {

    @Getter
    private AbstractSearchStorage<T> storage;

    public void setStorage(AbstractSearchStorage<T> storage) {
        this.storage = storage;
    }

    protected String getTypeName() {
        return storage.getTypeName();
    }

    protected String getIndexName() {
        return storage.getIndexName();
    }

    protected RestHighLevelClient getClient() {
        return storage.getRestHighLevelClient();
    }

    protected RefreshPolicy getRefreshPolicy() {
        return storage.getRefreshPolicy();
    }

    protected SearchResponse searchByRequest(SearchRequest searchRequest) throws IOException {
        log.info("搜索引擎 索引: {} 类型: {} 搜索语句: {} 开始搜索", getIndexName(), getTypeName(), searchRequest);
        return getClient().search(searchRequest, RequestOptions.DEFAULT);
    }
}
