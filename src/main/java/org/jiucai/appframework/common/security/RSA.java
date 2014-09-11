package org.jiucai.appframework.common.security;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import org.jiucai.appframework.common.encode.Base64;

/**
 * RSA 非对称加密解码算法, JDK 实现的封装
 * 
 * @author jiucai
 * 
 */
public class RSA extends AsymmetricEncryptor {

	private static final String algorithmKey = "RSA";
	private static final String algorithmSignatureKey = "MD5withRSA";

	/**
	 * 用私钥对信息生成数字签名
	 * 
	 * @param data
	 *            加密数据
	 * @param privateKey
	 *            私钥
	 * @return 数据签名
	 */
	public static String sign(byte[] data, String privateKey) {
		try {
			// 解密由base64编码的私钥

			byte[] keyBytes = Base64.decode(privateKey);

			// 构造PKCS8EncodedKeySpec对象
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
			// algorithmKey 指定的加密算法
			KeyFactory keyFactory = KeyFactory.getInstance(algorithmKey);

			// 取私钥匙对象
			PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);

			// 用私钥对信息生成数字签名
			Signature signature = Signature.getInstance(algorithmSignatureKey);
			signature.initSign(priKey);
			signature.update(data);

			return Base64.encode(signature.sign());

		} catch (Throwable e) {
			log.error("sign failed", e);
		}
		return null;
	}

	/**
	 * 校验数字签名
	 * 
	 * @param data
	 *            加密数据
	 * @param publicKey
	 *            公钥
	 * @param sign
	 *            待校验的数字签名
	 * @return 校验成功返回 true 失败返回 false
	 */
	public static boolean verify(byte[] data, String publicKey, String sign) {
		try {
			log.info("using algorithmKey: " + algorithmKey
					+ " , algorithmSignatureKey: " + algorithmSignatureKey);
			// 解密由base64编码的公钥
			byte[] keyBytes = Base64.decode(publicKey);

			if (null == keyBytes) {
				log.error("invalid Base64 publicKey.");
				return false;
			}

			// 构造X509EncodedKeySpec对象
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

			// algorithmKey 指定的加密算法
			KeyFactory keyFactory = KeyFactory.getInstance(algorithmKey);

			// 取公钥匙对象
			PublicKey pubKey = keyFactory.generatePublic(keySpec);

			Signature signature = Signature.getInstance(algorithmSignatureKey);
			signature.initVerify(pubKey);
			signature.update(data);

			byte[] signVal = Base64.decode(sign);

			if (null != signVal) {
				// 验证签名是否正常
				return signature.verify(signVal);
			} else {
				log.error("invalid Base64 sign.");
				return false;
			}

		} catch (Throwable e) {
			log.error("verify failed", e);
		}
		return false;
	}

	/**
	 * 生成密钥
	 * 
	 * @param seed
	 *            种子
	 * @return 密钥对象
	 */
	public static Map<String, Object> initKey(String seed) {
		KeyPairGenerator keygen = null;
		try {
			keygen = KeyPairGenerator.getInstance(algorithmKey);
		} catch (NoSuchAlgorithmException e) {
			log.error("invalid algorithmKey" + algorithmKey, e);
			return null;
		}
		// 初始化随机产生器
		SecureRandom secureRandom = new SecureRandom();
		secureRandom.setSeed(seed.getBytes());
		keygen.initialize(1024, secureRandom);

		KeyPair keys = keygen.genKeyPair();

		PublicKey publicKey = keys.getPublic();
		PrivateKey privateKey = keys.getPrivate();

		Map<String, Object> map = new HashMap<String, Object>(2);
		map.put(PUBLIC_KEY, publicKey);
		map.put(PRIVATE_KEY, privateKey);

		return map;
	}

	public static void main(String[] args) throws Exception {
		String inputStr = "RSA加密字符串测试by-jiucai";
		byte[] data = inputStr.getBytes();

		String algorithmSeed = DSA.class.getName() + ".rsa.by.jiucai.20140705";
		// 构建密钥
		Map<String, Object> keyMap = initKey(algorithmSeed);

		// 获得密钥
		String publicKey = getPublicKey(keyMap);
		String privateKey = getPrivateKey(keyMap);

		System.out.println("公钥:\r" + publicKey);
		System.out.println("私钥:\r" + privateKey);

		// 产生签名
		String sign = sign(data, privateKey);
		System.out.println("签名:\r" + sign);

		// 验证签名
		boolean status = verify(data, publicKey, sign);
		System.out.println("状态:\r" + status);
	}

}
