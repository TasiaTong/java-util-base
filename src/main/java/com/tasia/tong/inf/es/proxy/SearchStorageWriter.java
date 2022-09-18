package com.tasia.tong.inf.es.proxy;

import com.tasia.tong.inf.es.BaseSearchStorageIndexPO;
import com.tasia.tong.inf.es.BaseSearchStorageProxy;
import lombok.Builder;
import lombok.Data;

public class SearchStorageWriter<T extends BaseSearchStorageIndexPO> extends
        BaseSearchStorageProxy {

    private static final Integer DEFAULT_BATCH_SIZE = 200;

    @Data
    @Builder
    private static class docWithParentId {

        /**
         * 文档Id
         */
        private String docId;

        /**
         * 父Id
         */
        private String parentId;

        /**
         * 文档
         */
        private String doc;
    }
    // TODO: 待补充
}
