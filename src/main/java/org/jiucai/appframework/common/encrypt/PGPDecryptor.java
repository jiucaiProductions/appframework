package org.jiucai.appframework.common.encrypt;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.Iterator;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.PGPCompressedData;
import org.bouncycastle.openpgp.PGPEncryptedDataList;
import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPLiteralData;
import org.bouncycastle.openpgp.PGPObjectFactory;
import org.bouncycastle.openpgp.PGPPrivateKey;
import org.bouncycastle.openpgp.PGPPublicKeyEncryptedData;
import org.bouncycastle.openpgp.PGPSecretKey;
import org.bouncycastle.openpgp.PGPSecretKeyRingCollection;
import org.bouncycastle.openpgp.PGPUtil;
import org.bouncycastle.openpgp.bc.BcPGPObjectFactory;
import org.bouncycastle.openpgp.bc.BcPGPSecretKeyRingCollection;
import org.bouncycastle.openpgp.operator.PBESecretKeyDecryptor;
import org.bouncycastle.openpgp.operator.bc.BcPBESecretKeyDecryptorBuilder;
import org.bouncycastle.openpgp.operator.bc.BcPGPDigestCalculatorProvider;
import org.bouncycastle.openpgp.operator.bc.BcPublicKeyDataDecryptorFactory;

/**
 * Simple routine to encrypt and decrypt using a Public and Private key with
 * passphrase. This service routine provides the basic PGP services between byte
 * arrays.
 *
 */
public class PGPDecryptor extends AbstractPGPEncryptor {

    public static String decrypt(String passphrase, String keyFile, String encryptedStr)
            throws Exception {

        byte[] decrypted = decryptByte(passphrase, keyFile, encryptedStr.getBytes());

        return new String(decrypted);
    }

    public static String decryptFile(String passphrase, String keyFile, String inputFile)
            throws Exception {

        byte[] encFromFile = getBytesFromFile(new File(inputFile));
        byte[] decrypted = decryptByte(passphrase, keyFile, encFromFile);

        return new String(decrypted);

    }

    private static PGPPrivateKey extractPrivateKey(PGPSecretKey pgpSecKey, char[] passPhrase)
            throws PGPException {
        PGPPrivateKey privateKey = null;
        BcPGPDigestCalculatorProvider calculatorProvider = new BcPGPDigestCalculatorProvider();
        BcPBESecretKeyDecryptorBuilder secretKeyDecryptorBuilder = new BcPBESecretKeyDecryptorBuilder(
                calculatorProvider);
        PBESecretKeyDecryptor pBESecretKeyDecryptor = secretKeyDecryptorBuilder.build(passPhrase);

        try {
            privateKey = pgpSecKey.extractPrivateKey(pBESecretKeyDecryptor);
        } catch (PGPException e) {
            throw new PGPException("invalid privateKey passPhrase: " + String.valueOf(passPhrase),
                    e);
        }

        return privateKey;
    }

    /**
     * decrypt the passed in message stream
     *
     * @param encrypted
     *            The message to be decrypted.
     * @param passPhrase
     *            Pass phrase (key)
     *
     * @return Clear text as a byte array. I18N considerations are not handled
     *         by this routine
     * @exception IOException
     * @exception PGPException
     * @exception NoSuchProviderException
     */
    @SuppressWarnings("unchecked")
    protected static byte[] decrypt(byte[] encrypted, InputStream keyIn, char[] password)
            throws IOException, PGPException, NoSuchProviderException {

        InputStream decodeIn = PGPUtil.getDecoderStream(new ByteArrayInputStream(encrypted));
        BcPGPObjectFactory pgpF = new BcPGPObjectFactory(decodeIn);
        decodeIn.close();

        PGPEncryptedDataList enc = null;
        Object o = pgpF.nextObject();

        //
        // the first object might be a PGP marker packet.
        //
        if (o instanceof PGPEncryptedDataList) {
            enc = (PGPEncryptedDataList) o;
        } else {
            enc = (PGPEncryptedDataList) pgpF.nextObject();
        }

        // find the secret key

        PGPPrivateKey sKey = null;

        Iterator<PGPPublicKeyEncryptedData> it = enc.getEncryptedDataObjects();
        PGPPublicKeyEncryptedData pbe = null;
        PGPSecretKeyRingCollection pgpSec = new BcPGPSecretKeyRingCollection(
                PGPUtil.getDecoderStream(keyIn));

        while (sKey == null && it.hasNext()) {
            pbe = it.next();
            sKey = findSecretKey(pgpSec, pbe.getKeyID(), password);

        }

        // ////
        // PGPSecretKey secretKey = readSecretKey(keyIn);
        // sKey = getPGPSecretKey(secretKey, password);
        // ///

        if (pbe == null) {
            throw new IllegalArgumentException("PGPPublicKeyEncryptedData not found.");
        }

        if (sKey == null) {
            throw new IllegalArgumentException("secret key for message not found.");
        }

        BcPublicKeyDataDecryptorFactory pkdf = new BcPublicKeyDataDecryptorFactory(sKey);

        InputStream clear = pbe.getDataStream(pkdf);
        PGPObjectFactory pgpFact = new BcPGPObjectFactory(clear);

        PGPCompressedData cData = (PGPCompressedData) pgpFact.nextObject();

        pgpFact = new BcPGPObjectFactory(cData.getDataStream());

        PGPLiteralData ld = (PGPLiteralData) pgpFact.nextObject();

        InputStream unc = ld.getInputStream();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int ch;

        while ((ch = unc.read()) >= 0) {
            out.write(ch);

        }

        byte[] returnBytes = out.toByteArray();
        clear.close();
        out.close();
        unc.close();

        return returnBytes;

    }

    protected static byte[] decryptByte(String passphrase, String keyFile, byte[] encryptedBytes)
            throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        FileInputStream secKey = new FileInputStream(keyFile);
        byte[] decrypted = decrypt(encryptedBytes, secKey, passphrase.toCharArray());

        return decrypted;
    }

    protected static PGPPrivateKey findSecretKey(PGPSecretKeyRingCollection pgpSec, long keyID,
            char[] pass) throws PGPException, NoSuchProviderException {
        PGPPrivateKey privateKey = null;
        PGPSecretKey pgpSecKey = pgpSec.getSecretKey(keyID);

        if (pgpSecKey == null) {
            return null;
        }
        privateKey = extractPrivateKey(pgpSecKey, pass);

        return privateKey;
    }

}