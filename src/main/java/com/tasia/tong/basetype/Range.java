package com.tasia.tong.basetype;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Range<T extends Comparable<T>> {

    private T min;

    private T max;

    public static <T extends Comparable<T>> Range<T> of(T min, T max) {
        return new Range<>(min, max);
    }

    public boolean contains(T o) {
        if (min == null && max == null) {
            return false;
        }
        if (min == null) {
            return max.compareTo(o) >= 0;
        }
        if (max == null) {
            return min.compareTo(o) <= 0;
        }
        return min.compareTo(o) <= 0 && max.compareTo(o) >= 0;
    }

}
