package org.jiucai.appframework.common.crypto;

import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang.StringUtils;
import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AES 对称加密解码算法
 *
 * <pre>
 * AES加密算法/ECB 模式/NoPadding 填充
 * NOTE: jdk jce must enabled
 * cp local_policy.jar,US_export_policy.jar $JAVA_HOME/jre/lib/security/
 * 
 * without jce:
 * java.security.InvalidKeyException: Illegal key size or default parameters
 * </pre>
 *
 * @author zhaidangwei
 *
 */
public class AES {

    private static Logger logger = LoggerFactory.getLogger(AES.class);

    /**
     * 加密算法使用 AES
     */
    public static final String ALGORITHM = "AES";
    /**
     * 加密/解密算法/工作模式/填充方式
     *
     * JAVA6 支持PKCS5PADDING填充方式, Bouncy castle支持PKCS7Padding填充方式
     * */
    public static final String CIPHER_ALGORITHM = "AES/ECB/NoPadding";

    public static final String CHARSET = "UTF-8";

    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    public static byte[] decrypt(byte[] hexContentBytes, byte[] passwdBytes) {
        byte[] result = null;
        try {
            if (null == hexContentBytes || null == passwdBytes) {
                return result;
            }

            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            SecretKeySpec key = new SecretKeySpec(passwdBytes, ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);

            result = cipher.doFinal(hexContentBytes);

        } catch (Exception e) {
            logger.error("aes decrypt failed", e);
        }
        return result;
    }

    /**
     * AES解密
     *
     * @param content
     *            十六进制字符串
     * @param passwd
     *            密钥
     * @return 解密后的字符串
     */
    public static String decrypt(String content, String passwd) {
        String resultStr = "";
        try {
            if (StringUtils.isBlank(content) || StringUtils.isBlank(passwd)) {
                return resultStr;
            }

            byte[] data = Hex.decode(content);
            byte[] result = decrypt(data, passwd.getBytes(CHARSET));

            // 去除转成字符串后的不可见字符
            resultStr = new String(result, CHARSET).trim();

        } catch (Exception e) {
            logger.error("aes decrypt failed", e);
        }
        return resultStr;
    }

    public static byte[] encrypt(byte[] hexContentBytes, byte[] passwdBytes) {
        byte[] result = null;
        try {

            if (null == hexContentBytes || null == passwdBytes) {
                return result;
            }

            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);

            SecretKeySpec key = new SecretKeySpec(passwdBytes, ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);

            // AES要求数据源和密钥长度都必须是16的整数倍
            int blockSize = cipher.getBlockSize();

            // System.out.println("blockSize: " + blockSize);

            int orgLen = hexContentBytes.length;

            int requireLength = orgLen + blockSize - (orgLen % blockSize);

            // 加密的数组长度必须是16的整数倍，新长度中多余的长度默认是空字节，和C的openssl调用保持一致
            byte[] content = new byte[requireLength];
            System.arraycopy(hexContentBytes, 0, content, 0, orgLen);

            result = cipher.doFinal(content);

        } catch (Exception e) {
            logger.error("aes encrypt failed", e);
        }
        return result;
    }

    /**
     * AES加密
     *
     * @param content
     *            明文
     * @param passwd
     *            密钥
     * @return 解密后的十六进制字符串
     */
    public static String encrypt(String content, String passwd) {
        String resultStr = "";
        try {

            if (StringUtils.isBlank(content) || StringUtils.isBlank(passwd)) {
                return resultStr;
            }

            byte[] result = encrypt(content.getBytes(CHARSET), passwd.getBytes(CHARSET));

            resultStr = Hex.toHexString(result);

        } catch (Exception e) {
            logger.error("aes encrypt failed", e);
        }
        return resultStr;
    }

    public static void main(String[] args) throws Exception {

        // demo data
        String data = "851_1431483552157";
        String encodeData = "d117a2c889b728e123e7ac2bb933a29851a0a53d3f8ff70ed604d26538a8585a";
        String key = "c9539dacbe89eec252afa703f28ef06a";

        String r1 = encrypt(data, key);

        logger.info("java encrypt r1: [{}] {} {}", r1, r1.equals(encodeData) ? "=" : "<>",
                encodeData);

        String r2 = decrypt(encodeData, key);
        logger.info("java decrypt r2: [{}] {} {}", r2, r2.equals(data) ? "=" : "<>", data);

        logger.info("price in cent: {}", data.split("_", -1)[0]);

        data = "AES可以使用128、192、和256位密钥";

        r1 = encrypt(data, key);
        logger.info("java encrypt [{}][len={}] : [{}]", data, data.getBytes(CHARSET).length, r1);

        r2 = decrypt(r1, key);

        logger.info("java encrypt [{}] {} [{}]", r2, r2.equals(data) ? "=" : "<>", data);

    }

}
