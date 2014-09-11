package org.jiucai.appframework.common.crypto;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.jiucai.appframework.common.encrypt.AbstractEncryptor;

/**
 * AES 对称加密解码算法, JDK 实现的封装
 * @author jiucai
 *
 */
public class AES extends AbstractEncryptor {
	
	//AES可以使用128、192、和256位密钥
	protected static int keyLength = 256;
	protected static String algorithmKey = "AES";

	/**
	 * 加密
	 * 
	 * @param content  需要加密的内容
	 * @param password  加密密码
	 * @return 加密后的字节数组
	 */
	public static byte[] encrypt(String content, String password) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance(algorithmKey);
			kgen.init(keyLength, new SecureRandom(password.getBytes(charsetName)));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, algorithmKey);
			Cipher cipher = Cipher.getInstance(algorithmKey);// 创建密码器
			byte[] byteContent = content.getBytes(charsetName);
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(byteContent);
			return result; // 加密
		} catch (Throwable e) {
			log.error("encrypt failed",e);
		}
		return null;
	}

	/**
	 * 解密
	 * 
	 * @param content  待解密内容
	 * @param password  解密密钥
	 * @return 解密后的字节数组
	 */
	public static byte[] decrypt(byte[] content, String password) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance(algorithmKey);
			kgen.init(keyLength, new SecureRandom(password.getBytes(charsetName)));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, algorithmKey);
			Cipher cipher = Cipher.getInstance(algorithmKey);// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(content);
			return result; // 加密
		} catch (Throwable e) {
			log.error("decrypt failed",e);
		}
		return null;
	}

	
	public static String encode(final String content, final String password) {
		String result = null;
		byte[] encryptResult = encrypt(content, password);

		if (null != encryptResult) {
			result = byte2Hex(encryptResult);
		}
		return result;
	}

	public static String decode(final String encryptResult,
			final String password) {
		String result = null;

		byte[] decryptFrom = hex2Byte(encryptResult);
		byte[] decryptResult = decrypt(decryptFrom, password);
		if (null != decryptResult) {
			try {
				result = new String(decryptResult, charsetName);
			} catch (UnsupportedEncodingException e) {
				log.error("decode failed",e);
			}
		}
		return result;

	}

	public static void main(String[] args) {
		String content = "AES加密字符串测试by-jiucai";
		String password = "123456789";
		// 加密
		System.out.println("加密前：" + content);
		
		String encodeStr = encode(content, password);
		System.out.println("加密后：" + encodeStr );

		// 解密
		System.out.println("解密后：" + decode(encodeStr, password));
	}

}
