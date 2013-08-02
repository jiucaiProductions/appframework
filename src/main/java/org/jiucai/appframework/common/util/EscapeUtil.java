package org.jiucai.appframework.common.util;

/**
 * html 字符转义
 * 
 * @author zhaidw
 * 
 */
public class EscapeUtil extends BaseUtil {

	/**
	 * 对字符转义
	 * <pre>
	 * 测试字符串 &lt;input/&gt;"'test&lt;script&gt;alert(1);&lt;/script&gt;
	 * </pre>
	 * @param data
	 *            要转义的字符串
	 * @return 转以后的字符串
	 */
	public static String escape(String data) {

		if (null == data) {
			return null;
		}

		data = data.trim();

		//绝对不能替换，否则 js 编码后会出现乱码！
		//data = data.replaceAll(" ", "&nbsp;");

		data = data.replaceAll("<", "&lt;");
		data = data.replaceAll(">", "&gt;");

		data = data.replaceAll("\"", "&#034;");
		data = data.replaceAll("'", "&#039;");

		return data;
	}

}
