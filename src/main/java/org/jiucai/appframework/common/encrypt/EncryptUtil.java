package org.jiucai.appframework.common.encrypt;

import org.apache.commons.lang.StringUtils;
import org.jiucai.appframework.common.encode.Base64;
import org.jiucai.appframework.common.encode.MD5;

/**
 * 动态密钥的加密解密算法
 * 
 * @author zhaidw
 * 
 */
public class EncryptUtil {

	/**
	 * DES key size must be equal to 56 DESede(TripleDES) key size must be equal
	 * to 112 or 168 AES key size must be equal to 128, 192 or 256,but 192 and
	 * 256 bits may not be available Blowfish key size must be multiple of 8,
	 * and can only range from 32 to 448 (inclusive) RC2 key size must be
	 * between 40 and 1024 bits RC4(ARCFOUR) key size must be between 40 and
	 * 1024 bits
	 **/

	/**
	 * 
	 */
	private static final String charsetName = "UTF-8";

	/**
	 * 加密字符串
	 * 
	 * @param msg
	 *            字符串
	 * @param key
	 *            密钥
	 * @return 返回加密后的字符串
	 * @throws Exception
	 */
	public static synchronized String encrypt(String msg, String key) throws Exception {
		return MD5.encode(encode(msg, key));
	}

	public static synchronized String encode(String msg, String key) throws Exception {
		String encodeMsg = "";
		byte[] data;

		if (StringUtils.isEmpty(msg) || StringUtils.isEmpty(key)) {
			return encodeMsg;
		}

		data = XXTEA.encrypt(msg.getBytes(charsetName),
				key.getBytes(charsetName));

		encodeMsg = Base64.encode(data);

		return encodeMsg;
	}

	public static synchronized String decode(String encodeMsg, String key) throws Exception {

		String decodeMsg = "";

		if (StringUtils.isEmpty(encodeMsg) || StringUtils.isEmpty(key)) {
			return decodeMsg;
		}

		byte[] data = Base64.decode(encodeMsg);
		data = XXTEA.decrypt(data, key.getBytes(charsetName));
		decodeMsg = new String(data, charsetName);

		return decodeMsg;

	}

	/**
	 * 将二进制转换成16进制
	 * 
	 * @param buf
	 * @return String
	 */
	public static String byte2Hex(byte[] buf) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 将16进制转换为二进制
	 * 
	 * @param hexStr
	 * @return byte[]
	 */
	public static byte[] hex2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
					16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

}
