package org.jiucai.appframework.common.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

/**
 * 数值格式化工具类
 * @author zhaidw
 *
 */
public class ValueFormatUtil extends BaseUtil {

	public final static String PERSENT_STR = "%";
	public final static String RMB_STR = "￥";

	public static NumberFormat nf = NumberFormat.getInstance(Locale.CHINA);
	public static DecimalFormat df = new DecimalFormat("0.00");
	
	
	public static NumberFormat doubleFormater = new DecimalFormat("0.00");
	public static NumberFormat longFormater = new DecimalFormat("#");
	public static String nullValue = "-";

	/**
	 * 格式化百分比；如果有小数，则保留 doubleFormater 指定的位数
	 * @deprecated use  {@link #getPersentString }
	 * @param val
	 * @return String 
	 */
	@Deprecated
	public static String formatPercentString(final Object val) {
		return getPersentString(val);
	}

	/**
	 * 格式化数字；如果有小数，则保留 doubleFormater 指定的位数
	 * 
	 * @param val
	 * @return String
	 */
	@Deprecated
	public static String formatNumberString(final Object val) {
		String result = convertMapValue(val);

		if (StringUtils.isNotEmpty(result)) {

			if (NumberUtils.toLong(result) > 0) {
				result = longFormater.format(NumberUtils.toLong(result));
			} else if (NumberUtils.toDouble(result) > 0) {
				result = doubleFormater.format(NumberUtils.toDouble(result));
			}
		}

		return result;

	}

	/**
	 * 判断字符串值为null则返回 nullValue 常量代表的字符串
	 * 
	 * @param val
	 * @return String
	 */
	@Deprecated
	public static String formatNullString(final Object val) {
		String result = convertMapValue(val);
		if (StringUtils.isBlank(result) || "null".equalsIgnoreCase(result)) {
			result = nullValue;
		}
		return result;
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String money = getMoneyString(13356234234888.3242343d);
		System.out.println(money);

	}
	

	protected static NumberFormat getNumberFormat() {
		//保留两位小数
		nf.setMaximumFractionDigits(2);
		nf.setMinimumFractionDigits(2);
		//不使用千分位
		nf.setGroupingUsed(false);

		return nf;
	}

	protected static DecimalFormat getDecimalFormat() {
		return df;
	}

	

	public static String getNoneString() {
		return "--";
	}

	public static String getIntegerString(Object data) {
		String result = convertMapValue(data);
		if (StringUtils.isNotBlank(result)) {
			return result;
		} else {
			return getNoneString();
		}
	}

	// 比如 0.2356 返回 0.24 ,null 返回 --
	public static String getDoubleString(Object data) {
		String result = convertMapValue(data);
		if (StringUtils.isNotBlank(result)) {
			result = getNumberFormat().format(NumberUtils.toDouble(result));
			return result;
		} else {
			return getNoneString();
		}
	}

	public static String getMoneyString(Object data) {
		String result = convertMapValue(data);
		if (StringUtils.isNotBlank(result)) {
			result = getNumberFormat().format(NumberUtils.toDouble(result));
			return new StringBuffer("").append(RMB_STR).append(result)
					.toString();
		} else {
			return getNoneString();
		}
	}

	public static String getPersentString(Object data) {
		String result = convertMapValue(data);
		if (StringUtils.isNotBlank(result)) {
			result = getDecimalFormat().format(
					NumberUtils.toDouble(result) * 100);
			return new StringBuffer("").append(result).append(PERSENT_STR)
					.toString();
		} else {
			return getNoneString();
		}
	}
	

	public static String getHourString(final String hour){
		 String dateTime = "";
		 if(hour.equals("0")){
			 dateTime = "00:00-01:00";
		 }else  if(hour.equals("1")){
			 dateTime = "01:00-02:00";
		 }else  if(hour.equals("2")){
			 dateTime = "02:00-03:00";
		 }else  if(hour.equals("3")){
			 dateTime = "03:00-04:00";
		 }else  if(hour.equals("4")){
			 dateTime = "04:00-05:00";
		 }else if(hour.equals("5")){
			 dateTime = "05:00-06:00";
		 }else if(hour.equals("6")){
			 dateTime = "06:00-07:00";
		 }else if(hour.equals("7")){
			 dateTime = "07:00-08:00";
		 }else  if(hour.equals("8")){
			 dateTime = "08:00-09:00";
		 }else if(hour.equals("9")){
			 dateTime = "09:00-10:00";
		 }else  if(hour.equals("10")){
			 dateTime = "10:00-11:00";
		 }else  if(hour.equals("11")){
			 dateTime = "11:00-12:00";
		 }else  if(hour.equals("12")){
			 dateTime = "12:00-13:00";
		 }else  if(hour.equals("13")){
			 dateTime = "13:00-14:00";
		 }else  if(hour.equals("14")){
			 dateTime = "14:00-15:00";
		 }else  if(hour.equals("15")){
			 dateTime = "15:00-16:00";
		 }else  if(hour.equals("16")){
			 dateTime = "16:00-17:00";
		 }else if(hour.equals("17")){
			 dateTime = "17:00-18:00";
		 }else if(hour.equals("18")){
			 dateTime = "18:00-19:00";
		 }else  if(hour.equals("19")){
			 dateTime = "19:00-20:00";
		 }else if(hour.equals("20")){
			 dateTime = "20:00-21:00";
		 }else if(hour.equals("21")){
			 dateTime = "21:00-22:00";
		 }else  if(hour.equals("22")){
			 dateTime = "22:00-23:00";
		 }else if(hour.equals("23")){
			 dateTime = "23:00-24:00";
		 }
		 
		 return dateTime;
		 
	}
	
	
}
