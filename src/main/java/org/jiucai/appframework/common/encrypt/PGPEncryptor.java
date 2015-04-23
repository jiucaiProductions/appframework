package org.jiucai.appframework.common.encrypt;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Date;

import org.bouncycastle.bcpg.ArmoredOutputStream;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.PGPCompressedDataGenerator;
import org.bouncycastle.openpgp.PGPEncryptedData;
import org.bouncycastle.openpgp.PGPEncryptedDataGenerator;
import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPLiteralData;
import org.bouncycastle.openpgp.PGPLiteralDataGenerator;
import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.operator.PGPDataEncryptorBuilder;
import org.bouncycastle.openpgp.operator.bc.BcPGPDataEncryptorBuilder;
import org.bouncycastle.openpgp.operator.bc.BcPublicKeyKeyEncryptionMethodGenerator;

/**
 * PGPEncryptor
 *
 * @author zhaidangwei
 *
 */
public class PGPEncryptor extends AbstractPGPEncryptor {

    /**
     * encrypt String
     *
     * @param strToEncrtpt
     *            strToEncrtpt
     * @param pulicKeyFilePath
     *            pulicKeyFilePath
     * @return encrypt String
     * @throws Exception
     *             if cat not encrypt
     */
    public static String encrypt(String strToEncrtpt, String pulicKeyFilePath) throws Exception {
        byte[] encrypted = encryptByte(strToEncrtpt, pulicKeyFilePath);

        return new String(encrypted);
    }

    public static String encrypt(String strToEncrtpt, String pulicKeyFilePath, String outFile)
            throws Exception {

        byte[] encrypted = encryptByte(strToEncrtpt, pulicKeyFilePath);

        writeToFile(encrypted, outFile);

        return new String(encrypted);
    }

    public static byte[] encryptByte(String strToEncrtpt, String pulicKeyFilePath) throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        byte[] original = strToEncrtpt.getBytes();
        byte[] encrypted = encrypt(original, readPublicKey(pulicKeyFilePath), null, true, true);

        return encrypted;
    }

    /**
     * Simple PGP encryptor between byte[].
     *
     * @param clearData
     *            The test to be encrypted
     * @param passPhrase
     *            The pass phrase (key). This method assumes that the key is a
     *            simple pass phrase, and does not yet support RSA or more
     *            sophisiticated keying.
     * @param fileName
     *            File name. This is used in the Literal Data Packet (tag 11)
     *            which is really inly important if the data is to be related to
     *            a file to be recovered later. Because this routine does not
     *            know the source of the information, the caller can set
     *            something here for file name use that will be carried. If this
     *            routine is being used to encrypt SOAP MIME bodies, for
     *            example, use the file name from the MIME type, if applicable.
     *            Or anything else appropriate.
     *
     * @param armor
     *
     * @return encrypted data.
     * @exception IOException
     * @exception PGPException
     * @exception NoSuchProviderException
     */
    private static byte[] encrypt(byte[] clearData, PGPPublicKey encKey, String fileName,
            boolean withIntegrityCheck, boolean armor) throws IOException, PGPException,
            NoSuchProviderException {
        if (fileName == null) {
            fileName = PGPLiteralData.CONSOLE;
        }

        ByteArrayOutputStream encOut = new ByteArrayOutputStream();

        OutputStream out = encOut;
        if (armor) {
            out = new ArmoredOutputStream(out);
        }

        ByteArrayOutputStream bOut = new ByteArrayOutputStream();

        PGPCompressedDataGenerator comData = new PGPCompressedDataGenerator(
                PGPCompressedDataGenerator.ZIP);
        OutputStream cos = comData.open(bOut); // open it with the final
        // destination
        PGPLiteralDataGenerator lData = new PGPLiteralDataGenerator();

        // we want to generate compressed data. This might be a user option
        // later,
        // in which case we would pass in bOut.
        OutputStream pOut = lData.open(cos, // the compressed output stream
                PGPLiteralData.BINARY, fileName, // "filename" to store
                clearData.length, // length of clear data
                new Date() // current time
                );
        pOut.write(clearData);
        pOut.flush();
        pOut.close();
        cos.close();

        lData.close();
        comData.close();

        PGPDataEncryptorBuilder encryptorBuilder = new BcPGPDataEncryptorBuilder(
                PGPEncryptedData.CAST5);
        PGPEncryptedDataGenerator cPk = new PGPEncryptedDataGenerator(encryptorBuilder);
        BcPublicKeyKeyEncryptionMethodGenerator methodGenerator = new BcPublicKeyKeyEncryptionMethodGenerator(
                encKey);
        methodGenerator.setSecureRandom(new SecureRandom());
        cPk.addMethod(methodGenerator);

        byte[] bytes = bOut.toByteArray();

        OutputStream cOut = cPk.open(out, bytes.length);
        cOut.write(bytes);
        cOut.flush();
        cOut.close();

        out.close();

        return encOut.toByteArray();
    }

    protected static void writeToFile(byte[] byteData, String fileName) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        fos.write(byteData);
        fos.flush();
        fos.close();
    }

}
