package com.tasia.tong.inf.es;

import com.tasia.tong.inf.es.proxy.SearchStorageAggregator;
import com.tasia.tong.inf.es.proxy.SearchStorageReader;
import com.tasia.tong.inf.es.proxy.SearchStorageWriter;
import java.util.Objects;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.support.WriteRequest.RefreshPolicy;
import org.elasticsearch.client.RestHighLevelClient;

@Slf4j
abstract
public class AbstractSearchStorage<T extends BaseSearchStorageIndexPO> {

    private static final ThreadLocal<RefreshPolicy> refreshPolicyThreadLocal = new ThreadLocal<>();

    /**
     * restHighLevelClient
     */
    private RestHighLevelClient restHighLevelClient;

    /**
     * 写入代理
     */
    private SearchStorageWriter<T> write;

    /**
     * 读取代理
     */
    private SearchStorageReader<T> reader;

    /**
     * 聚合代理
     */
    private SearchStorageAggregator<T> aggregator;

    /**
     * 搜索引擎配置
     * @return
     */
    protected abstract BaseSearchStorageConfig getConfig();

    /**
     * 获取索引名称
     * @return
     */
    public abstract String getIndexName();

    /**
     * 获取类型名称
     * @return
     */
    public abstract String getTypeName();

    /**
     * 子类型
     * @return
     */
    public String getChildTypeName() {
        return null;
    }

    /**
     * 获取搜索引擎客户端
     *
     * @return
     */
    public RestHighLevelClient getRestHighLevelClient() {
        return restHighLevelClient;
    }

    @PostConstruct
    public void init() {
        // TODO: 根据Config配置初始化client
    }

    /**
     * 设置刷新策略
     * @param refreshPolicy
     */
    public void setRefreshPolicy(RefreshPolicy refreshPolicy) {
        refreshPolicyThreadLocal.set(refreshPolicy);
    }

    /**
     * 重置刷新策略
     */
    public void clearRefreshPolicy() {
        refreshPolicyThreadLocal.remove();
    }

    /**
     * 获取刷新策略
     * @return
     */
    public RefreshPolicy getRefreshPolicy() {
        RefreshPolicy refreshPolicy = refreshPolicyThreadLocal.get();
        return Objects.isNull(refreshPolicy) ? RefreshPolicy.NONE : refreshPolicy;
    }
}
