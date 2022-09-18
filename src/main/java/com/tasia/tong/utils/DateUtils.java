package com.tasia.tong.utils;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

@UtilityClass
public class DateUtils {

    public final DateTimeFormatter YYYY_MM_FORMAT = DateTimeFormatter
            .ofPattern("yyyy-MM");

    public final DateTimeFormatter YYYY_MM_DD_FORMAT = DateTimeFormatter
            .ofPattern("yyyy-MM-dd");

    public final DateTimeFormatter YYYY_MM_DD_SLASH_FORMAT = DateTimeFormatter
            .ofPattern("yyyy/MM/dd");

    public final DateTimeFormatter YYYY_MM_DD_SLASH_SHORT_FORMAT = DateTimeFormatter
            .ofPattern("yyyy/M/d");

    public final DateTimeFormatter YYYY_MM_DD_CHS_FORMAT = DateTimeFormatter
            .ofPattern("yyyy年M月d日");

    public final DateTimeFormatter YYYY_MM_DD_HH_MM_SS_FORMAT = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss");

    public final DateTimeFormatter YYYYMMDDHHMMSS_FORMAT = DateTimeFormatter
            .ofPattern("yyyyMMddHHmmss");

    /**
     * 定义全年的周从周一开始 第一周即使只有一天也作为第一周
     */
    private final WeekFields WEEK_FIELDS = WeekFields.of(DayOfWeek.MONDAY, 1);

    public String getStringTimeByTimestamp(Long timestamp, String pattern) {
        LocalDateTime localDateTime =
                LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp),
                        TimeZone.getDefault().toZoneId());
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    public Long getTimestampOfTodayHour(Integer hour) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hour);
        return cal.getTime().getTime();
    }

    public Integer getWeekDayOfToday() {
        Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
        return localCalendar.get(Calendar.DAY_OF_WEEK);
    }

    public Integer getCurrentHour() {
        Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
        return localCalendar.get(Calendar.HOUR);
    }

    public Integer getCurrentHourOfToday() {
        Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
        return localCalendar.get(Calendar.HOUR_OF_DAY);
    }

    public Long getSpecifiedTimestamp(Long timestamp, Integer hour) {
        LocalDateTime localDateTime = dateToLocalDateTime(new Date(timestamp)).withHour(hour)
                .withMinute(0).withSecond(0);
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()).getTime();
    }

    public LocalDateTime atStartOfMonth(LocalDateTime date) {
        return date.with(TemporalAdjusters.firstDayOfMonth());
    }

    public LocalDateTime atEndOfMonth(LocalDateTime date) {
        return date.with(TemporalAdjusters.lastDayOfMonth());
    }

    public LocalDateTime atStartOfDay(LocalDateTime date) {
        return date.with(LocalTime.MIN);
    }

    public LocalDateTime atEndOfDay(LocalDateTime date) {
        return date.with(LocalTime.MAX);
    }

    public LocalDateTime localDateToLocalDateTime(LocalDate localDate) {
        Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);
        instant = date.toInstant();
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public LocalDate toLocalDate(String dateStr, DateTimeFormatter formatter) {
        return LocalDate.parse(dateStr, formatter);
    }

    public boolean isAfter(String start, String end, DateTimeFormatter formatter) {
        LocalDateTime startTime = LocalDateTime.parse(start, formatter);
        LocalDateTime endTime = LocalDateTime.parse(end, formatter);
        return endTime.isAfter(startTime);
    }

    public String dateToString(Date applyTime, DateTimeFormatter formatter) {
        LocalDateTime localDateTime = toLocalDateTime(applyTime);
        return formatter.format(localDateTime);
    }

    public String increaseDateStringByDay(Date applyTime, Integer day,
            DateTimeFormatter formatter) {
        LocalDateTime localDateTime = toLocalDateTime(applyTime).plusDays(day);
        return formatter.format(localDateTime);
    }

    public String localDateToSting(LocalDate date, DateTimeFormatter formatter) {
        return formatter.format(date);
    }

    public boolean isCurrentMonth(LocalDate localDate) {
        LocalDate today = LocalDate.now();
        return today.getYear() == localDate.getYear()
                && today.getMonthValue() == localDate.getMonthValue();
    }

    public boolean isCurrentMonth(Date date) {
        return isCurrentMonth(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }

    public long getDiffDay(Long startTime, Long endTime) {
        LocalDate start = timestampToLocalDate(startTime);
        LocalDate end = timestampToLocalDate(endTime);
        return ChronoUnit.DAYS.between(start, end);
    }

    public long getDiffMonth(Long startTime, Long endTime) {
        LocalDate start = timestampToLocalDate(startTime);
        LocalDate end = timestampToLocalDate(endTime);
        long monthOfYear = (end.getYear() - start.getYear()) * 12L;
        return monthOfYear + end.getMonthValue() - start.getMonthValue();
    }

    public long getDiffOfWeek(Long startTime, Long endTime) {
        LocalDate start = timestampToLocalDate(startTime);
        LocalDate end = timestampToLocalDate(endTime);
        if (end.compareTo(start) >= 0) {
            return getPositiveDiffOfWeek(start, end);
        } else {
            return -getPositiveDiffOfWeek(start, end);
        }
    }

    private long getPositiveDiffOfWeek(LocalDate start, LocalDate end) {
        int yearDiff = end.getYear() - start.getYear();
        int totalWeeks = 0;
        for (int i = 0; i < yearDiff; ++ i) {
            totalWeeks += totalWeeksOfYear(start.getYear() + i);
        }
        // 由于全年最后一周的特殊性, 跨年情况下用作调节的值
        int adjustValue = start.getYear() != end.getYear()
                && totalWeeksOfYear(start.getYear()) != lastWeekOfYearIfCoincide(start.getYear())
                ? 1 : 0;
        return totalWeeks - start.get(WEEK_FIELDS.weekOfYear()) + end.get(WEEK_FIELDS.weekOfYear())
                - adjustValue;
    }

    public boolean isSameMonth(Long... timestampArrays) {
        return Arrays.stream(timestampArrays)
                .map(timestamp -> timestampToLocalDate(timestamp).getMonthValue())
                .distinct()
                .count() == 1L;
    }

    public boolean isSameDay(LocalDateTime time1, LocalDateTime time2) {
        if (time1 == null || time2 == null) {
            return false;
        }
        LocalDate day1 = time1.toLocalDate();
        LocalDate day2 = time2.toLocalDate();
        return day2.isEqual(day1);
    }

    public LocalDateTime timestampToLocalDateTime(Long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
    }

    public Long LocalDateToTimestamp(LocalDate localDate) {
        return localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public Long localDateTimeToTimestamp(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public boolean checkStringPattern(String dateString, String pattern) {
        if (StringUtils.isEmpty(dateString)) {
            return false;
        }
        try {
            LocalDate.parse(dateString, DateTimeFormatter.ofPattern(pattern));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 当前start <= end
     * @param start
     * @param end
     * @return
     */
    public boolean isAfterAndEquals(String start, String end) {
        LocalDate startTime = LocalDate.parse(start, YYYY_MM_DD_FORMAT);
        LocalDate endTime = LocalDate.parse(end, YYYY_MM_DD_FORMAT);
        return endTime.isEqual(startTime) || endTime.isAfter(startTime);
    }

    public LocalDate timestampToLocalDate(Long timestamp) {
        return Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * 获取某一年最后一周是第几周 如果最后一周和次年第一周有重合则返回 1 如果最后一周和次年第一周五重和则返回最后一周
     * @param year
     * @return
     */
    private int lastWeekOfYearIfCoincide(int year) {
        return LocalDate.of(year, 12, 31).get(WEEK_FIELDS.weekOfWeekBasedYear());
    }

    /**
     * 获取某一年的总周数
     * @param year
     * @return
     */
    private int totalWeeksOfYear(int year) {
        return LocalDate.of(year, 12, 31).get(WEEK_FIELDS.weekOfYear());
    }

    private LocalDateTime dateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
}
