package com.tasia.tong.inf.es.proxy;

import com.tasia.tong.inf.es.BaseSearchStorageIndexPO;
import com.tasia.tong.inf.es.BaseSearchStorageProxy;
import lombok.Builder;
import lombok.Data;

public class SearchStorageReader<T extends BaseSearchStorageIndexPO> extends
        BaseSearchStorageProxy {

    private static final Integer MAX_SCROLL_PAGE = 10000000;

    // TODO: 待补充
}
