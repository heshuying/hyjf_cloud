package com.hyjf.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * 类描述：时间操作定义类
 *
 * @version 1.0
 */
public class GetDate extends PropertyEditorSupport {

	private static final Logger logger = LoggerFactory.getLogger(GetDate.class);


	private static ThreadLocal<Map<String,SimpleDateFormat>> dateFormatThreadLocal = new ThreadLocal<>();

	/**
	 * 根据时间处理格式获取转换器
	 * @author zhangyk
	 * @date 2018/9/7 18:29
	 */
	private static SimpleDateFormat getDateFormat(String format){
		Map<String,SimpleDateFormat>  dateFormatMap = dateFormatThreadLocal.get();
		SimpleDateFormat simpleDateFormat = null;
		if (dateFormatMap == null){
			dateFormatMap = new HashMap<>();
			simpleDateFormat = new SimpleDateFormat(format);
			dateFormatMap.put(format,simpleDateFormat);
			dateFormatThreadLocal.set(dateFormatMap);
		}else{
			simpleDateFormat = dateFormatMap.get(format);
			if (simpleDateFormat == null){
				simpleDateFormat = new SimpleDateFormat(format);
				dateFormatMap.put(format,simpleDateFormat);
			}
		}
		return simpleDateFormat;
	}

	public static final String date_sdf_key = "yyyy-MM-dd";

	public static final String yyyyMMdd_key = "yyyyMMdd";

	public static final String date_sdf_wz_key = "yyyy年MM月dd日";

	public static final String time_sdf_key = "yyyy-MM-dd HH:mm";

	public static final String yyyymmddhhmmss_key = "yyyyMMddHHmmss";

	public static final String yyyymmddhhmmSS_key = "yyyyMMddHHmmSS";

	public static final String short_time_sdf_key = "HH:mm";

	public static final String datetimeFormat_key = "yyyy-MM-dd HH:mm:ss";

	public static final String datetimeFormathhmm_key = "yyyy-MM-dd HH:mm";

	public static final String datesdf_key = "yyyy.MM.dd";

	public static final String yyyyMM_key = "yyyy-MM";

	public static final String mmDD_key = "MM-dd";






	// 各种时间格式
	public static final SimpleDateFormat date_sdf = new SimpleDateFormat(date_sdf_key);
	public static final SimpleDateFormat yyyyMMdd = new SimpleDateFormat(yyyyMMdd_key);
	public static final SimpleDateFormat date_sdf_wz = new SimpleDateFormat(date_sdf_wz_key);
	public static final SimpleDateFormat time_sdf = new SimpleDateFormat(time_sdf_key);
	public static final SimpleDateFormat yyyymmddhhmmss = new SimpleDateFormat(yyyymmddhhmmss_key);
	public static final SimpleDateFormat yyyymmddhhmmssSS = new SimpleDateFormat(yyyymmddhhmmSS_key);
	public static final SimpleDateFormat short_time_sdf = new SimpleDateFormat(short_time_sdf_key);
	public static final SimpleDateFormat datetimeFormat = new SimpleDateFormat(datetimeFormat_key);
	public static final SimpleDateFormat datetimeFormathhmm = new SimpleDateFormat(datetimeFormathhmm_key);
	public static final SimpleDateFormat datesdf = new SimpleDateFormat(datesdf_key);
	public static final SimpleDateFormat yyyyMMdf = new SimpleDateFormat(yyyyMM_key);

	// 以毫秒表示的时间
	private static final long DAY_IN_MILLIS = (long) 24 * 3600 * 1000;
	private static final long HOUR_IN_MILLIS = (long) 3600 * 1000;
	private static final long MINUTE_IN_MILLIS = (long) 60 * 1000;
	private static final long SECOND_IN_MILLIS = 1000;

	/**
	 * gc.add(1,-1)表示年份减一. gc.add(2,-1)表示月份减一. gc.add(3.-1)表示周减一.
	 * gc.add(5,-1)表示天减一.
	 *
	 * @param ymd
	 * @param cout
	 * @return
	 */
	public static Date countDate(int ymd, int cout) {
		GregorianCalendar gc = new GregorianCalendar();
		Date d = new Date();
		gc.setTime(d);
		gc.add(ymd, cout);
		gc.set(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc.get(Calendar.DATE));
		return gc.getTime();
	}

	/**
	 * gc.add(1,-1)表示年份减一. gc.add(2,-1)表示月份减一. gc.add(3.-1)表示周减一.
	 * gc.add(5,-1)表示天减一.
	 *
	 * @param ymd
	 * @param cout
	 * @return
	 */
	public static Date countDate(String date, int ymd, int cout) {
		GregorianCalendar gc = new GregorianCalendar();
		Date d = str2Date(date, getDateFormat(datetimeFormat_key));
		gc.setTime(d);
		gc.add(ymd, cout);
		gc.set(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc.get(Calendar.DATE));
		return gc.getTime();
	}
	
	/**
	 * gc.add(1,-1)表示年份减一. gc.add(2,-1)表示月份减一. gc.add(3.-1)表示周减一.
	 * gc.add(5,-1)表示天减一.
	 * @param date 10位时间戳
	 * @param ymd
	 * @param cout
	 * @return 10位时间戳
	 */
	public static Integer countDate(Integer date, int ymd, int cout) {
		GregorianCalendar gc = new GregorianCalendar();
		//Date d = str2Date(date, datetimeFormat);
		gc.setTime(new Date(date*1000L));
		gc.add(ymd, cout);
		gc.set(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc.get(Calendar.DATE), gc.get(Calendar.HOUR_OF_DAY), gc.get(Calendar.MINUTE),gc.get(Calendar.SECOND));
		return (int)(gc.getTime().getTime()/1000);
	}

	/**
	 * gc.add(1,-1)表示年份减一. gc.add(2,-1)表示月份减一. gc.add(3.-1)表示周减一.
	 * gc.add(5,-1)表示天减一.
	 *
	 * @param ymd
	 * @param cout
	 * @return yyyy-MM-dd
	 */
	public static String getCountDate(int ymd, int cout) {
		return GetDate.date2Str(GetDate.countDate(ymd, cout), GetDate.getDateFormat(date_sdf_key));
	}

	/**
	 * gc.add(1,-1)表示年份减一. gc.add(2,-1)表示月份减一. gc.add(3.-1)表示周减一.
	 * gc.add(5,-1)表示天减一.
	 *
	 * @param ymd
	 * @param cout
	 * @return
	 */
	public static String countDateYYYYMMDDHHMMSS(String date, int ymd, int cout) {
		return GetDate.date2Str(GetDate.countDate(date, ymd, cout), GetDate.getDateFormat(datetimeFormat_key));
	}

	/**
	 * 计算年龄
	 *
	 * @param birthDay
	 * @return
	 */
	public static String getAge(Date birthDay) {
		Calendar cal = Calendar.getInstance();
		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

		cal.setTime(birthDay);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				}
			} else {
				age--;
			}
		}

		return age + "";
	}

	/**
	 * 计算后返回的结果类似为 Thu May 15 00:00:00 CST 2014
	 *
	 * @param ymd
	 * @param cout
	 * @return
	 */
	public static Date countDateYYYYMMDD(int ymd, int cout) {
		return GetDate.str2Date(GetDate.date2Str(GetDate.countDate(ymd, cout), getDateFormat(date_sdf_key)), getDateFormat(date_sdf_key));
	}

	// 指定模式的时间格式
	private static SimpleDateFormat getSDFormat(String pattern) {
		return getDateFormat(pattern);
	}

	/**
	 * 当前日历，这里用中国时间表示
	 *
	 * @return 以当地时区表示的系统当前日历
	 */
	public static Calendar getCalendar() {
		return Calendar.getInstance();
	}

	/**
	 * 指定毫秒数表示的日历
	 *
	 * @param millis
	 *            毫秒数
	 * @return 指定毫秒数表示的日历
	 */
	public static Calendar getCalendar(long millis) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(millis));
		return cal;
	}

	/**
	 * 当前日期
	 *
	 * @return 系统当前时间
	 */
	public static Date getDate() {
		return new Date();
	}

	/**
	 * 指定毫秒数表示的日期
	 *
	 * @param millis
	 *            毫秒数
	 * @return 指定毫秒数表示的日期
	 */
	public static Date getDate(long millis) {
		return new Date(millis);
	}

	/**
	 * 指定秒数表示的日期
	 *
	 * @param seconds 秒数
	 *            
	 * @return 指定毫秒数表示的日期
	 */
	public static Date getDate(int seconds) {
		return new Date((long)seconds*1000);
	}

	
	/**
	 * 时间戳转换为字符串
	 *
	 * @param time
	 * @return
	 */
	public static String timestamptoStr(Timestamp time) {
		Date date = null;
		if (null != time) {
			date = new Date(time.getTime());
		}
		return date2Str(getDateFormat(date_sdf_key));
	}

	/**
	 * 时间戳转换为字符串
	 *
	 * @param timeParam
	 * @return
	 */
	public static String timestamptoStrYYYYMMDDHHMM(String timeParam) {
		Timestamp time = getTimestamp(Long.valueOf(timeParam) * 1000);
		Date date = null;
		if (null != time) {
			date = new Date(time.getTime());
			return date2Str(date, getDateFormat(datetimeFormathhmm_key));
		}
		return "";
	}

	/**
	 * 时间戳转换为字符串
	 *
	 * @param timeParam
	 * @return
	 */
	public static String timestamptoStrYYYYMMDDHHMMSS(String timeParam) {
		Timestamp time = getTimestamp(Long.valueOf(timeParam) * 1000);
		Date date = null;
		if (null != time) {
			date = new Date(time.getTime());
			return date2Str(date, getDateFormat(datetimeFormat_key));
		}
		return "";
	}

	/**
	 * 时间戳转换为字符串（yyyy-MM-dd）
	 *
	 * @param timeParam
	 * @return
	 */
	public static String times10toStrYYYYMMDD(Integer timeParam) {
		Timestamp time = getTimestamp(Long.valueOf(timeParam) * 1000);
		Date date = null;
		if (null != time) {
			date = new Date(time.getTime());
			return date2Str(date, getDateFormat(date_sdf_key));
		}
		return "";
	}
	
	/**
	 * 10位时间戳转换为字符串（）
	 *
	 * @param timeParam
	 * @return
	 */
	public static String timestamptoNUMStrYYYYMMDDHHMMSS(Integer timeParam) {
		Timestamp time = getTimestamp(Long.valueOf(timeParam) * 1000);
		Date date = null;
		if (null != time) {
			date = new Date(time.getTime());
			return date2Str(date, getDateFormat(datetimeFormat_key));
		}
		return "";
	}


	/**
	 * 时间戳转换为字符串  (yyyy-MM-dd)
	 *
	 * @param timeParam
	 * @return
	 */
	public static String timestamptoStrYYYYMMDD(Integer timeParam) {
		Timestamp time = getTimestamp(Long.valueOf(timeParam) * 1000);
		Date date = null;
		if (null != time) {
			date = new Date(time.getTime());
			return date2Str(date, getDateFormat(date_sdf_key));
		}
		return "";
	}
	
	/**
	 * 时间戳转换为字符串  (yyyy-MM-dd)
	 *
	 * @param timeParam
	 * @return
	 */
	public static String timestamptoStrYYYYMMDDHHMMSS(Integer timeParam) {
		Timestamp time = getTimestamp(Long.valueOf(timeParam) * 1000);
		Date date = null;
		if (null != time) {
			date = new Date(time.getTime());
			return date2Str(date, getDateFormat(date_sdf_key));
		}
		return "";
	}
	
	/**
	 * 时间戳转换为字符串  (yyyy.MM.dd)
	 * @author LiBin
	 * @param timeParam
	 * @return
	 */
	public static String timestampToStrYYYYMMDD(String timeParam) {
		Timestamp time = getTimestamp(Long.valueOf(timeParam) * 1000);
		Date date = null;
		if (null != time) {
			date = new Date(time.getTime());
			return date2Str(date, getDateFormat(datesdf_key));
		}
		return "";
	}
	
	
	/**
	 * 字符串转换时间戳
	 *
	 * @param str
	 * @return
	 */
	public static Timestamp str2Timestamp(String str) {
		Date date = str2Date(str, getDateFormat(date_sdf_key));
		if(date == null){
			date = str2Date(str,getDateFormat(datetimeFormat_key));
		}
		return new Timestamp(date.getTime());
	}

	/**
	 * 字符串转换成日期
	 *
	 * @param str
	 * @param sdf
	 * @return
	 */
	public static Date str2Date(String str, SimpleDateFormat sdf) {
		if (null == str || "".equals(str)) {
			return null;
		}
		Date date = null;
		try {
			date = sdf.parse(str);
			return date;
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 日期转换为字符串
	 *
	 * @param date_sdf
	 *            日期格式
	 * @return 字符串
	 */
	public static String date2Str(SimpleDateFormat date_sdf) {
		Date date = getDate();
		return date_sdf.format(date);
	}

	/**
	 * 格式化时间
	 *
	 * @param data
	 * @param format
	 * @return
	 */
	public static String dataformat(String data, String format) {
		SimpleDateFormat sformat = getDateFormat(format);//new SimpleDateFormat(format);
		Date date = null;
		try {
			date = sformat.parse(data);
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		return sformat.format(date);
	}

	/**
	 * 日期转换为字符串
	 *
	 * @param date
	 *            日期
	 * @param date_sdf
	 *            日期格式
	 * @return 字符串
	 */
	public static String date2Str(Date date, SimpleDateFormat date_sdf) {
		if (null == date) {
			return null;
		}
		//synchronized (date_sdf){
			return date_sdf.format(date);
		//}
	}

	/**
	 * 日期转换为字符串
	 *
	 * @param format
	 *            日期格式
	 * @return 字符串
	 */
	public static String getDate(String format) {
		Date date = new Date();
		SimpleDateFormat sdf = getDateFormat(format);//new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 指定毫秒数的时间戳
	 *
	 * @param millis
	 *            毫秒数
	 * @return 指定毫秒数的时间戳
	 */
	public static Timestamp getTimestamp(long millis) {
		return new Timestamp(millis);
	}

	/**
	 * 以字符形式表示的时间戳
	 *
	 * @param time
	 *            毫秒数
	 * @return 以字符形式表示的时间戳
	 */
	public static Timestamp getTimestamp(String time) {
		return new Timestamp(Long.parseLong(time));
	}

	/**
	 * 系统当前的时间戳
	 *
	 * @return 系统当前的时间戳
	 */
	public static Timestamp getTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * 指定日期的时间戳
	 *
	 * @param date
	 *            指定日期
	 * @return 指定日期的时间戳
	 */
	public static Timestamp getTimestamp(Date date) {
		return new Timestamp(date.getTime());
	}

	/**
	 * 指定日历的时间戳
	 *
	 * @param cal
	 *            指定日历
	 * @return 指定日历的时间戳
	 */
	public static Timestamp getCalendarTimestamp(Calendar cal) {
		return new Timestamp(cal.getTime().getTime());
	}

	public static Timestamp gettimestamp() {
		Date dt = new Date();
		//DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowTime = getDateFormat(datetimeFormat_key).format(dt);
		Timestamp buydate = Timestamp.valueOf(nowTime);
		return buydate;
	}

	/**
	 * 系统时间的毫秒数
	 *
	 * @return 系统时间的毫秒数
	 */
	public static long getMillis() {
		return  System.currentTimeMillis();
	}

	/**
	 * 指定日历的毫秒数
	 *
	 * @param cal
	 *            指定日历
	 * @return 指定日历的毫秒数
	 */
	public static long getMillis(Calendar cal) {
		return cal.getTime().getTime();
	}

	/**
	 * 指定日期的毫秒数
	 *
	 * @param date
	 *            指定日期
	 * @return 指定日期的毫秒数
	 */
	public static long getMillis(Date date) {
		return date.getTime();
	}

	public static long getMillis10(Date date) {
		return date.getTime()/1000;
	}

	/**
	 * 指定时间戳的毫秒数
	 *
	 * @param ts
	 *            指定时间戳
	 * @return 指定时间戳的毫秒数
	 */
	public static long getMillis(Timestamp ts) {
		return ts.getTime();
	}

	// ////////////////////////////////////////////////////////////////////////////
	// formatDate
	// 将日期按照一定的格式转化为字符串
	// ////////////////////////////////////////////////////////////////////////////

	/**
	 * 默认方式表示的系统当前日期，具体格式：年-月-日
	 *
	 * @return 默认日期按“年-月-日“格式显示
	 */
	public static String formatDate() {
		return getDateFormat(date_sdf_key).format(getCalendar().getTime());
	}

	/**
	 * 默认方式表示的系统当前日期，具体格式：月-日
	 *
	 * @return 默认日期按“月-日“格式显示
	 */
	public static String formatDateMMDD() {
		return getDateFormat(mmDD_key).format(getCalendar().getTime());
	}


	/**
	 * 获取时间字符串
	 */
	public static String getDataString(SimpleDateFormat formatstr) {
		return formatstr.format(getCalendar().getTime());
	}
	
	/**
     * 获取时间字符串
     */
    public static String getDataString(SimpleDateFormat formatstr, Integer addDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, addDay);
        return formatstr.format(calendar.getTime());
    }

	/**
	 * 指定日期的默认显示，具体格式：年-月-日
	 *
	 * @param cal
	 *            指定的日期
	 * @return 指定日期按“年-月-日“格式显示
	 */
	public static String formatDate(Calendar cal) {
		return getDateFormat(date_sdf_key).format(cal.getTime());
	}

	/**
	 * 指定日期的默认显示，具体格式：年-月-日
	 *
	 * @param date
	 *            指定的日期
	 * @return 指定日期按“年-月-日“格式显示
	 */
	public static String formatDate(Date date) {
		return getDateFormat(date_sdf_key).format(date);
	}

	/**
	 * 指定毫秒数表示日期的默认显示，具体格式：年-月-日
	 *
	 * @param millis
	 *            指定的毫秒数
	 * @return 指定毫秒数表示日期按“年-月-日“格式显示
	 */
	public static String formatDate(long millis) {
		if (millis == 0L) {
			return "";
		}
		return getDateFormat(date_sdf_key).format(new Date(millis));
	}

	/**
	 * 指定毫秒数表示日期的默认显示，具体格式：年-月-日
	 *
	 * @param millis
	 *            指定的毫秒数
	 * @return 指定毫秒数表示日期按“年-月-日“格式显示
	 */
	public static String formatDateTime(long millis) {
		if (millis == 0L) {
			return "";
		}
		return getDateFormat(datetimeFormat_key).format(new Date(millis));
	}

	/**
	 * 默认日期按指定格式显示
	 *
	 * @param pattern
	 *            指定的格式
	 * @return 默认日期按指定格式显示
	 */
	public static String formatDate(String pattern) {
		return getSDFormat(pattern).format(getCalendar().getTime());
	}

	/**
	 * 指定日期按指定格式显示
	 *
	 * @param cal
	 *            指定的日期
	 * @param pattern
	 *            指定的格式
	 * @return 指定日期按指定格式显示
	 */
	public static String formatDate(Calendar cal, String pattern) {
		return getSDFormat(pattern).format(cal.getTime());
	}

	/**
	 * 指定日期按指定格式显示
	 *
	 * @param date
	 *            指定的日期
	 * @param pattern
	 *            指定的格式
	 * @return 指定日期按指定格式显示
	 */
	public static String formatDate(Date date, String pattern) {
		return getSDFormat(pattern).format(date);
	}

	// ////////////////////////////////////////////////////////////////////////////
	// formatTime
	// 将日期按照一定的格式转化为字符串
	// ////////////////////////////////////////////////////////////////////////////

	/**
	 * 默认方式表示的系统当前日期，具体格式：年-月-日 时：分
	 *
	 * @return 默认日期按“年-月-日 时：分“格式显示
	 */
	public static String formatTime() {
		return getDateFormat(time_sdf_key).format(getCalendar().getTime());
	}

	/**
	 * 默认方式表示的系统当前日期，具体格式：年-月-日 时：分：秒
	 *
	 * @author 孙亮
	 * @since 2015年12月15日08:39:54
	 * @return 默认日期按“年-月-日 时：分：秒“格式显示
	 */
	public static String formatTime2() {
		return getDateFormat(datetimeFormat_key).format(getCalendar().getTime());
	}

	/**
	 * 指定毫秒数表示日期的默认显示，具体格式：年-月-日 时：分
	 *
	 * @param millis
	 *            指定的毫秒数
	 * @return 指定毫秒数表示日期按“年-月-日 时：分“格式显示
	 */
	public static String formatTime(long millis) {
		return getDateFormat(time_sdf_key).format(new Date(millis));
	}

	/**
	 * 指定日期的默认显示，具体格式：年-月-日 时：分
	 *
	 * @param cal
	 *            指定的日期
	 * @return 指定日期按“年-月-日 时：分“格式显示
	 */
	public static String formatTime(Calendar cal) {
		return getDateFormat(time_sdf_key).format(cal.getTime());
	}

	/**
	 * 指定日期的默认显示，具体格式：年-月-日 时：分
	 *
	 * @param date
	 *            指定的日期
	 * @return 指定日期按“年-月-日 时：分“格式显示
	 */
	public static String formatTime(Date date) {
		return getDateFormat(time_sdf_key).format(date);
	}

	// ////////////////////////////////////////////////////////////////////////////
	// formatShortTime
	// 将日期按照一定的格式转化为字符串
	// ////////////////////////////////////////////////////////////////////////////

	/**
	 * 默认方式表示的系统当前日期，具体格式：时：分
	 *
	 * @return 默认日期按“时：分“格式显示
	 */
	public static String formatShortTime() {
		return getDateFormat(short_time_sdf_key).format(getCalendar().getTime());
	}

	/**
	 * 指定毫秒数表示日期的默认显示，具体格式：时：分
	 *
	 * @param millis
	 *            指定的毫秒数
	 * @return 指定毫秒数表示日期按“时：分“格式显示
	 */
	public static String formatShortTime(long millis) {
		return getDateFormat(short_time_sdf_key).format(new Date(millis));
	}

	/**
	 * 指定日期的默认显示，具体格式：时：分
	 *
	 * @param cal
	 *            指定的日期
	 * @return 指定日期按“时：分“格式显示
	 */
	public static String formatShortTime(Calendar cal) {
		return getDateFormat(short_time_sdf_key).format(cal.getTime());
	}

	/**
	 * 指定日期的默认显示，具体格式：时：分
	 *
	 * @param date
	 *            指定的日期
	 * @return 指定日期按“时：分“格式显示
	 */
	public static String formatShortTime(Date date) {
		return getDateFormat(short_time_sdf_key).format(date);
	}

	// ////////////////////////////////////////////////////////////////////////////
	// parseDate
	// parseCalendar
	// parseTimestamp
	// 将字符串按照一定的格式转化为日期或时间
	// ////////////////////////////////////////////////////////////////////////////

	/**
	 * 根据指定的格式将字符串转换成Date 如输入：2003-11-19 11:20:20将按照这个转成时间
	 *
	 * @param src
	 *            将要转换的原始字符窜
	 * @param pattern
	 *            转换的匹配格式
	 * @return 如果转换成功则返回转换后的日期
	 * @throws ParseException
	 * @throws ParseException
	 */
	public static Date parseDate(String src, String pattern) throws ParseException {
		return getSDFormat(pattern).parse(src);

	}

	/**
	 * 根据指定的格式将字符串转换成Date 如输入：2003-11-19 11:20:20将按照这个转成时间
	 *
	 * @param src
	 *            将要转换的原始字符窜
	 * @param pattern
	 *            转换的匹配格式
	 * @return 如果转换成功则返回转换后的日期
	 * @throws ParseException
	 * @throws ParseException
	 */
	public static Calendar parseCalendar(String src, String pattern) throws ParseException {

		Date date = parseDate(src, pattern);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	public static String formatAddDate(String src, String pattern, int amount) throws ParseException {
		Calendar cal;
		cal = parseCalendar(src, pattern);
		cal.add(Calendar.DATE, amount);
		return formatDate(cal);
	}

	/**
	 * 根据指定的格式将字符串转换成Date 如输入：2003-11-19 11:20:20将按照这个转成时间
	 *
	 * @param src
	 *            将要转换的原始字符窜
	 * @param pattern
	 *            转换的匹配格式
	 * @return 如果转换成功则返回转换后的时间戳
	 * @throws ParseException
	 * @throws ParseException
	 */
	public static Timestamp parseTimestamp(String src, String pattern) throws ParseException {
		Date date = parseDate(src, pattern);
		return new Timestamp(date.getTime());
	}

	// ////////////////////////////////////////////////////////////////////////////
	// dateDiff
	// 计算两个日期之间的差值
	// ////////////////////////////////////////////////////////////////////////////

	/**
	 * 计算两个时间之间的差值，根据标志的不同而不同
	 *
	 * @param flag
	 *            计算标志，表示按照年/月/日/时/分/秒等计算
	 * @param calSrc
	 *            减数
	 * @param calDes
	 *            被减数
	 * @return 两个日期之间的差值
	 */
	public static int dateDiff(char flag, Calendar calSrc, Calendar calDes) {

		long millisDiff = getMillis(calSrc) - getMillis(calDes);

		if (flag == 'y') {
			return (calSrc.get(Calendar.YEAR) - calDes.get(Calendar.YEAR));
		}

		if (flag == 'd') {
			return (int) (millisDiff / DAY_IN_MILLIS);
		}

		if (flag == 'h') {
			return (int) (millisDiff / HOUR_IN_MILLIS);
		}

		if (flag == 'm') {
			return (int) (millisDiff / MINUTE_IN_MILLIS);
		}

		if (flag == 's') {
			return (int) (millisDiff / SECOND_IN_MILLIS);
		}

		return 0;
	}

	/**
	 * 获取当前时间
	 * @return
	 */
    public static String getNowTimeYYYYMMDDHHMMSS() {
		return yyyymmddhhmmss.format(new Date());
    }

	/**
	 * 10位时间戳转换为字符串
	 * @param timeParam
	 * @return
	 */
	public static String timestamptoNUMStrYYYYMMDDHHMMSS2(Integer timeParam) {
		Timestamp time = getTimestamp(Long.valueOf(timeParam) * 1000);
		Date date = null;
		if (null != time) {
			date = new Date(time.getTime());
			return date2Str(date, yyyymmddhhmmss);
		}
		return "";
	}

	/**
	 * String类型 转换为Date, 如果参数长度为10 转换格式”yyyy-MM-dd“ 如果参数长度为19 转换格式”yyyy-MM-dd
	 * HH:mm:ss“ * @param text String类型的时间值
	 */
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (text!=null && !"".equals(text)) {
			try {
				if (text.indexOf(":") == -1 && text.length() == 10) {
					setValue(getDateFormat(date_sdf_key).parse(text));
				} else if (text.indexOf(":") > 0 && text.length() == 19) {
					setValue(getDateFormat(datetimeFormat_key).parse(text));
				} else {
					throw new IllegalArgumentException("Could not parse date, date format is error ");
				}
			} catch (ParseException ex) {
				IllegalArgumentException iae = new IllegalArgumentException("Could not parse date: " + ex.getMessage());
				iae.initCause(ex);
				throw iae;
			}
		} else {
			setValue(null);
		}
	}

	public static int getYear() {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(getDate());
		return calendar.get(Calendar.YEAR);
	}

	public static String getServerDateTime(int Kind, Date currentTime) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String strDate = formatter.format(currentTime);
		StringTokenizer token = new StringTokenizer(strDate, "-");
		String year = token.nextToken();
		String month = token.nextToken();
		String day = token.nextToken();
		String hh = token.nextToken();
		String mm = token.nextToken();
		String ss = token.nextToken();
		String strServerDateTime = "";
		switch (Kind) {
		case 1:
			strServerDateTime = year + month + day;
			break;
		case 2:
			strServerDateTime = year + "-" + month + "-" + day + " " + hh;
			break;
		case 3:
			strServerDateTime = hh + mm + ss;
			break;
		case 4:
			strServerDateTime = hh + ":" + mm + ":" + ss;
			break;
		case 5:
			strServerDateTime = year + month + day + " " + hh + mm + ss;
			break;
		case 6:
			strServerDateTime = year + "-" + month + "-" + day + " " + hh + ":" + mm + ":" + ss;
			break;
		case 7:
			strServerDateTime = year + "-" + month + "-" + day + "|" + hh + ":" + mm + ":" + ss;
			break;
		case 8:
			strServerDateTime = year + month + day + hh + mm + ss;
			break;
		case 9:
			strServerDateTime = year + "-" + month + "-" + day + " " + hh + ":" + mm + ":" + ss;
			break;
		case 10:
			strServerDateTime = year + "-" + month + "-" + day;
			break;
		case 11:
			strServerDateTime = month + "." + day;
			break;
		case 12:
			strServerDateTime = month + day;
			break;
		case 13:
			strServerDateTime = year + month;
			break;
		case 14:
			strServerDateTime = year + "-" + month + "-" + day + " " + hh + ":" + mm;
			break;
		case 15:
			strServerDateTime = day;
			break;
		default:
			break;
		}
		return strServerDateTime;
	}

	// -----------------------------------------------------------

	/**
	 * 获取创建时间
	 *
	 * @return
	 */
	public static int getNowTime10() {
		return (int) (getMillis() / 1000);
	}

	/**
	 * 原来的getNowTime,感觉太繁琐了,似乎没什么用
	 * @auth sunpeikai
	 * @param
	 * @return
	 */
	@Deprecated
	public static Date getNowTime2(){
		Date date=new Date();
		SimpleDateFormat temp = getDateFormat(datetimeFormat_key); //new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date1=temp.format(date);
		Date date2= null;
		try {
			date2 = temp.parse(date1);
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		return date2;
	}
	/**
	 * 获取系统当前时间
	 * @auth sunpeikai
	 * @param
	 * @return
	 */
	public static Date getNowTime(){
		return new Date();
	}
	/**
	 * 转换10时间
	 *
	 * @return
	 */
	public static String get10Time(String date) {
		if (date == null || "".equals(date)) {
			return "";
		}
		return String.valueOf(str2Timestamp(date).getTime() / 1000);
	}
	
	/**
	 * 转换10位时间，支持加减秒数（加1秒，则参数1，减一秒，则参数-1）
	 * @param date
	 * @param second
	 * @return
	 */
	public static String get10Time(String date, int second) {
		if (date == null || "".equals(date)) {
			return "";
		}
		long secondTimes = str2Timestamp(date).getTime() / 1000 + second;
		return String.valueOf(secondTimes);
	}

	/**
	 * 获得当前的时间 例：201512
	 *
	 * @return
	 */
	public static String getMonthDay() {
		String monthday = "";
		Calendar cal = Calendar.getInstance();
		int yearnow = cal.get(Calendar.YEAR);
		int monthnow = cal.get(Calendar.MONTH) + 1;
		if (monthnow < 10) {
			monthday = String.valueOf(yearnow) + "0" + String.valueOf(monthnow);
		} else {
			monthday = String.valueOf(yearnow) + String.valueOf(monthnow);
		}
		return monthday;
	}
	/**
	 * 获得当前的时间月份 例：201512
	 *
	 * @return
	 */
	public static String getMonth() {
		String month = "";
		Calendar cal = Calendar.getInstance();
		int monthnow = cal.get(Calendar.MONTH) + 1;
		if (monthnow < 10) {
			month =  "0" + String.valueOf(monthnow);
		} else {
			month =  String.valueOf(monthnow);
		}
		return month;
	}

	/**
	 * 获得时间开始 例：传入 2015-12-03 返回 2015-12-03 00:00:00
	 *
	 * @param curDate
	 * @return
	 */
	public static String getDayStart(String curDate) {
		return curDate + " 00:00:00";
	}

	/**
	 * 获得时间结束 例：传入 2015-12-03 返回 2015-12-03 23:59:59
	 *
	 * @param curDate
	 * @return
	 */
	public static String getDayEnd(String curDate) {
		return curDate + " 23:59:59";
	}
	
	/**
	 * 
	 * 获取某一天的开始时间
	 * @author hsy
	 * @param date
	 * @return
	 */
	public static Date getDayStartOfSomeDay(Date date){
	    String dayStartString = getDateFormat(date_sdf_key).format(date) + " 00:00:00";
	    SimpleDateFormat d = getDateFormat(datetimeFormat_key);//new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date2 = null;
        try{
            date2 = d.parse(dayStartString);
        } catch (ParseException e) {
            logger.error(e.getMessage());
        }
        
        return date2;
        
	}

	public static Date getDayEndOfSomeDay(Date date){
		String dayStartString = getDateFormat(date_sdf_key).format(date) + " 23:59:59";
		SimpleDateFormat d = getDateFormat(datetimeFormat_key);//new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date2 = null;
		try{
			date2 = d.parse(dayStartString);
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}

		return date2;

	}

	/**
	 *
	 * 获取某一天的开始时间
	 * @author hsy
	 * @param date
	 * @return
	 */
	public static int getDayStart11(Date date){
		String dayStartString = getDateFormat(date_sdf_key).format(date) + " 00:00:00";
		SimpleDateFormat d = getDateFormat(datetimeFormat_key);//new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date2 = new Date();
		try{
			date2 = d.parse(dayStartString);
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}

		return (int)(date2.getTime()/1000);

	}


	/**
	 * 
	 * 获取某一天的开始时间戳
	 * @author hsy
	 * @param date（2017-07-10）
	 * @return
	 */
   public static int getDayStart10(String date){
        String dayStartString = date + " 00:00:00";
        SimpleDateFormat d = getDateFormat(datetimeFormat_key);//new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date2 = new Date();
        try{
            date2 = d.parse(dayStartString);
        } catch (ParseException e) {
            logger.error(e.getMessage());
        }
        
        return (int)(date2.getTime()/1000);
        
    }
   
   /**
    * 
    * 获取某一天的开始时间
    * @author hsy
    * @param date
    * @return
    */
   public static int getDayStart10(Date date){
       String dayStartString = getDateFormat(date_sdf_key).format(date) + " 00:00:00";
       SimpleDateFormat d =  getDateFormat(datetimeFormat_key);//new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       Date date2 = null;
       try{
           date2 = d.parse(dayStartString);
       } catch (ParseException e) {
           logger.error(e.getMessage());
       }
       
       return (int)(date2.getTime()/1000);
       
   }
   
   /**
    * 
    * 获取某一天的结束时间
    * @author hsy
    * @param date（2017-07-10）
    * @return
    */
   public static int getDayEnd10(String date){
       String dayStartString = date + " 23:59:59";
       SimpleDateFormat d = getDateFormat(datetimeFormat_key);//new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       Date date2 = new Date();
       try{
           date2 = d.parse(dayStartString);
       } catch (ParseException e) {
           logger.error(e.getMessage());
       }
       
       return (int)(date2.getTime()/1000);
       
   }

	
	/**
	 * 
	 * 获取某一天的开始时间字符串
	 * @author hsy
	 * @param date
	 * @return
	 */
    public static String getDayStart(Date date){
        String dayStartString = getDateFormat(date_sdf_key).format(date) + " 00:00:00";
        return dayStartString;
    }

   /**
    * 
    * 获取某一天的结束时间
    * @author hsy
    * @param date
    * @return
    */
   public static int getDayEnd10(Date date){
        String dayStartString = getDateFormat(date_sdf_key).format(date) + " 23:59:59";
        SimpleDateFormat d = getDateFormat(datetimeFormat_key);//new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date2 = new Date();
        try{
            date2 = d.parse(dayStartString);
        } catch (ParseException e) {
            logger.error(e.getMessage());
        }
        
        return (int)(date2.getTime()/1000);
        
    }

   /**
    * 
    * 获取某一天的结束时间字符串
    * @author hsy
    * @param date
    * @return
    */
   public static String getDayEnd(Date date){
       String dayEndString = getDateFormat(date_sdf_key).format(date) + " 23:59:59";
       return dayEndString;
       
   }

	/**
	 * yyyyMMddHHMMSS字符串转换时间戳
	 *
	 * @param dateParam
	 * @return
	 */
	public static Integer strYYYYMMDDTimestamp(String dateParam) {
		if (dateParam == null || "".equals(dateParam)) {
			return 0;
		}
		//SimpleDateFormat datetimeFormathhmm = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = str2Date(dateParam, getDateFormat(yyyymmddhhmmss_key));
		if(date == null){
			date = str2Date(dateParam,getDateFormat(yyyymmddhhmmSS_key));
		}
		long timestamp = new Timestamp(date.getTime()).getTime() / 1000;
		return Integer.valueOf(String.valueOf(timestamp));
	}
	/**
	 * yyyy-MM-dd HH:mm:ss字符串转换时间戳
	 *
	 * @param dateParam
	 * @return
	 */
	public static Integer strYYYYMMDDHHMMSS2Timestamp(String dateParam) {
		if (dateParam == null || "".equals(dateParam)) {
			return 0;
		}
		//SimpleDateFormat datetimeFormathhmm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = str2Date(dateParam, getDateFormat(time_sdf_key));
		long timestamp = new Timestamp(date.getTime()).getTime() / 1000;
		return Integer.valueOf(String.valueOf(timestamp));
	}

	/**
	 * yyyy-MM-dd HH:mm:ss字符串转换时间戳
	 *
	 * @param dateParam
	 * @return
	 */
	public static Integer strYYYYMMDDHHMMSS2Timestamp2(String dateParam) {
		if (dateParam == null || "".equals(dateParam)) {
			return 0;
		}
		//SimpleDateFormat datetimeFormathhmm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = str2Date(dateParam, getDateFormat(datetimeFormat_key));
		long timestamp = new Timestamp(date.getTime()).getTime() / 1000;
		return Integer.valueOf(String.valueOf(timestamp));
	}
	
	/**
	 * yyyy-MM-dd字符串转换时间戳
	 *
	 * @param dateParam
	 * @return
	 */
	public static Integer strYYYYMMDD2Timestamp2(String dateParam) {
		if (dateParam == null || "".equals(dateParam)) {
			return 0;
		}
		//SimpleDateFormat datetimeFormathhmm = new SimpleDateFormat("yyyy-MM-dd");
		Date date = str2Date(dateParam, getDateFormat(date_sdf_key));
		long timestamp = new Timestamp(date.getTime()).getTime() / 1000;
		return Integer.valueOf(String.valueOf(timestamp));
	} 
	/**
	 * yyyyMMddHHMMSS字符串转换时间戳
	 *
	 * @param dateParam
	 * @return
	 */
	public static Integer strYYYYMMDD3Timestamp3(String dateParam) {
		if (dateParam == null || "".equals(dateParam)) {
			return 0;
		}
		//SimpleDateFormat datetimeFormathhmm = new SimpleDateFormat("yyyyMMdd");
		Date date = str2Date(dateParam, getDateFormat(yyyyMMdd_key));
		long timestamp = new Timestamp(date.getTime()).getTime() / 1000;
		return Integer.valueOf(String.valueOf(timestamp));
	}

	/**
	 * 计算两个日期之间相差的天数
	 *
	 * @param smdate
	 *            较小的时间
	 * @param bdate
	 *            较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(Date smdate, Date bdate) throws ParseException {
		SimpleDateFormat sdf = getDateFormat(date_sdf_key);//new SimpleDateFormat("yyyy-MM-dd");
		smdate = sdf.parse(sdf.format(smdate));
		bdate = sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * yyyy-MM-dd HH:mm:ss字符串转换时间戳
	 *
	 * @param dateParam
	 * @return
	 */
	public static Integer dateString2Timestamp(String dateParam) {
		if (dateParam == null || "".equals(dateParam)) {
			return 0;
		}
		SimpleDateFormat fmt = null;
		if(dateParam.length() > 10){
			fmt = getDateFormat(datetimeFormat_key);//new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}else{
			fmt = getDateFormat(date_sdf_key);//new SimpleDateFormat("yyyy-MM-dd");
		}
		Date date = str2Date(dateParam, fmt);
		long timestamp = new Timestamp(date.getTime()).getTime() / 1000;
		return Integer.valueOf(String.valueOf(timestamp));
	}

	
	
	/**
	 * 字符串的日期格式的计算
	 */
	public static int daysBetween(String smdate, String bdate) throws ParseException {
		SimpleDateFormat sdf = getDateFormat(date_sdf_key);//new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(smdate));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(bdate));
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * Int的日期格式的计算
	 */
	public static int daysBetween(int sdate, int edate) throws ParseException {
		long between_days = (edate - sdate) / (3600 * 24);
		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * gc.add(1,-1)表示年份减一. gc.add(2,-1)表示月份减一. gc.add(3.-1)表示周减一.
	 * gc.add(5,-1)表示天减一.
	 *
	 * @param ymd
	 * @param cout
	 * @return
	 */
	public static Date countDate(Date date, int ymd, int cout) {
		GregorianCalendar gc = new GregorianCalendar();
		Date d = date;
		gc.setTime(d);
		gc.add(ymd, cout);
		gc.set(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc.get(Calendar.DATE));
		return gc.getTime();
	}

	/**
	 * 获取毫秒值
	 *
	 * @return
	 */
	public static Integer getMyTimeInMillis() {
		// 当前时间
		long milliseconds = System.currentTimeMillis() / 1000;
		return (int) milliseconds;
	}

	/**
	 * 获取毫秒日期
	 *
	 * @return
	 */
	public static String getDateTimeMyTimeInMillis(Integer milliseconds) {
		// 时间转换
		Date date = new Date((long) milliseconds * 1000);
		SimpleDateFormat sdf = getDateFormat(datetimeFormat_key);//new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

	public static String getDateTimeMyTimeInMillis(Date date) {
		// 时间转换
		SimpleDateFormat sdf = getDateFormat(datetimeFormat_key);//new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

	/**
	 * 获取毫秒日期 yyyy-MM-dd HH:mm:ss(24小时制)
	 *
	 * @return
	 */
	public static String getDateTimeMyTime(Integer milliseconds) {
		// 时间转换
		Date date = new Date((long) milliseconds * 1000);
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return getDateFormat(datetimeFormat_key).format(date);
	}

	/**
	 * 获取毫日期时分秒
	 *
	 * @return
	 */
	public static String getDateMyTimeInMillis(Integer milliseconds) {
		// 时间转换
		Date date = new Date((long) milliseconds * 1000);
		SimpleDateFormat sdf = getDateFormat(date_sdf_key);//new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}

	/**
	 * 获取毫日期时分秒
	 *
	 * @return
	 */
	public static String getDateMyTimeInMillisYYYYMMDD(Integer milliseconds) {
		// 时间转换
		Date date = new Date((long) milliseconds * 1000);
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return getDateFormat(yyyyMMdd_key).format(date);
	}
	/**
	 * 获取次日零时时间
	 *
	 * @return
	 */
	public static Integer getTomorrowTimeInMillis() {
		String dates = GetDate.getDayStart(GetDate.date2Str(GetDate.countDateYYYYMMDD(5, 1), GetDate.getDateFormat(date_sdf_key)));
		int time = (int) (GetDate.str2Timestamp(dates).getTime() / 1000);
		return time;
	}

	/**
	 * 获取afterMin分钟后的日期
	 *
	 * @param date
	 * @param afterMin
	 * @return
	 */
	public static Date getMinutesAfter(Date date, int afterMin) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, afterMin);// 24小时制
		date = cal.getTime();
		return date;
	}

	/**
	 * 获取afterMin分钟后的日期
	 *
	 * @param millis
	 * @param afterMin
	 * @return
	 */
	public static Date getMinutesAfter(Integer millis, int afterMin) {
		// SimpleDateFormat sdf= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		// 前面的millis是秒数，先乘1000得到毫秒数，再转为java.util.Date类型
		Long milliscond = (long) (millis) * 1000;
		Date date = new Date(milliscond);
		// String sDateTime = sdf.format(date); //得到精确到秒的表示：08/31/2006 21:08:00

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, afterMin);// 24小时制
		date = cal.getTime();
		return date;
	}

	/**
	 * 时间转换成格式字符串
	 *
	 * @param date
	 *            时间+日期
	 * @return 根据格式字符串返回字符串
	 */
	public static String dateToString2(Date date, String format) {
		SimpleDateFormat d = getDateFormat(format);//new SimpleDateFormat(format);
		String date2 = d.format(date);
		return date2;
	}

	/**
	 * 时间类型转换为integer类型 例如：当前时间：2016-01-15 12:23:23 结果：2016-01-15
	 * 00:00:00的时间戳除以1000后的Integer类型
	 * 
	 * @param date
	 * @return
	 */
	public static Integer getSearchStartTime(Date date) {
		return GetDate.strYYYYMMDDHHMMSS2Timestamp2(GetDate.getDayStart(GetDate.date2Str(date, GetDate.getDateFormat(date_sdf_key))));
	}

	/**
	 * 时间类型转换为integer类型 例如：当前时间：2016-01-15 12:23:23 结果：2016-01-15
	 * 23:59:59的时间戳除以1000后的Integer类型
	 * 
	 * @param date
	 * @return
	 */
	public static Integer getSearchEndTime(Date date) {
		return GetDate.strYYYYMMDDHHMMSS2Timestamp2(GetDate.getDayEnd(GetDate.date2Str(date, GetDate.getDateFormat(date_sdf_key))));
	}

	/**
	 * 时间类型转换为integer类型 例如：当前时间：2016-01-15 12:23:23 结果：2016-01-15
	 * 00:00:00的时间戳除以1000后的Integer类型
	 * 
	 * @param date
	 * @return
	 */
	public static String getSearchStartTime(String date) {
		if (date == null || "".equals(date)) {
			return "";
		}
		return String.valueOf(GetDate.strYYYYMMDDHHMMSS2Timestamp2(GetDate.getDayStart(date)));
	}

	/**
	 * 时间类型转换为integer类型 例如：当前时间：2016-01-15 12:23:23 结果：2016-01-15
	 * 23:59:59的时间戳除以1000后的Integer类型
	 * 
	 * @param date
	 * @return
	 */
	public static String getSearchEndTime(String date) {
		if (date == null || "".equals(date)) {
			return "";
		}
		return String.valueOf(GetDate.strYYYYMMDDHHMMSS2Timestamp2(GetDate.getDayEnd(date)));
	}
	/**
	 * 得到某时某刻日期+时间字符串用以比较大小 20150821131919
	 * 
	 * @param date
	 *            日期+时间
	 * @return 得到某时某刻日期+时间字符串用以比较大小 20150821131919
	 */
	public static String getcurrenttimestr(Date date) {
		String currenttime = GetDate.dateToString(date);
		currenttime = currenttime.replaceAll("-", "").replaceAll(" ", "")
				.replaceAll(":", "");
		return currenttime;
	}

	/**
	 * 时间转换成格式字符串
	 * 
	 * @param date
	 *            时间+日期
	 * @return 根据格式字符串返回字符串
	 */
	public static String dateToString(Date date) {
		SimpleDateFormat d = getDateFormat(datetimeFormat_key);//new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date2 = d.format(date);
		return date2;
	}

	/**
	 * 时间转换成格式字符串
	 * 
	 * @param date
	 *            时间+日期
	 * @return 根据格式字符串返回字符串
	 */
	public static String dateToString2(Date date) {
		SimpleDateFormat d = getDateFormat(date_sdf_key);//new SimpleDateFormat("yyyy-MM-dd");
		String date2 = d.format(date);
		return date2;
	}

	/**
	 * 时间转换成格式字符串
	 * 
	 * @param date
	 *            时间+日期
	 * @param format
	 *            格式字符串
	 * @return 根据格式字符串返回字符串
	 */
	public static String dateToString3(Date date, String format) {
		SimpleDateFormat d = getDateFormat(format);//new SimpleDateFormat(format);
		String date2 = d.format(date);
		return date2;
	}

	/**
	 * 格式字符串转换成时间,字符串必须是 2015-08-21 13:19:19的格式
	 * 
	 * @param date
	 *            时间字符串
	 * @return 20150821131919
	 */
	public static Date stringToDate(String date) {
		SimpleDateFormat d = getDateFormat(datetimeFormat_key);//new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date2 = null;
		try {
			date2 = d.parse(date);
		} catch (ParseException e) {
			logger.error(e.getMessage());
			return null;
		}
		return date2;
	}

	/**
	 * 格式字符串转换成时间,字符串必须是 2015-08-21的格式
	 * 
	 * @param date
	 *            时间字符串
	 * @return 20150821
	 */
	public static Date stringToDate2(String date) {
		SimpleDateFormat d = getDateFormat(date_sdf_key);//new SimpleDateFormat("yyyy-MM-dd");
		Date date2 = null;
		try {
			date2 = d.parse(date);
		} catch (ParseException e) {
			return null;
		}
		return date2;
	}

	/**
	 * 根据传入的格式字符串转换成时间,字符串必须是 2015-08-21的格式
	 * 
	 * @param date
	 *            时间字符串
	 * @return 根据格式字符串返回值
	 */
	public static Date stringToDate3(String date, String format) {
		SimpleDateFormat d = getDateFormat(format);//new SimpleDateFormat(format);
		Date date2 = null;
		try {
			date2 = d.parse(date);
		} catch (ParseException e) {
			logger.error(e.getMessage());
			return null;
		}
		return date2;
	}

	/**
	 * 计算两个时间之间的时间差-用于比较程序运行时常,来优化
	 * 
	 * @param begin
	 *            开始日期+时间
	 * @param end
	 *            结束日期+时间
	 * @return XX填XX小时XX分XX秒XX毫秒
	 */
	public static String countTime(Date begin, Date end) {
		long between = 0;
		String res="";
		try {
			between = (end.getTime() - begin.getTime());// 得到两者的毫秒数
			long day = between / (24 * 60 * 60 * 1000);
			long hour = (between / (60 * 60 * 1000) - day * 24);
			long min = ((between / (60 * 1000)) - day * 24 * 60 - hour * 60);
			long s = (between / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
			long ms = (between - day * 24 * 60 * 60 * 1000 - hour * 60 * 60
					* 1000 - min * 60 * 1000 - s * 1000);
			res = day + "天" + hour + "小时" + min + "分" + s + "秒" + ms
					+ "毫秒";
			return res;
		} catch (Exception ex) {
			res="出错鸟";
		}
		return res;
	}

	/**
	 * 计算两个日期内的自然日数量
	 * 
	 * @param begin 开始日期
	 * @param end 结束日期
	 * @return
	 */
	public static Integer countDate(Date begin, Date end) {
		Date beginStr = GetDate.stringToDate2(GetDate.dateToString2(begin));
		Date endStr = GetDate.stringToDate2(GetDate.dateToString2(end));
		long between = 0;
		try {
			between = (endStr.getTime() - beginStr.getTime());// 得到两者的毫秒数
			if (between < 0) {
				return 0;
			} else if (between == 0) {
				return 1;
			} else {
				Integer day = Integer.parseInt(between / (24 * 60 * 60 * 1000)
						+ "");
				return day;
			}
		} catch (Exception ex) {
			System.out.println("出错鸟");
		}
		return 0;
	}

	/**
	 * 获取今日的几天前或几天后的日期
	 * 
	 * @param before
	 *            几天前或几天后，若是几天前，则为负值
	 * @return
	 */
	public static Date getTodayBeforeOrAfter(int before) {
		Calendar calendar = Calendar.getInstance();// 此时打印它获取的是系统当前时间
		calendar.add(Calendar.DATE, before);
		Date date = calendar.getTime();
		return date;
	}

	/**
	 * 获取某日的几天前或几天后的日期
	 * 
	 * @param before
	 *            几天前或几天后，若是几天前，则为负值
	 * @return
	 */
	public static Date getSomeDayBeforeOrAfter(Date date, int before) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, before);
		Date result = calendar.getTime();
		return result;
	}

	/**
	 * 获取某时间的几分前或几分后的日期
	 * 
	 * @param before
	 *            几天前或几天后，若是几天前，则为负值
	 * @return
	 */
	public static Date getSomeTimeBeforeOrAfterMin(Date date, int before) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, before);
		Date result = calendar.getTime();
		return result;
	}

	/**
	 * 获取某个日期的周几的日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date getZHOUJI(Date date, Integer zhouji) {
		Calendar calander = Calendar.getInstance();
		calander.setTime(date);
		calander.set(Calendar.DAY_OF_WEEK, zhouji);
		return calander.getTime();
	}
	
	/**
	 * 获取某日期的月份的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getYUECHU(Date date) {
		Calendar calander = Calendar.getInstance();
		calander.setTime(date);
		calander.set(Calendar.DATE, 1);
		return calander.getTime();
	}

	/**
	 * 获取某日期的月份的最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getYUEMO(Date date) {
		Calendar calander = Calendar.getInstance();
		calander.setTime(date);
		calander.set(Calendar.DATE,
				calander.getActualMaximum(Calendar.DAY_OF_MONTH));
		return calander.getTime();
	}

	/**
	 * 获取某日期的季度的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getJICHU(Date date) {
		String dateStr = GetDate.dateToString2(date);
		String jidu = dateStr.substring(5, 7);
		String resultDateStr = dateStr.substring(0, 5);
		if ("01".equals(jidu) || "02".equals(jidu) || "03".equals(jidu)) {
			resultDateStr += "01-01";
		} else if ("04".equals(jidu) || "05".equals(jidu) || "06".equals(jidu)) {
			resultDateStr += "04-01";
		}
		if ("07".equals(jidu) || "08".equals(jidu) || "09".equals(jidu)) {
			resultDateStr += "07-01";
		}
		if ("10".equals(jidu) || "11".equals(jidu) || "12".equals(jidu)) {
			resultDateStr += "10-01";
		}
		return GetDate.stringToDate2(resultDateStr);
	}

	/**
	 * 获取某日期的季度的最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getJIMO(Date date) {
		String dateStr = GetDate.dateToString2(date);
		String jidu = dateStr.substring(5, 7);
		String resultDateStr = dateStr.substring(0, 5);
		if ("01".equals(jidu) || "02".equals(jidu) || "03".equals(jidu)) {
			resultDateStr += "03-31";
		} else if ("04".equals(jidu) || "05".equals(jidu) || "06".equals(jidu)) {
			resultDateStr += "06-30";
		}
		if ("07".equals(jidu) || "08".equals(jidu) || "09".equals(jidu)) {
			resultDateStr += "09-30";
		}
		if ("10".equals(jidu) || "11".equals(jidu) || "12".equals(jidu)) {
			resultDateStr += "12-31";
		}
		return GetDate.stringToDate2(resultDateStr);
	}
	/**
	 * 
	 * @Title dateToFormatString
	 * @Description 将date转换成指定格式的字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String dateToDateFormatStr(Date date, String format) {
		SimpleDateFormat d = getDateFormat(format);//new SimpleDateFormat(format);
		String date2 = d.format(date);
		return date2;
	}
	
	/**
	 * 
	 * @Title stringToFormatDate
	 * @Description 格式字符串转换成指定格式的日期或日期+时间
	 * @param date
	 * @param format
	 * @return
	 */
	public static Date stringToFormatDate(String date, String format) {
		SimpleDateFormat d = getDateFormat(format);//new SimpleDateFormat(format);
		Date date2 = null;
		try {
			date2 = d.parse(date);
		} catch (ParseException e) {
			logger.error(e.getMessage());
			return null;
		}
		return date2;
	}
	
	 /**
     * 获取当前日期是星期几<br>
     * 
     * @param dt
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
			w = 0;
		}
        return weekDays[w];
    }
    
    /**
     * 功能描述：返回小时
     * 
     * @param date
     *            日期
     * @return
     */

    public static int getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);

    }

	/**
	 * Date转Int10
	 *
	 * @param date
	 *            指定日期
	 * @return 指定日期的时间戳
	 */
	public static int getTime10(Date date) {
		return (int) (date.getTime()/1000);
}

	/**
	 * 返回距离某个日期多少年(不满按一年计算)
	 * @param dateStart 字符串必须是 2015-08的格式
	 * @return
	 */
	public static Integer getYearFromDate(String dateStart) {
		Calendar calendar1 = Calendar.getInstance();
		int yearNow = calendar1.get(Calendar.YEAR);
		int monthNow = calendar1.get(Calendar.MONTH) + 1;
		Date date = stringToDate3(dateStart, yyyyMM_key);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(date);
		int yearDate = calendar2.get(Calendar.YEAR);
		int monthDate = calendar2.get(Calendar.MONTH) + 1;
		int result = yearNow - yearDate;
		if (monthNow > monthDate) {
			return result + 1;
		} else {
			return result;
		}
	}

	/**
	 * 返回yyyymmdd的int值
	 *
	 * @param seconds 秒数
	 *
	 * @return 指定毫秒数表示的日期
	 */
	public static int getIntYYMMDD(int seconds) {
		Date repayEndDate = new Date((long)seconds * 1000);
		return getIntYYMMDD(repayEndDate);
	}

	/**
	 * 返回yyyymmdd的int值
	 *
	 * @return 指定毫秒数表示的日期
	 */
	public static int getIntYYMMDD(Date repayEndDate) {
		String enddateStr = getDateFormat(yyyyMMdd_key).format(repayEndDate);
		return Integer.parseInt(enddateStr);
	}

	// add 汇计划三期 汇计划自动出借 liubin 20180515 start
	/**
	 * 根据HH:mm得到日期
	 *
	 * @param hhmm (HH:mm)
	 *            指定的时分 按“时：分“格式
	 * @return
	 */
	public static Date getDateFromShortTime(String hhmm) {
		try {
			return short_time_sdf.parse(hhmm);
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 根据当前时间得到只有HH:mm部分的日期
	 * @return
	 */
	public static Date getShortTimeDate() {
		return getDateFromShortTime(short_time_sdf.format(getDate()));
	}

	/**
	 * 判断时间是否属于该时间段
	 * @param nowTime
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
		Calendar date = Calendar.getInstance();
		date.setTime(nowTime);

		Calendar begin = Calendar.getInstance();
		begin.setTime(beginTime);

		Calendar end = Calendar.getInstance();
		end.setTime(endTime);

		if (date.compareTo(begin) >= 0 && date.compareTo(end) <= 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取某日期的月份的第一天
	 *
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOnMonth(Date date) {
		Calendar calander = Calendar.getInstance();
		calander.setTime(date);
		calander.set(Calendar.DATE, 1);
		return getSomeDayStart(calander.getTime());
	}

	/**
	 * 获取某日期的月份的最后一天
	 *
	 * @param date
	 * @return
	 */
	public static Date getLastDayOnMonth(Date date) {
		Calendar calander = Calendar.getInstance();
		calander.setTime(date);
		calander.set(Calendar.DATE,
				calander.getActualMaximum(Calendar.DAY_OF_MONTH));
		return getSomeDayEnd(calander.getTime());
	}

	/**
	 *
	 * 获取某一天的开始时间
	 * @author hsy
	 * @param date（2017-07-10）
	 * @return
	 */
	public static Date getSomeDayStart(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 *
	 * 获取某一天的结束时间
	 * @author hsy
	 * @param date（2017-07-10）
	 * @return
	 */
	public static Date getSomeDayEnd(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}

	/**
	 * 获取指定格式当前字符串  (yyyy.MM.dd)
	 * @return
	 */
	public static String getFormatDateStr() {
		return date2Str(new Date(), datesdf);
	}

	/**
	 * 昨日结束时间
	 *
	 * @param currentDate
	 * @return
	 */
	public static Date getYesterdayEndTime(Date currentDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		return getSomeDayEnd(calendar.getTime());
	}

	// add 汇计划三期 汇计划自动出借 liubin 20180515 end

	public static void main(String[] args) {
		System.out.println(getMillis10(new Date()));
	}

	/**
	 * 时间戳转YYYYMM
	 * @param timeParam
	 * @return
	 */
	public static String times10toStrYYYYMM(String timeParam) {
		Timestamp time = getTimestamp(Long.valueOf(timeParam) * 1000);
		Date date = null;
		if (null != time) {
			date = new Date(time.getTime());
			return date2Str(date, new SimpleDateFormat("yyyy-MM"));
		}
		return "";
	}

	/**
	 * 默认方式表示的系统当前日期，具体格式：年月日
	 *
	 */
	public static String formatTime3() {
		return new SimpleDateFormat("yyyyMMdd").format(getCalendar().getTime());
	}

	/**
	 * 默认方式表示的系统当前日期，具体格式：年月
	 * @return
	 */
	public static String formatTimeYYYYMM() {
		return new SimpleDateFormat("yyyyMM").format(getCalendar().getTime());
	}

	/**
	 * 获得当前月有多少天
	 * @param dateStr
	 * @return
	 */
	public static String getDaysOfMonth(String dateStr) {
		Date date = null;
		try{
			date =  new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)+"";
	}

	/**
	 * 获取当前月最后一天
	 * @return
	 */
	public static Integer getLastDayOfMonth() {
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		return  getDayEnd10(ca.getTime());
	}

	/**
	 * 获取当前月最后一天
	 * @return
	 */
	public static Date getLastDayOfMonthDate() {
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		return  ca.getTime();
	}
	/**
	 * 获取当前月第一天
	 * @return
	 */
	public static Integer getFirstDayOfMonth() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 0);
		//设置为1号,当前日期既为本月第一天
		c.set(Calendar.DAY_OF_MONTH,1);
		return getDayStart11(c.getTime());
	}

	/**
	 * 获取当前月第一天
	 * @return
	 */
	public static Date getFirstDayOfMonthDate() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 0);
		//设置为1号,当前日期既为本月第一天
		c.set(Calendar.DAY_OF_MONTH,1);
		return c.getTime();
	}
}