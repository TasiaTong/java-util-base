package com.tasia.tong.basetype;

import com.tasia.tong.basetype.enums.OrderDirectionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private String orderKey;

    private OrderDirectionEnum direction;

    public static Order of(String orderKey, OrderDirectionEnum orderDirection) {
        return new Order(orderKey, orderDirection);
    }

    @Override
    public String toString() {
        return orderKey + " " + direction.getDesc();
    }
}
