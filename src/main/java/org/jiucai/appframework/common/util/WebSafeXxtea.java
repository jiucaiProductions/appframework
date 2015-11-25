package org.jiucai.appframework.common.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xxtea.XXTEA;

/**
 * WebSafeXxtea
 *
 * @author zhaidangwei
 *
 */
public final class WebSafeXxtea {
    private static Logger logger = LoggerFactory.getLogger(WebSafeXxtea.class);

    public static String decrypt(final String encodeData, final String encKey) {
        String data = "";

        if (StringUtils.isBlank(encodeData)) {
            return data;
        }
        try {
            data = encodeData.replace("-", "+").replace("_", "/").replace("*", "=");
            data = XXTEA.decryptBase64StringToString(data, encKey);
        } catch (Throwable e) {
            logger.error("xxtea decrypt failed, {}", ExceptionUtils.getRootCauseMessage(e));
        }

        return data;
    }

    public static String encrypt(final String plainData, final String encKey) {
        String encodeData = "";
        if (StringUtils.isBlank(plainData)) {
            return encodeData;
        }
        try {
            encodeData = XXTEA.encryptToBase64String(plainData, encKey);
            encodeData = encodeData.replace("+", "-").replace("/", "_").replace("=", "*");

        } catch (Throwable e) {
            logger.error("xxtea encrypt failed, {}", ExceptionUtils.getRootCauseMessage(e));
        }

        return encodeData;
    }

}
