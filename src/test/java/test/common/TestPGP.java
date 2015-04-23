package test.common;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.jiucai.appframework.common.encrypt.PGPDecryptor;
import org.jiucai.appframework.common.encrypt.PGPEncryptor;

public class TestPGP {

    public static void main(String[] args) throws Exception {

        String publicKeyFile = "E:/doc/gpg/jiucai-public.asc";
        String privateKeyFile = "E:/doc/gpg/jiucai-private.asc";
        String passPhrase = "";

        String data = FileUtils.readFileToString(new File("E:/logs/web.log"), "UTF-8");

        String targetFile = "E:/logs/web.log.pgp";

        long start = System.currentTimeMillis();

        String encrypted = PGPEncryptor.encrypt(data, publicKeyFile, targetFile);
        // System.out.println("encrypted data = '" + new String(encrypted) +
        // "'");
        System.out.println("decrypt length: " + encrypted.length());
        System.out.println("encrypt time in ms: " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        String decrypted = PGPDecryptor.decryptFile(passPhrase, privateKeyFile, targetFile);

        // System.out.println("\ndecrypted data = '" + decrypted + "'");
        System.out.println("\ndecrypt length: " + decrypted.length());
        System.out.println("decrypt time in ms: " + (System.currentTimeMillis() - start));

    }
}
