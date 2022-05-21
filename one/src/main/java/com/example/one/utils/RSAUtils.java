package com.example.one.utils;

import cn.hutool.core.io.FileUtil;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.Cipher;
import java.security.*;

public class RSAUtils {

    private static final String encrypt_file = "/Users/xuweiqiang/nginx/www/php_rsa/encrypt_file.txt";
    private static final String private_key = "/Users/xuweiqiang/nginx/www/php_rsa/private_key.pem";
    private static final String public_key = "/Users/xuweiqiang/nginx/www/php_rsa/public_key.pem";
    private static final String transformationCode = "RSA/ECB/PKCS1Padding";

    public static void main(String[] args) {
        byte[] encryptFile = FileUtil.readBytes(encrypt_file);
        String content = new String(encryptFile, StandardCharsets.UTF_8);
        PrivateKey p = null;
        try {
            p = readPrivateKey(private_key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String c = decryptRSA(p, content);
        System.out.println(c);
        PublicKey pbk = null;
        try {
            pbk = readPublicKey(public_key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String d = encryptRSA(pbk, "hello world");
        System.out.println(d);

    }

    // 返回 RSA 加密的结果
    public static String encryptRSA(Key publicKey, String text) {
        try {
            Cipher rsa = Cipher.getInstance(transformationCode);
            rsa.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] originBytes = text.getBytes();
            //大于117时进行分段 加密
            int subLength = originBytes.length / 117 + (originBytes.length % 117 == 0 ? 0 : 1);
            byte[] finalByte = new byte[128 * subLength];
            for (int i = 0; i < subLength; i++) {
                //需要加密的字节长度
                int len = i == subLength - 1 ? (originBytes.length - i * 117) : 117;
                //加密完成的字节数组
                byte[] doFinal = rsa.doFinal(originBytes, i * 117, len);
                //复制这次加密的数组
                System.arraycopy(doFinal, 0, finalByte, i * 128, doFinal.length);
            }
            return new String(Base64.getEncoder().encode(finalByte),
                    StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 返回 RSA 解密的结果
     */
    public static String decryptRSA(Key privateKey, String content) {
        try {
            byte[] text = Base64.getDecoder().decode(content);
            Cipher rsa = Cipher.getInstance(transformationCode);
            rsa.init(Cipher.DECRYPT_MODE, privateKey);
            //大于128时进行分段 解密
            int subLength = text.length / 128;
            StringBuilder finalString = new StringBuilder();
            for (int i = 0; i < subLength; i++) {
                finalString.append(new String(rsa.doFinal(text, i * 128, 128), StandardCharsets.UTF_8));
            }
            return finalString.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    //根据文件路径返回公匙
    public static PublicKey readPublicKey(String filePath) throws IOException {
        return readPublicKey(new FileInputStream(filePath));
    }

    // 根据输入流返回公匙
    public static PublicKey readPublicKey(InputStream input) throws IOException {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        int n;
        final byte[] buffer = new byte[1024 * 4];
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        String publicPEM = output.toString()
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END PUBLIC KEY-----", "");
        X509EncodedKeySpec spec =
                new X509EncodedKeySpec(Base64.getDecoder().decode(publicPEM.getBytes(StandardCharsets.UTF_8)));
        try {
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePublic(spec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 根据文件路径返回私匙
    public static PrivateKey readPrivateKey(String filePath) throws IOException {
        return readPrivateKey(new FileInputStream(filePath));
    }

    // 根据输入流返回私匙
    public static PrivateKey readPrivateKey(InputStream input) throws IOException {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        int n;
        final byte[] buffer = new byte[1024 * 4];
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
        String privateKeyPEM = output.toString()
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END PRIVATE KEY-----", "");
        PKCS8EncodedKeySpec spec =
                new PKCS8EncodedKeySpec(org.apache.commons.codec.binary.Base64.decodeBase64(privateKeyPEM.getBytes(StandardCharsets.UTF_8)));
        try {
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(spec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }
}

