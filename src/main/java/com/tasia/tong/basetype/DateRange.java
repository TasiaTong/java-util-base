package com.tasia.tong.basetype;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DateRange {

    private Long startTime;

    private Long endTime;

    public static DateRange of(Long startTime, Long endTime) {
        return new DateRange(startTime, endTime);
    }

    public Long getStartTime() {
        if (Objects.isNull(startTime)) {
            return 0L;
        }
        return startTime;
    }

    public Long getEndTime() {
        if (Objects.isNull(endTime)) {
            return Long.MAX_VALUE;
        }
        return endTime;
    }

}
