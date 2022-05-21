package com.example.one.utils;


import cn.hutool.core.io.FileUtil;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;


import javax.crypto.Cipher;
import java.nio.charset.Charset;
import java.security.*;

public class RSAUtils {

    public static RSAPublicKey readPublicKey(File file) throws Exception {
        String key = new String(Files.readAllBytes(file.toPath()), Charset.defaultCharset());
        String publicKeyPEM = key
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END PUBLIC KEY-----", "");

        byte[] encoded = Base64.getDecoder().decode(publicKeyPEM);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }


    public static RSAPrivateKey readPrivateKey(File file) throws Exception {
        String key = new String(Files.readAllBytes(file.toPath()), Charset.defaultCharset());

        String privateKeyPEM = key
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END PRIVATE KEY-----", "");

        byte[] encoded = Base64.getDecoder().decode(privateKeyPEM);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
    }

    public static void main(String[] args) {

        byte[] encryptFile = FileUtil.readBytes("/Users/xuweiqiang/nginx/www/php_rsa/encrypt_file.txt");
        String content = null;
        try {
            content = new String(encryptFile,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        PrivateKey p = null;
        try {
            p = readPrivateKey("/Users/xuweiqiang/nginx/www/php_rsa/private_key.pem");
        } catch (IOException e) {
            e.printStackTrace();
        }

        String c = decryptRSA(p, content);
        System.out.println(c);

        if(true){
            return;
        }



        RSAPublicKey rsaPublicKey = null;
        File f = new File("/Users/xuweiqiang/nginx/www/php_rsa/public_key.pem");
        try {
            rsaPublicKey = readPublicKey(f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RSAPrivateKey rsaPrivateKey = null;
        File b = new File("/Users/xuweiqiang/nginx/www/php_rsa/private_key.pem");
        try {
            rsaPrivateKey = readPrivateKey(b);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // encrypt the message
        byte[] encrypted = new byte[0];
        try {
            encrypted = encrypt(rsaPrivateKey, "This is a secret message");
//            byte[] encryptFile = FileUtil.readBytes("/Users/xuweiqiang/nginx/www/php_rsa/encrypt_file.txt");
//            String base64encodedString = new String(encryptFile,"utf-8");
//            byte[] base64decodedBytes = Base64.getDecoder().decode(base64encodedString);
//            encrypted = base64decodedBytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        // decrypt the message
        try {
            byte[] secret = decrypt(rsaPublicKey, encrypted);
            System.out.println(new String(secret, UTF8));     // This is a secret message
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] encrypt(PrivateKey privateKey, String message) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(message.getBytes(UTF8));
    }

    public static byte[] decrypt(PublicKey publicKey, byte[] encrypted) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return cipher.doFinal(encrypted);
    }

    public static final String RSA_ALGORITHM = "RSA";
    public static final Charset UTF8 = Charset.forName("UTF-8");


    //https://blog.csdn.net/weixin_34124577/article/details/91403667

    // 返回 RSA 加密的结果
    public static String encryptRSA(Key publicKey, String text) {
        try {
            Cipher rsa = Cipher.getInstance("RSA/NONE/PKCS1Padding");
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
                    Charset.forName("UTF-8"));
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
            Cipher rsa = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            rsa.init(Cipher.DECRYPT_MODE, privateKey);
            //大于128时进行分段 解密
            int subLength = text.length / 128;
            StringBuilder finalString = new StringBuilder();
            for (int i = 0; i < subLength; i++) {
                byte[] doFinal = rsa.doFinal(text, i * 128, 128);
                finalString.append(new String(doFinal, Charset.forName("UTF-8")));
            }
            return finalString.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    //根据文件路径返回公匙
    public static PublicKey readPublicKey(String filePath) throws IOException {
        FileInputStream fis = new FileInputStream(new File(filePath));
        return readPublicKey(fis);
    }

    // 根据输入流返回公匙
    public static PublicKey readPublicKey(InputStream input) throws IOException {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        int n = 0;
        final byte[] buffer = new byte[1024 * 4];
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        byte[] keyBytes = output.toByteArray();
        // Base64Utlis.decode(keyBytes, Base64Utlis.NO_WRAP)
        X509EncodedKeySpec spec =
                new X509EncodedKeySpec(Base64.getDecoder().decode(keyBytes));
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
        FileInputStream fis = new FileInputStream(new File(filePath));
        return readPrivateKey(fis);
    }

    // 根据输入流返回私匙
    public static PrivateKey readPrivateKey(InputStream input) throws IOException {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        int n = 0;
        final byte[] buffer = new byte[1024 * 4];
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
        byte[] keyBytes = output.toByteArray();

        String ss = new String(keyBytes);
        String privateKeyPEM = ss
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END PRIVATE KEY-----", "");

        org.apache.commons.codec.binary.Base64 base64 = new org.apache.commons.codec.binary.Base64();
        PKCS8EncodedKeySpec spec =
                new PKCS8EncodedKeySpec(base64.decodeBase64(privateKeyPEM.getBytes(StandardCharsets.UTF_8)));
        try {
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(spec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }
}

