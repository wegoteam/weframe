package com.wegoteam.framework.util;

import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

/**
 * @description:
 * @author: XUCHANG
 * @create: 2021-04-23 14:17
 */
@Slf4j
public class LocalDateUtils {
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    public static final String DATE_FORMAT = "yyyy/MM/dd";

    private static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final String DEFAULT_DATETIME_FORMAT2 = "yyyy-MM-dd HH:mm:ss:SSS";

    public static final String DEF_DATETIME_FORMAT = "yyyy/MM/dd HH:mm:ss";

    private static final String DATETIME_FORMAT = "yyyyMMddHHmmss";

    private static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

    private static final String DEFAULT_DATETIME_FORMAT_PATTERN = "yyyy-MM-dd[['T'HH][:mm][:ss]]";

    private static final String DEF_DATETIME_FORMAT_PATTERN = "yyyy/MM/dd[['T'HH][:mm][:ss]]";

    /**
     * String转LocalDateTime
     * @param dateString
     * @return
     */
    public static LocalDateTime parseLocalDateTime(String dateString) {
        if (StringUtils.isEmpty(dateString)) {
            return null;
        }

        try {
            if (dateString.length() <= 10) {
                DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                        .appendPattern(DEFAULT_DATETIME_FORMAT_PATTERN)
                        .parseDefaulting(ChronoField.HOUR_OF_DAY,0)
                        .parseDefaulting(ChronoField.MINUTE_OF_HOUR,0)
                        .parseDefaulting(ChronoField.SECOND_OF_MINUTE,0)
                        .parseDefaulting(ChronoField.MILLI_OF_SECOND,0)
                        .toFormatter();

                LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
                return dateTime;
            } else {
                if(dateString.length() == 14){
                    DateTimeFormatter df = DateTimeFormatter.ofPattern(DATETIME_FORMAT);
                    LocalDateTime dateTime = LocalDateTime.parse(dateString, df);
                    return dateTime;
                }
                DateTimeFormatter df = DateTimeFormatter.ofPattern(DEFAULT_DATETIME_FORMAT);
                LocalDateTime dateTime = LocalDateTime.parse(dateString, df);
                return dateTime;
            }
        } catch (Exception e) {
            log.error("日期转换失败",e);
            return null;
        }
    }
    public static LocalDateTime parseLocalDateTime(String dateString,String format) {
        if (StringUtils.isEmpty(dateString)) {
            return null;
        }
        try {
            if (dateString.length() <= 10) {
                DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                        .appendPattern(format)
                        .parseDefaulting(ChronoField.HOUR_OF_DAY,0)
                        .parseDefaulting(ChronoField.MINUTE_OF_HOUR,0)
                        .parseDefaulting(ChronoField.SECOND_OF_MINUTE,0)
                        .parseDefaulting(ChronoField.MILLI_OF_SECOND,0)
                        .toFormatter();

                LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
                return dateTime;
            }
            DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
            LocalDateTime dateTime = LocalDateTime.parse(dateString, df);
            return dateTime;
        }catch (Exception e){
            log.error("日期转换失败",e);
            return null;
        }
    }
    /**
     *String转LocalDate
     * @param dateString
     * @return
     */
    public static LocalDate parseLocalDate(String dateString){

        if (StringUtils.isEmpty(dateString)){
            return null;
        }
        try {
            LocalDate localDate = LocalDate.parse(dateString, DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT));
            return localDate;
        }catch (Exception e){
            log.error("日期转换失败",e);
            return null;
        }
    }
    /**
     * yyyy/MM/dd
     *String转LocalDate
     * @param dateString
     * @return
     */
    public static LocalDate parseLocalDateForLine(String dateString){

        if (StringUtils.isEmpty(dateString)){
            return null;
        }
        try {
            if (dateString.length() <= 10) {

                LocalDate dateTime = LocalDate.parse(dateString, DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT));
                return dateTime;
            }
            LocalDateTime dateTime = LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(DEFAULT_DATETIME_FORMAT));
            return dateTime.toLocalDate();
        }catch (Exception e){
            log.error("日期转换失败",e);
            return null;
        }
    }

    /**
     * 序列化成字符串
     * @return
     */
    public static String parseStr(LocalDateTime localDateTime){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DEFAULT_DATETIME_FORMAT);
        return dateTimeFormatter.format(localDateTime);
    }
    /**
     * 序列化成字符串
     * @return
     */
    public static String parseStr(LocalDate localDate){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);
        return dateTimeFormatter.format(localDate);
    }

    /**
     * 时间戳转字符串
     * @param timestamp 毫秒
     * @return
     */
    public static String timestampParseStr(long timestamp){
        try {
            LocalDateTime localDateTime = Instant.ofEpochMilli(timestamp).atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DEFAULT_DATETIME_FORMAT2);
            return dateTimeFormatter.format(localDateTime);
        }catch (Exception e){
            log.error("时间戳转字符串失败",e);
            return null;
        }

    }
    /**
     * 时间戳转字符串 秒
     * @param timestamp 毫秒
     * @return
     */
    public static String timestampParseStrForSecond(long timestamp){
        try {
            LocalDateTime localDateTime = Instant.ofEpochMilli(timestamp).atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DEFAULT_DATETIME_FORMAT);
            return dateTimeFormatter.format(localDateTime);
        }catch (Exception e){
            log.error("时间戳转字符串失败",e);
            return null;
        }

    }
}
