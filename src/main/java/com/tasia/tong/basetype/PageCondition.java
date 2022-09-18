package com.tasia.tong.basetype;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PageCondition {

    private Integer limit;

    private Integer offset;

    private Order order;

    private String hint;

    public static PageCondition of(Integer limit, Integer offset) {
        PageCondition pageCondition = new PageCondition();
        pageCondition.setLimit(limit);
        pageCondition.setOffset(offset);
        return pageCondition;
    }

    public static PageCondition of(Integer limit, Integer offset, Order order) {
        PageCondition pageCondition = new PageCondition();
        pageCondition.setLimit(limit);
        pageCondition.setOffset(offset);
        pageCondition.setOrder(order);
        return pageCondition;
    }
}
