package com.tasia.tong.basetype;

import com.tasia.tong.basetype.enums.EsSearchTypeEnum;
import com.tasia.tong.basetype.enums.IBaseEnum;
import com.tasia.tong.basetype.enums.ISearchKeyEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
public class SearchCondition {

    private static final String WILDCARD = "%";

    /**
     * 查询 key
     */
    private String key;

    /**
     * 查询的value
     */
    private Object value;

    /**
     * es特殊的匹配类型
     */
    private EsSearchTypeEnum searchTypeEnum;

    /**
     * es子文档
     */
    private Boolean isChild = false;

    /**
     * es嵌套nested查询的path
     */
    private String path;

    public SearchCondition(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public SearchCondition(String key, Object value,
            EsSearchTypeEnum searchTypeEnum, Boolean isChild) {
        this.key = key;
        this.value = value;
        this.searchTypeEnum = searchTypeEnum;
        this.isChild = isChild;
    }

    public SearchCondition(String key, Object value, String path) {
        this.key = key;
        this.value = value;
        this.path = path;
    }

    public static <T extends ISearchKeyEnum> SearchCondition of(T key, Object value, String path) {
        return new SearchCondition(key.getCode(), value, path);
    }

    public static <T extends ISearchKeyEnum> SearchCondition of(T key, Object value) {
        return new SearchCondition(key.getCode(), value);
    }

    public static <T extends ISearchKeyEnum> SearchCondition of(T key,
            EsSearchTypeEnum searchTypeEnum, Object value) {
        return new SearchCondition(key.getCode(), value, searchTypeEnum,null);
    }

    public static <T extends ISearchKeyEnum> SearchCondition of(T key,
            EsSearchTypeEnum searchTypeEnum, Object value, Boolean isChild) {
        return new SearchCondition(key.getCode(), value, searchTypeEnum, isChild);
    }

    public static <T extends ISearchKeyEnum> SearchCondition of(T key, Object value,
            boolean isChild) {
        return SearchCondition.of(key.getCode(), value, isChild);
    }

    public static <T extends IBaseEnum<String, String>> SearchCondition of(T key, Object value) {
        return new SearchCondition(key.getCode(), value);
    }

    public static <T extends IBaseEnum<String, String>> SearchCondition of(T key,
            EsSearchTypeEnum searchTypeEnum, Object value) {
        return new SearchCondition(key.getCode(), value, searchTypeEnum, null);
    }

    public static SearchCondition of(String key, Object value) {
        return new SearchCondition(key, value);
    }

    public static SearchCondition ofDateRange(String key, Long startTime, Long endTime) {
        DateRange dateRange = new DateRange(0L, 2147483647L * 1000L);
        if (startTime != null && startTime > 0) {
            dateRange.setStartTime(startTime);
        }
        if (endTime != null && endTime > 0) {
            dateRange.setEndTime(endTime);
        }
        return SearchCondition.of(key, dateRange);
    }


    public static SearchCondition of(String key) {
        return new SearchCondition(key, null, EsSearchTypeEnum.EXISTS, null);
    }

    public static SearchCondition of(String key, Object value, EsSearchTypeEnum esSearchTypeEnum) {
        return new SearchCondition(key, value, esSearchTypeEnum, null);
    }

    public static SearchCondition of(String key, boolean isChild) {
        return new SearchCondition(key, null, EsSearchTypeEnum.EXISTS, isChild);
    }

    public static SearchCondition of(String key, Object value, boolean isChild) {
        return new SearchCondition(key, value, null, isChild);
    }
}
