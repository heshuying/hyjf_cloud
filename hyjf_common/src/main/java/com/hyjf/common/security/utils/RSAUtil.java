package com.hyjf.common.security.utils;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

/**
 * RSA 工具类。提供加密，解密，生成密钥对等方法。
 * 需要到http://www.bouncycastle.org下载bcprov-jdk14-123.jar。
 * RSA加密原理概述  
 * RSA的安全性依赖于大数的分解，公钥和私钥都是两个大素数（大于100的十进制位）的函数。  
 * 据猜测，从一个密钥和密文推断出明文的难度等同于分解两个大素数的积  
 * ===================================================================  
 * （该算法的安全性未得到理论的证明）  
 * ===================================================================  
 * 密钥的产生：  
 * 1.选择两个大素数 p,q ,计算 n=p*q;  
 * 2.随机选择加密密钥 e ,要求 e 和 (p-1)*(q-1)互质  
 * 3.利用 Euclid 算法计算解密密钥 d , 使其满足 e*d = 1(mod(p-1)*(q-1)) (其中 n,d 也要互质)  
 * 4:至此得出公钥为 (n,e) 私钥为 (n,d)  
 * ===================================================================  
 * 加解密方法：  
 * 1.首先将要加密的信息 m(二进制表示) 分成等长的数据块 m1,m2,...,mi 块长 s(尽可能大) ,其中 2^s<n  
 * 2:对应的密文是： ci = mi^e(mod n)  
 * 3:解密时作如下计算： mi = ci^d(mod n)  
 * ===================================================================  
 * RSA速度  
 * 由于进行的都是大数计算，使得RSA最快的情况也比DES慢上100倍，无论 是软件还是硬件实现。  
 * 速度一直是RSA的缺陷。一般来说只用于少量数据 加密。 
 *文件名：RSAUtil.java<br>
 *@author 董利伟<br>
 *版本:<br>
 *描述：<br>
 *创建时间:2008-9-23 下午09:58:16<br>
 *文件描述：<br>
 *修改者：<br>
 *修改日期：<br>
 *修改描述：<br>
 */
public class RSAUtil {

    //密钥对
    private KeyPair keyPair = null;
    
    /**
     * 初始化密钥对
     */
    public RSAUtil(){
        try {
            this.keyPair = this.generateKeyPair();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
    * 生成密钥对
    * @return KeyPair
    * @throws Exception
    */
    private KeyPair generateKeyPair() throws Exception {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA",new org.bouncycastle.jce.provider.BouncyCastleProvider());
            //这个值关系到块加密的大小，可以更改，但是不要太大，否则效率会低
            final int KEY_SIZE = 1024;
            keyPairGen.initialize(KEY_SIZE, new SecureRandom());
            KeyPair keyPair = keyPairGen.genKeyPair();
            return keyPair;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    
    }

    /**
    * 生成公钥
    * @param modulus
    * @param publicExponent
    * @return RSAPublicKey
    * @throws Exception
    */
    private RSAPublicKey generateRSAPublicKey(byte[] modulus, byte[] publicExponent) throws Exception {
    
        KeyFactory keyFac = null;
        try {
            keyFac = KeyFactory.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
        } catch (NoSuchAlgorithmException ex) {
        throw new Exception(ex.getMessage());
        }
        RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(new BigInteger(modulus), new BigInteger(publicExponent));
        try {
            return (RSAPublicKey) keyFac.generatePublic(pubKeySpec);
        } catch (InvalidKeySpecException ex) {
            throw new Exception(ex.getMessage());
        }
    
    }

    /**
    * 生成私钥
    * @param modulus
    * @param privateExponent
    * @return RSAPrivateKey
    * @throws Exception
    */
    private RSAPrivateKey generateRSAPrivateKey(byte[] modulus, byte[] privateExponent) throws Exception {
        KeyFactory keyFac = null;
        try {
            keyFac = KeyFactory.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
        } catch (NoSuchAlgorithmException ex) {
            throw new Exception(ex.getMessage());
        }
        RSAPrivateKeySpec priKeySpec = new RSAPrivateKeySpec(new BigInteger(modulus), new BigInteger(privateExponent));
        try {
            return (RSAPrivateKey) keyFac.generatePrivate(priKeySpec);
        } catch (InvalidKeySpecException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    /**
    * 加密
    * @param key 加密的密钥
    * @param data 待加密的明文数据
    * @return 加密后的数据
    * @throws Exception
    */
    public byte[] encrypt(Key key, byte[] data) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
            cipher.init(Cipher.ENCRYPT_MODE, key);
            //获得加密块大小，如:加密前数据为128个byte，而key_size=1024 加密块大小为127 byte,加密后为128个byte;
            //因此共有2个加密块，第一个127 byte第二个为1个byte
            int blockSize = cipher.getBlockSize();
            int outputSize = cipher.getOutputSize(data.length);//获得加密块加密后块大小
            int leavedSize = data.length % blockSize;
            int blocksSize = leavedSize != 0 ? data.length / blockSize + 1 : data.length / blockSize;
            byte[] raw = new byte[outputSize * blocksSize];
            int i = 0;
            while (data.length - i * blockSize > 0) {
                if (data.length - i * blockSize > blockSize)
                cipher.doFinal(data, i * blockSize, blockSize, raw, i * outputSize);
                else
                cipher.doFinal(data, i * blockSize, data.length - i * blockSize, raw, i * outputSize);
                //这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把byte[]放到ByteArrayOutputStream中
                //，而最后doFinal的时候才将所有的byte[]进行加密，可是到了此时加密块大小很可能已经超出了OutputSize所以只好用dofinal方法。
                i++;
            }
            return raw;
        } catch (Exception e) {
        throw new Exception(e.getMessage());
        }
    }

    /**
     * <p>
     * 公钥加密
     * </p >
     *
     * @param data 源数据
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String publicKey) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > 117) {
                cache = cipher.doFinal(data, offSet, 117);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * 117;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;

    }
    
    /**
    * 解密
    * @param key 解密的密钥
    * @param raw 已经加密的数据
    * @return 解密后的明文
    * @throws Exception
    */
    public byte[] decrypt(Key key, byte[] raw) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
            cipher.init(cipher.DECRYPT_MODE, key);
            int blockSize = cipher.getBlockSize();
            ByteArrayOutputStream bout = new ByteArrayOutputStream(64);
            int j = 0;
            while (raw.length - j * blockSize > 0) {
                bout.write(cipher.doFinal(raw, j * blockSize, blockSize));
                j++;
            }
            return bout.toByteArray();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    
    /**
     * 返回公钥
     * @return
     * @throws Exception 
     */
    public RSAPublicKey getRSAPublicKey() throws Exception{
        
        //获取公钥
        RSAPublicKey pubKey = (RSAPublicKey) keyPair.getPublic();
        //获取公钥系数(字节数组形式)
        byte[] pubModBytes = pubKey.getModulus().toByteArray();
        //返回公钥公用指数(字节数组形式)
        byte[] pubPubExpBytes = pubKey.getPublicExponent().toByteArray();
        //生成公钥
        RSAPublicKey recoveryPubKey = this.generateRSAPublicKey(pubModBytes,pubPubExpBytes);
        return recoveryPubKey;
    }
    
    /**
     * 获取私钥
     * @return
     * @throws Exception 
     */
    public RSAPrivateKey getRSAPrivateKey() throws Exception{
        
        //获取私钥
        RSAPrivateKey priKey = (RSAPrivateKey) keyPair.getPrivate();
        //返回私钥系数(字节数组形式)
        byte[] priModBytes = priKey.getModulus().toByteArray();
        //返回私钥专用指数(字节数组形式)
        byte[] priPriExpBytes = priKey.getPrivateExponent().toByteArray();
        //生成私钥
        RSAPrivateKey recoveryPriKey = this.generateRSAPrivateKey(priModBytes,priPriExpBytes);
        return recoveryPriKey;
    }
    
    /**
     * 使用getPublicKey得到公钥,返回类型为PublicKey
     * @param base64 String to PublicKey
     * @throws Exception
     */
    public static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes;
        //keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        keyBytes = BASE64.decode(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }
    
    /**
     * 转换私钥
     * @param base64 String to PrivateKey
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes;
        //keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        keyBytes = BASE64.decode(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }
    /**
    * 测试
    * @param args
    * @throws Exception
    */
    public static void main(String[] args) throws Exception {
        
        RSAUtil rsa = new RSAUtil();

        String str = "董利伟";
        RSAPublicKey pubKey = rsa.getRSAPublicKey();
        RSAPrivateKey priKey = rsa.getRSAPrivateKey();
        System.out.println("公钥--->"+BASE64.encode(pubKey.getEncoded()));
        System.out.println("私钥--->"+BASE64.encode(priKey.getEncoded()));
        
        String encrypt = BASE64.encode(rsa.encrypt(pubKey,str.getBytes()));
        System.out.println("加密后==" + BASE64.encode(rsa.encrypt(pubKey,str.getBytes())));
        
        String encryptWK = BASE64.encode(rsa.encryptByPublicKey(str.getBytes(),BASE64.encode(pubKey.getEncoded())));
        System.out.println("加密后WK==" + BASE64.encode(rsa.encryptByPublicKey(str.getBytes(),BASE64.encode(pubKey.getEncoded()))));
        
        System.out.println("解密后==" + new String(rsa.decrypt(priKey, BASE64.decode(encrypt))));
        System.out.println("解密后WK==" + new String(rsa.decrypt(priKey, BASE64.decode(encryptWK))));

        /*
        String jsonStr = "{\"partnerId\":\"hyjf\",\"requestTime\":\"20161107142619\",\"version\":\"1.0.0\"}";
        String base64publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCL/ru4uKLWNxNelNU2cBxsUhmdJ0NQyKSPf1S4en+E2TkWEAKd2B8ZkwNH9LNa6QEPccFoKG7YejsgRlngw9fcISZ8S1cQy3PPzujKLhd1cK0123cXM3FL2e3QV3aEOnc7Y+kzdMi18HhVa7AOg8cWxW/P3jFmh4y8HwW2T1VErwIDAQAB";
        String encryptByPublicKey = BASE64.encode(rsa.encrypt(RSAUtil.getPublicKey(base64publicKey),jsonStr.getBytes("UTF-8")));
        
        String encryptByPublicKey2 = BASE64.encode(encryptByPublicKey(jsonStr.getBytes("UTF-8"), base64publicKey));
        System.out.println(encryptByPublicKey);
        System.out.println(encryptByPublicKey2);
        System.out.println("解密后==" + BASE64.encode(rsa.decrypt(getPublicKey(base64publicKey), encryptByPublicKey2.getBytes())));
        */
    }
}
