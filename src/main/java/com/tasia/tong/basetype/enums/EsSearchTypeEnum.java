package com.tasia.tong.basetype.enums;

import com.tasia.tong.basetype.SearchCondition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

@AllArgsConstructor
public enum EsSearchTypeEnum implements IBaseEnum<String, String> {

    /**
     *
     */
    MUST_NOT("must_not", "反向匹配"),
    MATCH_PHASE("match_phase", "短语匹配"),
    EXISTS("exists", "存在性"),
    NOT_EXISTS("not_exists", "存在性"),
    REGEXP("regexp", "正则匹配")
    ;

    @Getter
    private final String code;

    @Getter
    private final String desc;

    public QueryBuilder getEsBuilders(SearchCondition searchCondition) {
        if (EsSearchTypeEnum.EXISTS.equals(searchCondition.getSearchTypeEnum())) {
            return QueryBuilders.existsQuery(searchCondition.getKey());
        } else if (EsSearchTypeEnum.MATCH_PHASE.equals(searchCondition.getSearchTypeEnum())) {
            return QueryBuilders.matchPhraseQuery(searchCondition.getKey(), searchCondition.getValue());
        } else if (EsSearchTypeEnum.NOT_EXISTS.equals(searchCondition.getSearchTypeEnum())) {
            return QueryBuilders.boolQuery().mustNot(QueryBuilders.existsQuery(searchCondition.getKey()));
        } else if (EsSearchTypeEnum.REGEXP.equals(searchCondition.getSearchTypeEnum())) {
            return QueryBuilders.boolQuery().must(QueryBuilders.regexpQuery(searchCondition.getKey(),
                    String.valueOf(searchCondition.getValue())));
        } else {
            return null;
        }
    }
}
