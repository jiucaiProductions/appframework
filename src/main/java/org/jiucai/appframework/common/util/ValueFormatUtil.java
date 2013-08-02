package org.jiucai.appframework.common.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

/**
 * 数值格式化工具类
 * @author zhaidw
 *
 */
public class ValueFormatUtil extends BaseUtil {

	public static NumberFormat doubleFormater = new DecimalFormat("0.00");
	public static NumberFormat longFormater = new DecimalFormat("#");
	public static String nullValue = "-";

	/**
	 * 格式化百分比；如果有小数，则保留 doubleFormater 指定的位数
	 * 
	 * @param val
	 * @return String
	 */
	public static String formatPercentString(final Object val) {

		String tempVal = convertMapValue(val);
		StringBuffer result = null;

		if (StringUtils.isNotEmpty(tempVal)) {

			if (NumberUtils.toDouble(tempVal) > 0) {
				result = new StringBuffer(doubleFormater.format(NumberUtils
						.toDouble(tempVal) * 100)).append("%");
			} else if ("0".equals(tempVal) || "0.00".equals(tempVal)) {
				result = new StringBuffer(doubleFormater.format(0.00d))
						.append("%");
			} else {
				result = new StringBuffer(tempVal);
			}

		} else {
			result = new StringBuffer("");
		}

		return result.toString();
	}

	/**
	 * 格式化数字；如果有小数，则保留 doubleFormater 指定的位数
	 * 
	 * @param val
	 * @return String
	 */
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
	public static String formatNullString(final Object val) {
		String result = convertMapValue(val);
		if (StringUtils.isBlank(result) || "null".equalsIgnoreCase(result)) {
			result = nullValue;
		}
		return result;
	}

	public static void main(String[] args) {
		String v1Before = "-";
		String v1After = ValueFormatUtil.formatPercentString(v1Before);

		System.out.println(v1Before + " : " + v1After);
	}
}
