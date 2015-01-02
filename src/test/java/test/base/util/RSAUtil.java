package test.base.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

import org.jiucai.appframework.common.encode.Base64;
import org.jiucai.appframework.common.util.LogUtil;
import org.jiucai.appframework.common.util.Logs;

/**
 * RSA 工具类。 提供加密，解密，生成密钥对等方法。 非对称加密， 2011年1月18日 16:45:28 漆艾林
 */
public class RSAUtil {
    /**
     * 解密
     *
     * @param key
     *            解密的密钥
     * @param raw
     *            已经加密的数据
     * @return 解密后的明文
     * @throws EncryptException
     */
    public static byte[] decrypt(Key key, byte[] raw) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance("RSA",
                    new org.bouncycastle.jce.provider.BouncyCastleProvider());
            cipher.init(Cipher.DECRYPT_MODE, key);
            int blockSize = cipher.getBlockSize();
            ByteArrayOutputStream bout = new ByteArrayOutputStream(64);
            int j = 0;
            while (raw.length - j * blockSize > 0) {
                bout.write(cipher.doFinal(raw, j * blockSize, blockSize));
                j++;
            }
            return bout.toByteArray();
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 加密
     *
     * @param key
     *            加密的密钥
     * @param data
     *            待加密的明文数据
     * @return 加密后的数据
     * @throws EncryptException
     */
    public static byte[] encrypt(Key key, byte[] data) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance("RSA",
                    new org.bouncycastle.jce.provider.BouncyCastleProvider());
            cipher.init(Cipher.ENCRYPT_MODE, key);
            int blockSize = cipher.getBlockSize();// 获得加密块大小，如：加密前数据为128个byte，而key_size=1024
                                                  // 加密块大小为127
                                                  // byte,加密后为128个byte;因此共有2个加密块，第一个127
                                                  // byte第二个为1个byte
            int outputSize = cipher.getOutputSize(data.length);// 获得加密块加密后块大小
            int leavedSize = data.length % blockSize;
            int blocksSize = leavedSize != 0 ? data.length / blockSize + 1 : data.length
                    / blockSize;
            byte[] raw = new byte[outputSize * blocksSize];
            int i = 0;
            while (data.length - i * blockSize > 0) {
                if (data.length - i * blockSize > blockSize) {
                    cipher.doFinal(data, i * blockSize, blockSize, raw, i * outputSize);
                } else {
                    cipher.doFinal(data, i * blockSize, data.length - i * blockSize, raw, i
                            * outputSize);
                }
                // 这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把byte[]放到ByteArrayOutputStream中，而最后doFinal的时候才将所有的byte[]进行加密，可是到了此时加密块大小很可能已经超出了OutputSize所以只好用dofinal方法。
                i++;
            }
            return raw;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 生成密钥对
     *
     * @return KeyPair
     * @throws EncryptException
     */
    public static KeyPair generateKeyPair() throws Exception {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA",
                    new org.bouncycastle.jce.provider.BouncyCastleProvider());
            final int KEY_SIZE = 1024;// 没什么好说的了，这个值关系到块加密的大小，可以更改，但是不要太大，否则效率会低
            keyPairGen.initialize(KEY_SIZE, new SecureRandom());
            KeyPair keyPair = keyPairGen.genKeyPair();
            return keyPair;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 生成私钥
     *
     * @param modulus
     * @param privateExponent
     * @return RSAPrivateKey
     * @throws EncryptException
     */
    public static RSAPrivateKey generateRSAPrivateKey(byte[] modulus, byte[] privateExponent)
            throws Exception {
        KeyFactory keyFac = null;
        try {
            keyFac = KeyFactory.getInstance("RSA",
                    new org.bouncycastle.jce.provider.BouncyCastleProvider());
        } catch (NoSuchAlgorithmException ex) {
            throw new Exception(ex.getMessage());
        }
        RSAPrivateKeySpec priKeySpec = new RSAPrivateKeySpec(new BigInteger(modulus),
                new BigInteger(privateExponent));
        try {
            return (RSAPrivateKey) keyFac.generatePrivate(priKeySpec);
        } catch (InvalidKeySpecException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    /**
     * 生成公钥
     *
     * @param modulus
     * @param publicEXPonent
     * @return RSAPublicKey
     * @throws EncryptException
     */
    public static RSAPublicKey generateRSAPublicKey(byte[] modulus, byte[] publicExponent)
            throws Exception {
        KeyFactory keyFac = null;
        keyFac = KeyFactory.getInstance("RSA",
                new org.bouncycastle.jce.provider.BouncyCastleProvider());
        RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(new BigInteger(modulus), new BigInteger(
                publicExponent));
        try {
            return (RSAPublicKey) keyFac.generatePublic(pubKeySpec);
        } catch (InvalidKeySpecException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    /**
     *
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        logger.info("started ....");
        String path = "e:";
        File file = new File(path + "/test.txt");
        FileInputStream in = new FileInputStream(file);
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        byte[] tmpbuf = new byte[1024];
        int count = 0;
        while ((count = in.read(tmpbuf)) != -1) {
            bout.write(tmpbuf, 0, count);
            tmpbuf = new byte[1024];
        }
        in.close();
        byte[] orgData = bout.toByteArray();
        KeyPair keyPair = RSAUtil.generateKeyPair();
        RSAPublicKey pubKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey priKey = (RSAPrivateKey) keyPair.getPrivate();

        logger.info("---publicKey:" + pubKey);
        logger.info("---privateKey:" + priKey);

        byte[] pubModBytes = pubKey.getModulus().toByteArray();
        byte[] pubPubExpBytes = pubKey.getPublicExponent().toByteArray();
        byte[] priModBytes = priKey.getModulus().toByteArray();
        byte[] priPriExpBytes = priKey.getPrivateExponent().toByteArray();
        RSAPublicKey recoveryPubKey = RSAUtil.generateRSAPublicKey(pubModBytes, pubPubExpBytes);
        RSAPrivateKey recoveryPriKey = RSAUtil.generateRSAPrivateKey(priModBytes, priPriExpBytes);

        logger.info("---recoveryPubKey:" + Base64.encode(recoveryPubKey.getEncoded()));
        logger.info("---recoveryPriKey:" + Base64.encode(recoveryPriKey.getEncoded()));

        byte[] raw = RSAUtil.encrypt(recoveryPriKey, orgData);
        file = new File(path + "/encryptresult.txt");
        OutputStream out = new FileOutputStream(file);
        out.write(raw);
        out.close();

        byte[] data = RSAUtil.decrypt(recoveryPubKey, raw);
        file = new File(path + "/decryptresult.txt");
        out = new FileOutputStream(file);
        out.write(data);
        out.flush();
        out.close();

        logger.info("end ....");
    }

    protected static Logs logger = LogUtil.getLog(RSAUtil.class);
}
