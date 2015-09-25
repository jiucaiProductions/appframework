package org.jiucai.appframework.common.util;

import org.xxtea.XXTEA;

/**
 * WebSafeXxtea
 *
 * @author zhaidangwei
 *
 */
public final class WebSafeXxtea {

    public static String decrypt(final String encodeData, final String encKey) {
        String data = "";
        data = encodeData.replace("-", "+").replace("_", "/").replace("*", "=");
        data = XXTEA.decryptBase64StringToString(data, encKey);

        return data;
    }

    public static String encrypt(final String plainData, final String encKey) {
        String encodeData = "";
        encodeData = XXTEA.encryptToBase64String(plainData, encKey);
        encodeData = encodeData.replace("+", "-").replace("/", "_").replace("=", "*");

        return encodeData;
    }

}
