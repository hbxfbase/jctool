package cn.sharpen.jctool.util;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import java.util.Base64;

public class RsaTool {

    //生成密钥对,第一个为公钥,第二个为私钥
    public static String[] genKeyPari(int len) {
        KeyPairGenerator keyPairGen = null;
        try {
            keyPairGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        keyPairGen.initialize(len); // 密钥位数
        KeyPair keyPair = keyPairGen.generateKeyPair();

        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        String[] keys = new String[2];
        keys[0] = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        keys[1] = Base64.getEncoder().encodeToString(privateKey.getEncoded());
//        System.out.println(keys[0]);
//        System.out.println(keys[1]);
        return keys;

    }


    // 使用公钥加密,输出base64编码
    public static String encrypt(String data, String publicKeyStr) throws Exception {
        PublicKey publicKey = getPublicKeyFromString(publicKeyStr);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] eBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(eBytes);
    }

    // 使用私钥解密
    public static String decrypt(String encryptedData, String privateKeyStr) throws Exception {
        PrivateKey privateKey = getPrivateKeyFromString(privateKeyStr);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    }

    //公钥字符串变公钥
    public static PublicKey getPublicKeyFromString(String publicKeyString) throws Exception {
        String publicKeyPEM = publicKeyString
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s+", "");

        byte[] decoded = Base64.getDecoder().decode(publicKeyPEM);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decoded);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    //私钥字符串变私钥
    public static PrivateKey getPrivateKeyFromString(String privateKeyString) throws Exception {
        String privateKeyPEM = privateKeyString
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s+", "");

        byte[] decoded = Base64.getDecoder().decode(privateKeyPEM);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    public static void main(String[] args) {

        String[] keys = genKeyPari(512);
        String pub = keys[0];
        String pri = keys[1];

        try {

            String encryptData = encrypt("123456789", pub);
            System.out.println("加密数据为:123456789");
            System.out.println("加密后:"+ encryptData);
            String decryptData = decrypt(encryptData, pri);
            System.out.println("解密后数据:"+decryptData);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
