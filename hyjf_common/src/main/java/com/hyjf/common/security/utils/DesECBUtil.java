package com.hyjf.common.security.utils;
 
import java.io.IOException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
 
@SuppressWarnings("restriction")
public class DesECBUtil {
 
    private final static String DES = "DES";
     
    /**
     * Description 根据键值进行加密
     * @param data 
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    public static String encrypt(String data, String key){
        byte[] bt = null;
        String strs = null;
        try{
            bt = encrypt(data.getBytes(), key.getBytes());
            strs = new BASE64Encoder().encode(bt); 
        }catch(Exception e){
            e.printStackTrace();
        }
        return strs;
    }
 
    /**
     * Description 根据键值进行解密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static String decrypt(String data, String key){
        if (data == null)
            return null;
        BASE64Decoder decoder;
        byte[] buf = null;
        byte[] bt = null;
        try {
            decoder = new BASE64Decoder();
            buf = decoder.decodeBuffer(data);
            bt = decrypt(buf,key.getBytes());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new String(bt);
    }
 
    /**
     * Description 根据键值进行加密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
 
        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
 
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
 
        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(DES);
 
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
 
        return cipher.doFinal(data);
    }
     
     
    /**
     * Description 根据键值进行解密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
 
        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
 
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
 
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(DES);
 
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
 
        return cipher.doFinal(data);
    }
    
    public static void main(String[] args) throws Exception {
        /*String data = "{\"userId\":\"\",\"wkcd_id\":\"dc18d020-0614-45d8-a94a-4fb293c71934\",\"truename\":\"王成\",\"mobile\":\"15659926163\",\"borrow_amount\":\"8000.00\",\"car_no\":\"沪PPPPPP\",\"car_type\":\"2016款 宝马1系 118i 都市设计套装\",\"car_shop\":\"多平台测试门店嘉兴\",\"wkcd_status\":\"合同附件终审通过\",\"wkcd_repay_type\":\"FirstInterestLastCapital\",\"wkcd_borrow_period\":\"3\",\"wkcd_rate\":\"0.3\"}";
        String key = "hw78r2u3";
        System.out.println(encrypt(data, key));*/
        System.out.println(decrypt("oIU8CSQik93ZcJRW1OEY/d7WTBEPZs35EU2U/TBoUpR9vBmWdsnHY2smBtS/xWfoktn/k/8dXtY=", "w9lqgeu3"));
    }
}
