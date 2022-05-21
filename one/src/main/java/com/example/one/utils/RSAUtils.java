package com.example.one.utils;


import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;


import javax.crypto.Cipher;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

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


        // generate public and private keys
//        KeyPair keyPair = buildKeyPair();
//        KeyPair keyPair;

//        PublicKey publicKey = keyPair.getPublic();
//        PrivateKey privateKey = keyPair.getPrivate();

//        // encrypt the message
        byte [] encrypted = new byte[0];
        try {
            encrypted = encrypt(rsaPrivateKey, "This is a secret message");
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println(base64Encode(encrypted));  // <<encrypted message>>
//
//        // decrypt the message
        try {
            byte[] secret = decrypt(rsaPublicKey, encrypted);
            System.out.println(new String(secret, UTF8));     // This is a secret message
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println(new String(secret, UTF8));     // This is a secret message
//
//
//
//        public static final String RSA_ALGORITHM = "RSA";
//        public static final Charset UTF8 = Charset.forName("UTF-8");
//
////        public static void main(String [] args) throws Exception {
////
////        }
//
//        public static KeyPair buildKeyPair() throws NoSuchAlgorithmException {
//            final int keySize = 2048;
//            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM);
//            keyPairGenerator.initialize(keySize);
//            return keyPairGenerator.genKeyPair();
//        }
//
//        public static byte[] encrypt(PrivateKey privateKey, String message) throws Exception {
//            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
//            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
//
//            return cipher.doFinal(message.getBytes(UTF8));
//        }
//
//        public static byte[] decrypt(PublicKey publicKey, byte [] encrypted) throws Exception {
//            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
//            cipher.init(Cipher.DECRYPT_MODE, publicKey);
//
//            return cipher.doFinal(encrypted);
//        }
//
//        public static String base64Encode(byte[] data) {
//            return new BASE64Encoder().encode(data);
//        }
//        public static byte[] base64Decode(String data) throws IOException {
//            return new BASE64Decoder().decodeBuffer(data);
//        }
    }

    public static byte[] encrypt(PrivateKey privateKey, String message) throws Exception {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            return cipher.doFinal(message.getBytes(UTF8));
        }

            public static byte[] decrypt(PublicKey publicKey, byte [] encrypted) throws Exception {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);

            return cipher.doFinal(encrypted);
        }

    public static final String RSA_ALGORITHM = "RSA";
    public static final Charset UTF8 = Charset.forName("UTF-8");
}

