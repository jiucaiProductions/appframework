package org.jiucai.appframework.common.encrypt;

import org.jiucai.appframework.common.util.LogUtil;
import org.jiucai.appframework.common.util.Logs;

/**
 * 加密解密基类
 * 
 * @author jiucai
 *
 */
public abstract class AbstractEncryptor {

    /**
     * 将二进制转换成16进制
     * 
     * @param buf
     *            buf betes
     * @return String
     */
    public static String byte2Hex(byte[] buf) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = new StringBuffer("0").append(hex).toString();
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     * 
     * @param hexStr
     *            hexStr
     * @return byte[]
     */
    public static byte[] hex2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    protected static String charsetName = "UTF-8";

    protected static Logs log = LogUtil.getLog(AbstractEncryptor.class);

}
