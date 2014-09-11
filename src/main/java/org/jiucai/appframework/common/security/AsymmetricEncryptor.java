package org.jiucai.appframework.common.security;

import java.security.Key;
import java.util.Map;

import org.jiucai.appframework.common.encode.Base64;
import org.jiucai.appframework.common.encrypt.AbstractEncryptor;

/**
 * 非对称加密解密基类
 * @author jiucai
 *
 */
public abstract class AsymmetricEncryptor extends AbstractEncryptor {

	protected static final String PUBLIC_KEY 	= "AsymmetricEncryptorPublicKey";
	protected static final String PRIVATE_KEY = "AsymmetricEncryptorPrivateKey";

	/**
	 * 取得私钥
	 * 
	 * @param keyMap
	 * @return privateKey String
	 * @throws Exception
	 */
	public static String getPrivateKey(Map<String, Object> keyMap)
			throws Exception {
		Key key = (Key) keyMap.get(PRIVATE_KEY);

		return Base64.encode(key.getEncoded());
	}

	/**
	 * 取得公钥
	 * 
	 * @param keyMap
	 * @return publicKey String
	 * @throws Exception
	 */
	public static String getPublicKey(Map<String, Object> keyMap)
			throws Exception {
		Key key = (Key) keyMap.get(PUBLIC_KEY);

		return Base64.encode(key.getEncoded());
	}
	
}
