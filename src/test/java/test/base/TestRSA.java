package test.base;

import java.math.BigInteger;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.encodings.PKCS1Encoding;
import org.bouncycastle.crypto.engines.RSAEngine;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;
import org.bouncycastle.util.encoders.Hex;
import org.jiucai.appframework.common.security.AsymmetricEncryptor;
import org.jiucai.appframework.common.security.RSA;

public class TestRSA {

    final public static int RAW = 1;

    final public static int PKCS1 = 2;

    public static void main(String[] args) throws Throwable {

        Map<String, Object> keyMap = RSA.initKey("test123");
        String publicKey = AsymmetricEncryptor.getPublicKey(keyMap);
        String privateKey = AsymmetricEncryptor.getPrivateKey(keyMap);

        System.out.println("publicKey:" + publicKey);
        System.out.println("privateKey:" + privateKey);

    }

    /*
     * 私钥解密
     */
    public static byte[] rsaPriDecrypt(RSAPrivateKey prvKeyIn, byte[] encodedBytes, int type) {
        RSAPrivateCrtKey prvKey = (RSAPrivateCrtKey) prvKeyIn;
        BigInteger mod = prvKey.getModulus();
        BigInteger pubExp = prvKey.getPublicExponent();
        BigInteger privExp = prvKey.getPrivateExponent();
        BigInteger pExp = prvKey.getPrimeExponentP();
        BigInteger qExp = prvKey.getPrimeExponentQ();
        BigInteger p = prvKey.getPrimeP();
        BigInteger q = prvKey.getPrimeQ();
        BigInteger crtCoef = prvKey.getCrtCoefficient();
        RSAKeyParameters privParameters = new RSAPrivateCrtKeyParameters(mod, pubExp, privExp, p,
                q, pExp, qExp, crtCoef);
        AsymmetricBlockCipher eng = new RSAEngine();
        if (type == PKCS1) {
            eng = new PKCS1Encoding(eng);
        }
        eng.init(false, privParameters);
        byte[] data = null;
        try {
            data = eng.processBlock(encodedBytes, 0, encodedBytes.length);
            return data;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    /*
     * 使用私钥加密
     */
    public static byte[] rsaPriEncrypt(RSAPrivateKey prvKeyIn, byte[] encodedBytes, int type) {
        RSAPrivateCrtKey prvKey = (RSAPrivateCrtKey) prvKeyIn;
        BigInteger mod = prvKey.getModulus();
        BigInteger pubExp = prvKey.getPublicExponent();
        BigInteger privExp = prvKey.getPrivateExponent();
        BigInteger pExp = prvKey.getPrimeExponentP();
        BigInteger qExp = prvKey.getPrimeExponentQ();
        BigInteger p = prvKey.getPrimeP();
        BigInteger q = prvKey.getPrimeQ();
        BigInteger crtCoef = prvKey.getCrtCoefficient();
        RSAKeyParameters privParameters = new RSAPrivateCrtKeyParameters(mod, pubExp, privExp, p,
                q, pExp, qExp, crtCoef);
        AsymmetricBlockCipher eng = new RSAEngine();
        if (type == PKCS1) {
            eng = new PKCS1Encoding(eng);
        }
        eng.init(true, privParameters);
        byte[] data = null;
        try {
            data = eng.processBlock(encodedBytes, 0, encodedBytes.length);
            return data;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    /*
     * 公钥解密
     */
    public static byte[] rsaPubDecrypt(RSAPublicKey PubKey, byte[] clearBytes, int type) {
        BigInteger mod = PubKey.getModulus();
        BigInteger pubExp = PubKey.getPublicExponent();
        RSAKeyParameters pubParameters = new RSAKeyParameters(false, mod, pubExp);
        System.out.println("mod:\r\n" + mod.toString(16));
        System.out.println("pubExp:\r\n" + pubExp.toString(16));
        AsymmetricBlockCipher eng = new RSAEngine();
        if (type == PKCS1) {
            eng = new PKCS1Encoding(eng);
        }
        eng.init(false, pubParameters);
        byte[] data = null;
        try {
            System.out.println("clearBytes:\r\n" + new String(Hex.encode(clearBytes)));
            data = eng.processBlock(clearBytes, 0, clearBytes.length);
            System.out.println("EncBytes:\r\n" + new String(Hex.encode(data)));
            return data;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    /*
     * 使用公钥加密
     */
    public static byte[] rsaPubEncrypt(RSAPublicKey PubKey, byte[] clearBytes, int type) {
        BigInteger mod = PubKey.getModulus();
        BigInteger pubExp = PubKey.getPublicExponent();
        RSAKeyParameters pubParameters = new RSAKeyParameters(false, mod, pubExp);
        System.out.println("mod:\r\n" + mod.toString(16));
        System.out.println("pubExp:\r\n" + pubExp.toString(16));
        AsymmetricBlockCipher eng = new RSAEngine();
        if (type == PKCS1) {
            eng = new PKCS1Encoding(eng);
        }
        eng.init(true, pubParameters);
        byte[] data = null;
        try {
            System.out.println("clearBytes:\r\n" + new String(Hex.encode(clearBytes)));
            data = eng.processBlock(clearBytes, 0, clearBytes.length);
            System.out.println("EncBytes:\r\n" + new String(Hex.encode(data)));
            return data;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
