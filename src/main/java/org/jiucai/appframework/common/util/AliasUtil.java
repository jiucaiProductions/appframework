package org.jiucai.appframework.common.util;

/**
 * 序列值混淆工具
 * 
 * <pre>
 * v1.0 build 2011-03-21
 * 
 * 此工具用来将长整型值进行编码
 * 
 * </pre>
 * 
 * @author zhaidw
 */
public class AliasUtil extends BaseUtil {

	private static final long base_num = 10000L;
	private static final int base_len = 4;

	/**
	 * 编码
	 * 
	 * @param num
	 *            要编码的数值
	 * @return 编码后的字符串
	 */
	public static String encode(Long num) {
		String result = null;
		try {
			String ts = String.valueOf(System.currentTimeMillis());
			ts = ts.substring(ts.length() - base_len, ts.length()).replace('0',
					'1');
			long n = num * base_num + Long.valueOf(ts);
			result = Long.toString(n, Character.MAX_RADIX).toLowerCase();

		} catch (Exception e) {
			log.error("encode failed: {}", e);
		}
		return result;

	}

	/**
	 * 解码
	 * 
	 * @param str
	 *            要解码的字符串
	 * @return 解码后的数值
	 */
	public static Long decode(String str) {
		Long num = 0L;
		try {
			num = Long.parseLong(str, Character.MAX_RADIX);
			num = num / base_num;
		} catch (Exception e) {
			log.error("decode failed: {}", e);
		}
		return num;

	}

}
