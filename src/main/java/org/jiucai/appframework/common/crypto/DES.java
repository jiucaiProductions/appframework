package org.jiucai.appframework.common.crypto;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.jiucai.appframework.common.encrypt.AbstractEncryptor;

/**
 * DES 对称加密解码算法, JDK 实现的封装
 * <p>
 * DES加密算法出自IBM的研究，后来被美国政府正式采用，之后开始广泛流传，但是近些年使用越来越少，因为DES使用56位密钥，以现代计算能力，24
 * 小时内即可被破解。 <br/>
 * <br/>
 * 注意：DES加密和解密过程中，密钥长度都必须是8的倍数
 * </p>
 * 
 * @author jiucai
 * 
 */
public class DES extends AbstractEncryptor {

	protected static String algorithmKey = "DES";

	public static byte[] encrypt(byte[] datasource, String password) {
		try {
			SecureRandom random = new SecureRandom();
			DESKeySpec desKey = new DESKeySpec(password.getBytes(charsetName));
			// 创建一个密匙工厂，然后用它把DESKeySpec转换成
			SecretKeyFactory keyFactory = SecretKeyFactory
					.getInstance(algorithmKey);
			SecretKey securekey = keyFactory.generateSecret(desKey);
			// Cipher对象实际完成加密操作
			Cipher cipher = Cipher.getInstance(algorithmKey);
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
			// 现在，获取数据并加密
			// 正式执行加密操作
			return cipher.doFinal(datasource);
		} catch (Throwable e) {
			log.error("decrypt failed", e);
		}
		return null;
	}

	public static byte[] decrypt(byte[] src, String password) {
		try {
			// DES算法要求有一个可信任的随机数源
			SecureRandom random = new SecureRandom();
			// 创建一个DESKeySpec对象
			DESKeySpec desKey = new DESKeySpec(password.getBytes(charsetName));
			// 创建一个密匙工厂
			SecretKeyFactory keyFactory = SecretKeyFactory
					.getInstance(algorithmKey);
			// 将DESKeySpec对象转换成SecretKey对象
			SecretKey securekey = keyFactory.generateSecret(desKey);
			// Cipher对象实际完成解密操作
			Cipher cipher = Cipher.getInstance(algorithmKey);
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.DECRYPT_MODE, securekey, random);
			// 真正开始解密操作
			return cipher.doFinal(src);
		} catch (Throwable e) {
			log.error("decrypt failed", e);
		}
		return null;
	}

	public static String encode(final String content, final String password) {
		String result = null;
		byte[] encryptResult = encrypt(content.getBytes(), password);

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
				log.error("decode failed", e);
			}
		}
		return result;

	}

	public static void main(String[] args) {
		
		
		String content = "DES加密字符串测试by-jiucai";
		// 密码，长度要是8的倍数
		String password = "1234567890";
		// 加密
		System.out.println("加密前：" + content);
		
		String encodeStr = encode(content, password);
		System.out.println("加密后：" + encodeStr );

		// 解密
		System.out.println("解密后：" + decode(encodeStr, password));
	
	}

}
