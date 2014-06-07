package org.jiucai.appframework.base.util;

import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.jiucai.appframework.common.util.BaseUtil;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * DateTime 工具类
 * referer: http://joda-time.sourceforge.net/api-release/index.html
 * <pre>
 * DateTime is the standard implementation of an unmodifiable datetime class.
 * The pattern syntax is mostly compatible with java.text.SimpleDateFormat

 Symbol  Meaning                      Presentation  Examples
 ------  -------                      ------------  -------
 G       era                          text          AD
 C       century of era (>=0)         number        20
 Y       year of era (>=0)            year          1996

 x       weekyear                     year          1996
 w       week of weekyear             number        27
 e       day of week                  number        2
 E       day of week                  text          Tuesday; Tue

 y       year                         year          1996
 D       day of year                  number        189
 M       month of year                month         July; Jul; 07
 d       day of month                 number        10

 a       halfday of day               text          PM
 K       hour of halfday (0~11)       number        0
 h       clockhour of halfday (1~12)  number        12

 H       hour of day (0~23)           number        0
 k       clockhour of day (1~24)      number        24
 m       minute of hour               number        30
 s       second of minute             number        55
 S       fraction of second           number        978

 z       time zone                    text          Pacific Standard Time; PST
 Z       time zone offset/id          zone          -0800; -08:00; America/Los_Angeles

 '       escape for text              delimiter
 ''      single quote                 literal       '
 * </pre>
 *
 * @author zhaidw
 */
public class DateTimeUtil extends BaseUtil  {
	
	/**
	 * 日期格式： 中国
	 */
	protected static final String DEFAULT_PATTERN = "yyyy-MM-dd";
	/**
	 * 地区： 中国
	 */
	protected static final Locale DEFAULT_LOCALE = Locale.SIMPLIFIED_CHINESE;
	
	/**
	 * 时区：正八区 / (UTC+08:00)北京，重庆，香港特别行政区，乌鲁木齐
	 */
	protected static final DateTimeZone DEFAULT_TIMEZONE = DateTimeZone.forOffsetHours(8);

	
	public static DateTimeFormatter getDateTimeFormatter(String pattern){
		
		return DateTimeFormat.forPattern(pattern).withZone(DEFAULT_TIMEZONE).withLocale(DEFAULT_LOCALE);
	}	
	
	/**
	 * 转换字符串为 DateTime 对象
	 * @param date
	 * @param pattern
	 * @return DateTime
	 */
	public static DateTime parseDateTime(String date, String pattern){
		
		DateTime dt = getDateTimeFormatter(pattern).parseDateTime(date);
		return dt;
	}
	
	
	/**
	 * 转换 date 为 DateTime 对象
	 * @param date
	 * @return DateTime
	 */
	public static DateTime getDateTime(Date date){
		return new DateTime(date.getTime(),DEFAULT_TIMEZONE);
	}
	
	/**
	 * 返回格式化的字符串
	 * @param dt
	 * @param pattern
	 * @return String
	 */
	public static String getFormatedDateTime(DateTime dt, String pattern){
		return dt.toString(pattern, DEFAULT_LOCALE);
	}
	
	
	/**
	 * 返回指定日期的字符串
	 * 
	 * @param date 指定的日期对象
	 * @param pattern 格式字符串，可选，默认 yyyy-mm-dd
	 * @return String
	 */
	public static String getFormatedDate(Date date, String pattern){
		
		return getDateTime(date).toString(pattern,DEFAULT_LOCALE);
	}
	
	
	/**
	 * 返回指定日期的字符串
	 * 
	 * @param days 日期相差的天数
	 * @param pattern 格式字符串，可选，默认 yyyy-mm-dd
	 * @return String
	 */
	public static String getFormatedDate(int days, String pattern) {
		
		String formatPattern = DEFAULT_PATTERN;
		
		if(StringUtils.isNotBlank(pattern)){
			formatPattern = pattern;
		}
		
		return getDateTime(new Date()).plusDays(days).toString(formatPattern);

	}

	
	/**
	 * 获取指定日期的年月
	 * 
	 * @param date 日期对象
	 * @return 格式, yyyymm, 示例值 200908
	 */
	public static String getYearMonth(Date date){

		return getFormatedDate(date,"yyyyMM");
	}
	
	/**
	 * 获取指定日期的年周
	 * 
	 * @param date 日期对象
	 * @return 格式, yyyyww, 示例值 200953
	 */
	public static String getYearWeek(Date date){
		
		return getFormatedDate(date,"xxxxww");	
		
	}
	

	/**
	 * 返回指定 年周(比如200953) 的第一天的日期
	 * <p>
	 * referer: http://joda-time.sourceforge.net/api-release/org/joda/time/format/DateTimeFormat.html
	 * </p>
	 * @return DateTime
	 */
	public static DateTime getDateTimeFromYearWeek(String yearWeek){
		
		DateTime dt = parseDateTime(yearWeek, "xxxxww");
		return dt;
	}
	
	/**
	 * 返回当前系统时间
	 * @return String
	 */
	public static String getCurrentTime() {
		return DateTimeUtil.getFormatedDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}


	
	
}
