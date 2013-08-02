package org.jiucai.appframework.common.util;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.joda.time.DateTime;

public abstract class BaseUtil {

	protected static Logs log = LogUtil.getLog(BaseUtil.class);
	
	/**
	 * 把 Map 中的 Object 对象转换成可 显示字符串
	 * 
	 * @param val
	 * @return String
	 */
	public static String convertMapValue(Object val) {
		String result = "";

		if (val instanceof Long || val instanceof Integer
				|| val instanceof String || val instanceof Character) {

			result = String.valueOf(val);

		} else if (val instanceof Float || val instanceof Double) {

			Double d = (Double) val;

			BigDecimal bd = new BigDecimal(d);

			int scale = bd.scale();

			scale = scale > 0 ? 4 : 0;

			bd = bd.setScale(scale, BigDecimal.ROUND_HALF_UP);

			StringBuffer tempValue = new StringBuffer();
			tempValue.append(bd.toString());

			// 没有小数位数的，末尾添加
//			if (tempValue.toString().indexOf(".") == -1) {
//				tempValue.append(".00");
//
//			}

			result = tempValue.toString();

		} else if (val instanceof BigDecimal) {

			// BigDecimal scale 属性是小数位数，有则说明是小数，否则可能是整型

			BigDecimal bd = (BigDecimal) val;

			int scale = bd.scale();

			scale = scale > 0 ? 4 : 0;

			bd = bd.setScale(scale, BigDecimal.ROUND_HALF_UP);

			result = bd.toString();

		} else if (val instanceof Date) {

			Date dt = (Date) val;

			DateTime d = new DateTime(dt.getTime());

			if (d.getHourOfDay() == 0 && d.getMinuteOfHour() == 0) {
				result = FastDateFormat.getInstance("yyyy-MM-dd").format(dt);
			} else {
				result = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss")
						.format(dt);
			}

		} else {

			if (null != val) {
				result = (String) val;
				log.warn("convertMapValue()无法转换的对象: " + val.getClass().getName() + " , 值: " + val);
			}

		}

		if (StringUtils.isBlank(result) || "null".equalsIgnoreCase(result)) {
			return "";
		}

		return result;
	}
	
	
	
}
