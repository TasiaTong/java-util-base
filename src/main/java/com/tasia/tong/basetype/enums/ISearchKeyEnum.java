package com.tasia.tong.basetype.enums;

import com.tasia.tong.basetype.SearchCondition;
import org.apache.commons.lang3.StringUtils;

public interface ISearchKeyEnum {

    /**
     * 获取对应的code
     *
     * @return
     */
    String getCode();


    /**
     * 获取对应的描述信息
     *
     * @return
     */
    String getDesc();

    /**
     * 是否模糊搜索
     * @return
     */
    Boolean getLikable();

    /**
     * 搜索的数据源
     * @return
     */
    SearchStorageEnum getSearchStorage();

    default SearchCondition build(Object obj) {
        if (obj == null) {
            return null;
        }
        SearchStorageEnum searchStorageEnum = getSearchStorage();
        if (searchStorageEnum == null) {
            searchStorageEnum = SearchStorageEnum.MYSQL;
        }
        if (Boolean.TRUE.equals(getLikable())) {
            if (!(obj instanceof String)) {
                return null;
            }
            if (StringUtils.isBlank((String) obj)) {
                return null;
            }
            return SearchCondition.of(this, searchStorageEnum.wrapWildCard(String.valueOf(obj)));
        } else {
            return SearchCondition.of(this, obj);
        }
    }
}
