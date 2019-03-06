package com.hyjf.cs.user.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Random;

/**
 * DES加密
 */
public class DES {

    private static final Logger logger = LoggerFactory.getLogger(DES.class);
    private static byte[] iv = { 1, 2, 3, 4, 5, 6, 7, 8 };

    // 加密模式
    public static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";

    public static final String ALGORITHM_DES_ECB = "DES";

    public static final String ENCODING = "UTF-8";

    // 加密方式
    private final static String DES_VALUE = "DES";

    /**
     * 以CBC PKCS5Padding方式加密
     *
     * @param message
     * @param key
     * @return
     * @throws Exception
     */
    public static String encrypt(String message, String key) {
        if (message == null || "".equals(message)) {
            return "";
        }
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            DESKeySpec desKeySpec = new DESKeySpec(key.getBytes(ENCODING));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES_VALUE);
            SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
            IvParameterSpec iv = new IvParameterSpec(key.getBytes(ENCODING));
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
            return BASE64.encode(cipher.doFinal(message.getBytes(ENCODING)));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    /**
     * 8位DES方式加密
     *
     * @param encryptString
     * @param encryptKey
     * @return
     * @throws Exception
     */
    public static String encryptDES(String encryptString, String encryptKey) {
        try {
            IvParameterSpec zeroIv = new IvParameterSpec(iv);
            SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), DES_VALUE);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
            byte[] encryptedData = cipher.doFinal(encryptString.getBytes());
            return BASE64.encode(encryptedData);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        String value = encryptDES("hyjg-app-platform-001", "9KLH29Do");
        StringBuffer sb = new StringBuffer(8);
        Random random = new Random();
        int sub;
        for (int i = 0; i < 8; i++) {
            sub = random.nextInt(32);
            sb.append(value.charAt(sub));
        }
        System.err.println(sb.toString());
    }

    /**
     * DES-ECB 模式加密
     *
     * @param encryptString
     * @param encryptKey
     *            ,要求8位日期,如20150515
     * @return 加密后字段
     * @throws Exception
     */
    public static String encryptDES_ECB(String encryptString, String encryptKey) {
        try {
            SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), DES_VALUE);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES_ECB);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedData = cipher.doFinal(encryptString.getBytes(ENCODING));
            return BASE64.encode(encryptedData);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    /**
     * 解密
     *
     * @param key
     * @param data
     * @return
     * @throws Exception
     */
    public static String encode(String key, String data) {
        return encode(key, data.getBytes());
    }

    /**
     * DES-ECB算法，加密
     *
     * @param data
     *            待加密字符串
     * @param key
     *            加密私钥，长度不能够小于8位
     * @return 加密后的字节数组，一般结合Base64编码使用
     * @throws Exception
     *             异常
     */
    public static String encode(String key, byte[] data) {
        try {
            DESKeySpec dks = new DESKeySpec(key.getBytes());

            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES_VALUE);
            // key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES_ECB);
            IvParameterSpec iv = new IvParameterSpec(key.getBytes());
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
            byte[] bytes = cipher.doFinal(data);
            return BASE64.encode(bytes);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    /**
     * DES算法，解密
     *
     * @param data
     *            待解密字符串
     * @param key
     *            解密私钥，长度不能够小于8位
     * @return 解密后的字节数组
     * @throws Exception
     *             异常
     */
    private static byte[] decode(String key, byte[] data) {
        try {
            SecretKeySpec keyFactory = new SecretKeySpec(key.getBytes(), DES_VALUE);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES_ECB);
            cipher.init(Cipher.DECRYPT_MODE, keyFactory);
            return cipher.doFinal(data);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    /**
     * DES算法，解密
     *
     * @param data
     *            待解密字符串
     * @param key
     *            解密私钥，长度不能够小于8位
     * @return 解密后的字节数组
     * @throws Exception
     *             异常
     */
    public static String decodeValue(String key, String data) {
        byte[] datas;
        String value = null;
        try {
            datas = decode(key, BASE64.decode(data));
            value = new String(datas, "UTF-8");
        } catch (Exception e) {
            value = "";
        }
        return value;
    }

}
