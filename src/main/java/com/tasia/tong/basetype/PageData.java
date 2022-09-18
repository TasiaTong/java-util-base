package com.tasia.tong.basetype;

import java.util.Collections;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PageData<T> {

    /**
     * 总数量
     */
    private Long total;

    /**
     * 当前数据量
     */
    private Integer size;

    /**
     * 总的页数
     */
    private Integer pageNo;

    /**
     * 实际的数量
     */
    private List<T> data;

    public static <T> PageData<T> buildEmptyPageData() {
        PageData<T> emptyPage = new PageData<T>();
        emptyPage.setSize(0);
        emptyPage.setTotal(0L);
        emptyPage.setPageNo(1);
        emptyPage.setData(Collections.emptyList());
        return emptyPage;
    }
}
