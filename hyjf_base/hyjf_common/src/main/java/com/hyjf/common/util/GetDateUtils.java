/*
 * @Copyright: 2005-2018 so_what. All rights reserved.
 */
package com.hyjf.common.util;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * @Description
 * @Author sss
 * @Date 2018/7/25 14:32
 */
public class GetDateUtils {

    //1秒 java已毫秒为单位
    public static final long SECOND = 1000;

    //一分钟
    public static final long MINUTE = SECOND * 60;

    // 一小时
    public static final long HOUR = MINUTE * 60;

    //一天
    public static final long DAY = HOUR * 24;

    //一周
    public static final long WEEK = DAY * 7;

    //一年
    public static final long YEAR = DAY * 365;

    //默认时间格式
    public static final String FORMAT_TIME = "yyyy-MM-dd HH:mm:ss";
    //默认时间格式
    public static final String FORMAT_TIME_MINUTE = "yyyy-MM-dd HH:mm";
    //默认时间格式
    public static final String FORMAT_TIME_MINUTE_SSS = "yyyy-MM-dd HH:mm:ss:SSS";
    //默认日期格式
    public static final String FORTER_DATE = "yyyy-MM-dd";
    //默认日期格式
    public static final String FORTER_DATE_yyyyMMdd = "yyyyMMdd";
    //默认日期格式
    public static final String FORTER_DATE_ZW = "yyyy年MM月dd日";
    //默认时间格式
    public static final String FORTER_DATE_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    //默认时间格式
    public static final String FORTER_DATE_YYYYMMDDHHMMSSSS = "yyyyMMddHHmmssSS";
    //默认时间格式
    public static final String FORTER_DATE_SHORT_TIME = "HH:mm";
    //默认时间格式
    public static final String FORTER_DATE_YYYY_MM_DD = "yyyy.MM.dd";

    private static final Map<Integer, String> WEEK_DAY = new HashMap<Integer, String>();

    static {
        WEEK_DAY.put(7, "星期六");
        WEEK_DAY.put(1, "星期天");
        WEEK_DAY.put(2, "星期一");
        WEEK_DAY.put(3, "星期二");
        WEEK_DAY.put(4, "星期三");
        WEEK_DAY.put(5, "星期四");
        WEEK_DAY.put(6, "星期五");
    }

    /**
     * 获取当前系统时间
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentTime() {
        DateTime dt = new DateTime();
        String time = dt.toString(FORMAT_TIME);
        return time;
    }

    /**
     * 获取系统当前时间按照指定格式返回
     *
     * @param pattern yyyy/MM/dd hh:mm:a
     * @return
     */
    public static String getCurrentTimePattern(String pattern) {
        DateTime dt = new DateTime();
        String time = dt.toString(pattern);
        return time;
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static String getCurrentDate() {
        DateTime dt = new DateTime();
        String date = dt.toString(FORTER_DATE);
        return date;
    }

    /**
     * 获取当前日期按照指定格式
     *
     * @param pattern
     * @return
     */
    public static String getCurrentDatePattern(String pattern) {
        DateTime dt = new DateTime();
        String date = dt.toString(pattern);
        return date;
    }

    /**
     * 获得当前时间的时间戳  10位
     *
     * @return
     */
    public static Integer getNowTime() {
        DateTime dt = new DateTime();
        Long time = dt.getMillis() / 1000;
        return time.intValue();
    }


    /**
     * 按照时区转换时间
     *
     * @param date
     * @param timeZone 时区
     * @param parrten
     * @return
     */
    public static String format(Date date, TimeZone timeZone, String parrten) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(parrten);
        sdf.setTimeZone(timeZone);
        return sdf.format(date);
    }

    /**
     * 获取指定时间
     *
     * @param year    年
     * @param month   月
     * @param day     天
     * @param hour    小时
     * @param minute  分钟
     * @param seconds 秒
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getPointTime(Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer seconds) {
        DateTime dt = new DateTime(year, month, day, hour, minute, seconds);
        String date = dt.toString(FORMAT_TIME);
        return date;
    }

    /**
     * @param year    年
     * @param month   月
     * @param day     天
     * @param hour    小时
     * @param minute  分钟
     * @param seconds 秒
     * @param parrten 自定义格式
     * @return parrten
     */
    public static String getPointTimePattern(Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer seconds, String parrten) {
        DateTime dt = new DateTime(year, month, day, hour, minute, seconds);
        String date = dt.toString(parrten);
        return date;
    }

    /**
     * 获取指定日期
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static String getPointDate(Integer year, Integer month, Integer day) {
        LocalDate dt = new LocalDate(year, month, day);
        String date = dt.toString(FORTER_DATE);
        return date;
    }

    /**
     * 获取指定日期 返回指定格式
     *
     * @param year
     * @param month
     * @param day
     * @param parrten
     * @return
     */
    public static String getPointDatParrten(Integer year, Integer month, Integer day, String parrten) {
        LocalDate dt = new LocalDate(year, month, day);
        String date = dt.toString(parrten);
        return date;
    }

    /**
     * 获取当前是一周星期几
     *
     * @return
     */
    public static String getWeek() {
        DateTime dts = new DateTime();
        String week = null;
        switch (dts.getDayOfWeek()) {
            case DateTimeConstants.SUNDAY:
                week = "星期日";
                break;
            case DateTimeConstants.MONDAY:
                week = "星期一";
                break;
            case DateTimeConstants.TUESDAY:
                week = "星期二";
                break;
            case DateTimeConstants.WEDNESDAY:
                week = "星期三";
                break;
            case DateTimeConstants.THURSDAY:
                week = "星期四";
                break;
            case DateTimeConstants.FRIDAY:
                week = "星期五";
                break;
            case DateTimeConstants.SATURDAY:
                week = "星期六";
            default:
                break;
        }
        return week;
    }

    /**
     * 获取指定时间是一周的星期几
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static String getWeekPoint(Integer year, Integer month, Integer day) {
        LocalDate dts = new LocalDate(year, month, day);
        String week = null;
        switch (dts.getDayOfWeek()) {
            case DateTimeConstants.SUNDAY:
                week = "星期日";
                break;
            case DateTimeConstants.MONDAY:
                week = "星期一";
                break;
            case DateTimeConstants.TUESDAY:
                week = "星期二";
                break;
            case DateTimeConstants.WEDNESDAY:
                week = "星期三";
                break;
            case DateTimeConstants.THURSDAY:
                week = "星期四";
                break;
            case DateTimeConstants.FRIDAY:
                week = "星期五";
                break;
            case DateTimeConstants.SATURDAY:
                week = "星期六";
                break;
            default:
                break;
        }
        return week;
    }

    /**
     * 格式化日期
     *
     * @param date
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String format(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_TIME);
        return format.format(date);
    }

    /**
     * 格式化日期字符串
     *
     * @param date    日期
     * @param pattern 日期格式
     * @return
     */
    public static String format(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    /**
     * 解析日期
     *
     * @param date    日期字符串
     * @param pattern 日期格式
     * @return
     */
    public static Date parse(String date, String pattern) {
        if (date == null) {
            return null;
        }
        Date resultDate = null;
        try {
            resultDate = new SimpleDateFormat(pattern).parse(date);
        } catch (ParseException e) {

        }
        return resultDate;
    }

    /**
     * 解析日期yyyy-MM-dd HH:mm:ss
     *
     * @param date 日期字符串
     * @return
     */
    public static Date parseDateTime(String date) {
        if (date == null) {
            return null;
        }

        try {
            return new SimpleDateFormat(FORMAT_TIME).parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 解析日期yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static Date parseDate(String date) {
        if (date == null) {
            return null;
        }

        try {
            return new SimpleDateFormat(FORTER_DATE).parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 解析日期 yyyy-MM-dd HH:mm:ss
     *
     * @param timestamp
     * @return
     */
    public static String format(Long timestamp, String pattern) {
        String dateStr = "";
        if (null == timestamp || timestamp.longValue() < 0) {
            return dateStr;
        }
        try {
            Date date = new Date(timestamp);
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            dateStr = format.format(date);
        } catch (Exception e) {
            // ignore
        }

        return dateStr;
    }

    /**
     * 解析日期 yyyy-MM-dd HH:mm:ss
     *
     * @param timestamp
     * @return
     */
    public static String format(Long timestamp) {
        String dateStr = "";
        if (null == timestamp || timestamp.longValue() < 0) {
            return dateStr;
        }
        try {
            Date date = new Date(timestamp);
            SimpleDateFormat format = new SimpleDateFormat(FORMAT_TIME);
            dateStr = format.format(date);
        } catch (Exception e) {
            // ignore
        }

        return dateStr;
    }

    /**
     * 获取当前时间前几天时间,按指定格式返回
     *
     * @param days
     * @return
     */
    public static String forwardDay(Integer days, String format) {
        DateTime dt = new DateTime();
        DateTime y = dt.minusDays(days);
        return y.toString(format);
    }

    /**
     * 获取当前时间前几天时间
     *
     * @param days
     * @return
     */
    public static Date forwardDay(Integer days) {
        DateTime dt = new DateTime();
        DateTime y = dt.minusDays(days);
        return y.toDate();
    }

    /**
     * 获取指定时间之后或者之前的某一天00:00:00 默认返回当天
     *
     * @param days
     * @return
     */
    public static Date day00(Integer days, String date, String zimeZone) throws Throwable {
        DateTime dt;
        TimeZone timeZone;
        try {
            if (StringUtils.isBlank(zimeZone)) {
                timeZone = TimeZone.getDefault();
            } else {
                timeZone = TimeZone.getTimeZone(zimeZone);
            }
            if (StringUtils.isBlank(date)) {
                dt = new DateTime().withZone(DateTimeZone.forTimeZone(timeZone)).toLocalDateTime().toDateTime();
            } else {
                dt = new DateTime(date).withZone(DateTimeZone.forTimeZone(timeZone)).toLocalDateTime().toDateTime();
            }
        } catch (Exception e) {
            throw new Throwable(e);
        }

        DateTime y = dt.minusDays(days).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0);
        return y.toDate();
    }

    /**
     * 获取指定时间之后或者之前的某一天23:59:59 默认返回当天
     *
     * @param days 偏移量
     * @return
     */
    public static Date day59(Integer days, String date, String zimeZone) throws Throwable {
        DateTime dt;
        TimeZone timeZone;
        try {
            if (StringUtils.isBlank(zimeZone)) {
                timeZone = TimeZone.getDefault();
            } else {
                timeZone = TimeZone.getTimeZone(zimeZone);
            }
            if (StringUtils.isBlank(date)) {

                dt = new DateTime().withZone(DateTimeZone.forTimeZone(timeZone)).toLocalDateTime().toDateTime();
            } else {
                dt = new DateTime(date).withZone(DateTimeZone.forTimeZone(timeZone)).toLocalDateTime().toDateTime();
            }
        } catch (Exception e) {
            throw new Throwable(e);
        }
        DateTime y = dt.minusDays(days).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59);
        return y.toDate();
    }

    /**
     * 计算两个时间相差多少天
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static Integer diffDay(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            return null;
        }
        DateTime dt1 = new DateTime(startDate);
        DateTime dt2 = new DateTime(endDate);
        int day = Days.daysBetween(dt1, dt2).getDays();
        return Math.abs(day);
    }

    /**
     * 获取某月之前,之后某一个月最后一天,24:59:59
     *
     * @return
     */
    public static Date lastDay(Date date, Integer month) {
        DateTime dt1;
        if (month == null) {
            month = 0;
        }
        if (date == null) {
            dt1 = new DateTime().minusMonths(month);
        } else {
            dt1 = new DateTime(date).minusMonths(month);
        }
        DateTime lastDay = dt1.dayOfMonth().withMaximumValue().
                withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59);
        return lastDay.toDate();
    }

    /**
     * 获取某月月之前,之后某一个月第一天,00:00:00
     *
     * @return
     */
    public static Date firstDay(Date date, Integer month) {
        DateTime dt1;
        if (month == null) {
            month = 0;
        }
        if (date == null) {
            dt1 = new DateTime().minusMonths(month);
        } else {
            dt1 = new DateTime(date).minusMonths(month);
        }
        DateTime lastDay = dt1.dayOfMonth().withMinimumValue().
                withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0);
        return lastDay.toDate();
    }

    /**
     * 天加减
     *
     * @param date
     * @param offset
     * @return
     */
    public static Date addDay(Date date, int offset) {
        DateTime dt1;
        if (date == null) {
            dt1 = new DateTime().plusDays(offset);
            return dt1.toDate();
        }
        dt1 = new DateTime(date).plusDays(offset);
        return dt1.toDate();

    }

    /**
     * 小时加减
     *
     * @param date
     * @param offset
     * @return
     */
    public static Date addHours(Date date, int offset) {
        DateTime dt1;
        if (date == null) {
            dt1 = new DateTime().plusHours(offset);
            return dt1.toDate();
        }
        dt1 = new DateTime(date).plusHours(offset);
        return dt1.toDate();

    }

    /**
     * 年加减
     *
     * @param date
     * @param offset
     * @return
     */
    public static Date addYears(Date date, int offset) {
        DateTime dt1;
        if (date == null) {
            dt1 = new DateTime().plusYears(offset);
            return dt1.toDate();
        }
        dt1 = new DateTime(date).plusYears(offset);
        return dt1.toDate();

    }

    /**
     * 周加减
     *
     * @param date
     * @param offset
     * @return
     */
    public static Date addWeeks(Date date, int offset) {
        DateTime dt1;
        if (date == null) {
            dt1 = new DateTime().plusWeeks(offset);
            return dt1.toDate();
        }
        dt1 = new DateTime(date).plusWeeks(offset);
        return dt1.toDate();

    }

    /**
     * 分钟加减
     *
     * @param date
     * @param offset
     * @return
     */
    public static Date addMinutes(Date date, int offset) {
        DateTime dt1;
        if (date == null) {
            dt1 = new DateTime().plusMinutes(offset);
            return dt1.toDate();
        }
        dt1 = new DateTime(date).plusMinutes(offset);
        return dt1.toDate();

    }

    /**
     * 秒加减
     *
     * @param date
     * @param offset
     * @return
     */
    public static Date addSeconds(Date date, int offset) {
        DateTime dt1;
        if (date == null) {
            dt1 = new DateTime().plusSeconds(offset);
            return dt1.toDate();
        }
        dt1 = new DateTime(date).plusSeconds(offset);
        return dt1.toDate();

    }

    /**
     * 传入日期时间与当前系统日期时间的比较,
     * 若日期相同，则根据时分秒来返回 ,
     * 否则返回具体日期
     *
     * @return 日期或者 xx小时前||xx分钟前||xx秒前
     */
    public static String getNewUpdateDateString(Date now, Date createDate) {
        if (now == null || createDate == null) {
            return null;
        }
        Long time = (now.getTime() - createDate.getTime());
        if (time > (24 * 60 * 60 * 1000)) {
            return time / (24 * 60 * 60 * 1000) + "天前";
        } else if (time > (60 * 60 * 1000)) {
            return time / (60 * 60 * 1000) + "小时前";
        } else if (time > (60 * 1000)) {
            return time / (60 * 1000) + "分钟前";
        } else if (time >= 1000) {
            return time / 1000 + "秒前";
        }
        return "刚刚";
    }

    /**
     * 计算年龄
     *
     * @param birth
     * @return
     */
    public static int getAge(Date birth) {
        if (birth == null || "".equals(birth)) {
            return 0;
        }
        DateTime now = new DateTime();
        DateTime birthDate = new DateTime(birth);
        int day = Years.yearsBetween(birthDate, now).getYears();
        return Math.abs(day);
    }

    /**
     * 获取yyyyMMdd格式日期的年龄
     *
     * @param birth
     * @return
     */
    public static int getAge(String birth) {
        if (birth == null || "".equals(birth)) {
            return 0;
        }
        DateTime now = new DateTime();
        DateTime birthDate = new DateTime(birth);
        int day = Years.yearsBetween(birthDate, now).getYears();
        return Math.abs(day);
    }

    /**
     * 10位时间戳转字符串   默认为yyyy-MM-dd HH:mm:ss
     *
     * @param timestamp
     * @param format
     * @return
     */
    public static String timestampToStr10(Integer timestamp, String format) {
        if (StringUtils.isEmpty(format)) {
            return timestampToStr10(timestamp);
        }
        if (timestamp == null || timestamp == 0) {
            return "";
        }
        Long time = Long.valueOf(timestamp) * 1000;
        DateTime now = new DateTime(time);
        return now.toString(format);
    }

    /**
     * 10位时间戳转字符串yyyy-MM-dd HH:mm:ss
     *
     * @param timestamp
     * @return
     */
    public static String timestampToStr10(Integer timestamp) {
        if (timestamp == null || timestamp == 0) {
            return "";
        }
        Long time = Long.valueOf(timestamp);
        DateTime now = new DateTime(time);
        return now.toString(FORMAT_TIME);
    }

    /**
     * 13位时间戳转字符串   默认为yyyy-MM-dd HH:mm:ss:SSS
     *
     * @param timestamp
     * @param format
     * @return
     */
    public static String timestampToStr13(String timestamp, String format) {
        if (StringUtils.isEmpty(format)) {
            return timestampToStr13(timestamp);
        }
        if (StringUtils.isEmpty(timestamp)) {
            return "";
        }
        Long time = Long.valueOf(timestamp);
        DateTime now = new DateTime(time);
        return now.toString(format);
    }

    /**
     * 13位时间戳转字符串yyyy-MM-dd HH:mm:ss:SSS
     *
     * @param timestamp
     * @return
     */
    public static String timestampToStr13(String timestamp) {
        if (StringUtils.isEmpty(timestamp)) {
            return "";
        }
        Long time = Long.valueOf(timestamp);
        DateTime now = new DateTime(time);
        return now.toString(FORMAT_TIME_MINUTE_SSS);
    }

    /**
     * 系统当前的时间戳
     *
     * @return 系统当前的时间戳
     */
    public static Timestamp getTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }


    public static void main(String[] args) throws Throwable {
        //        System.out.println(format(day00(1,null)));
        //        System.out.println(format(day59(1,null)));
        //        System.out.println(lastDay(new Date(),2));
        //        System.out.println(firstDay(null,0));
        //          TimeZone zone1=TimeZone.getTimeZone("GMT+8");
        //          TimeZone zone2=TimeZone.getTimeZone("GMT-5");
        //          System.out.println(format(new Date(),zone1,FORMAT_TIME));
        //          System.out.println(format(new Date(),zone2,FORMAT_TIME));
        //
        //        System.out.println(format(day00(0,"2017-5-11","GMT+0")));
        //        System.out.println(format(day00(0,"2017-5-11","GMT+8")));
        //        System.out.println(format(day00(0,"2017-5-11","GMT-8")));
        //        Date date1 =parse("2017-05-11 17:53:52");
        //
        //        System.out.println(diffDay(date1,new Date()));

//        DateTime dateTime = new DateTime().withDayOfWeek(1);
//
//        DateTime dateTime1 = new DateTime().withDayOfWeek(7).withTime(0, 0, 0, 0);
//        System.out.println(format(dateTime.toDate()));
//
//        System.out.println(format(dateTime1.toDate()));
        DateTime now = new DateTime(Long.valueOf(1532567281) * 1000);
        System.out.println(timestampToStr13("1532568281000"));
        // 今天0点
        System.out.println(now.toString(FORMAT_TIME_MINUTE));

        System.out.println(now.getMillis() / 1000);

        System.out.println(getAge("1991-06-04"));
    }
}
