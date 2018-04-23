package com.hyjf.common.security.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
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
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

public class RSAJSPUtil {

	   private static String RSAKeyStore = "RSAKey.txt";  
	   
       /** 
        * 生成秘钥对 
        * @return 
        * @throws Exception 
        */  
       public static KeyPair generateKeyPair() throws Exception {   
                try {    
                            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA",    
                                    new org.bouncycastle.jce.provider.BouncyCastleProvider());    
                            final int KEY_SIZE = 1024;// 没什么好说的了，这个值关系到块加密的大小，可以更改，但是不要太大，否则效率会低    
                            keyPairGen.initialize(KEY_SIZE, new SecureRandom());    
                            KeyPair keyPair = keyPairGen.generateKeyPair();    
                                
                            System.out.println(keyPair.getPrivate());    
                            System.out.println(keyPair.getPublic());    
                                
                            saveKeyPair(keyPair);    
                            return keyPair;    
                          } catch (Exception e) {    
                                    throw new Exception(e.getMessage());    
                      }    
 
       }  
       public static KeyPair getKeyPair() throws Exception {    
               FileInputStream fis = new FileInputStream(RSAKeyStore);    
               ObjectInputStream oos = new ObjectInputStream(fis);    
               KeyPair kp = (KeyPair) oos.readObject();    
               oos.close();    
               fis.close();    
               return kp;    
       }    
       public static void saveKeyPair(KeyPair kp) throws Exception {    
               FileOutputStream fos = new FileOutputStream(RSAKeyStore);    
               ObjectOutputStream oos = new ObjectOutputStream(fos);    
               oos.writeObject(kp);    
               oos.close();    
               fos.close();    
       }    
        public static RSAPublicKey generateRSAPublicKey(byte[] modulus,  byte[] publicExponent) throws Exception {    
           KeyFactory keyFac = null;    
               try {    
                       keyFac = KeyFactory.getInstance("RSA",    
                   new org.bouncycastle.jce.provider.BouncyCastleProvider());    
       } catch (NoSuchAlgorithmException ex) {    
           throw new Exception(ex.getMessage());    
       }    
   
       RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(new BigInteger(    
              modulus), new BigInteger(publicExponent));    
       try {    
          return (RSAPublicKey) keyFac.generatePublic(pubKeySpec);    
       } catch (InvalidKeySpecException ex) {    
           throw new Exception(ex.getMessage());    
       }    
   }  
          
          
        public static RSAPrivateKey generateRSAPrivateKey(byte[] modulus,    
           byte[] privateExponent) throws Exception {    
           KeyFactory keyFac = null;    
           try {    
               keyFac = KeyFactory.getInstance("RSA",    
                       new org.bouncycastle.jce.provider.BouncyCastleProvider());    
           } catch (NoSuchAlgorithmException ex) {    
               throw new Exception(ex.getMessage());    
           }    
           RSAPrivateKeySpec priKeySpec = new RSAPrivateKeySpec(new BigInteger(    
                   modulus), new BigInteger(privateExponent));    
           try {    
               return (RSAPrivateKey) keyFac.generatePrivate(priKeySpec);    
           } catch (InvalidKeySpecException ex) {    
               throw new Exception(ex.getMessage());    
           }    
       }    
 
        public static byte[] encrypt(PublicKey pk, byte[] data) throws Exception {    
       try {    
           Cipher cipher = Cipher.getInstance("RSA",    
                   new org.bouncycastle.jce.provider.BouncyCastleProvider());    
           cipher.init(Cipher.ENCRYPT_MODE, pk);    
           int blockSize = cipher.getBlockSize();// 获得加密块大小，如：加密前数据为128个byte，而key_size=1024    
           // 加密块大小为127    
           // byte,加密后为128个byte;因此共有2个加密块，第一个127    
           // byte第二个为1个byte    
           int outputSize = cipher.getOutputSize(data.length);// 获得加密块加密后块大小    
           int leavedSize = data.length % blockSize;    
           int blocksSize = leavedSize != 0 ? data.length / blockSize + 1    
                   : data.length / blockSize;    
           byte[] raw = new byte[outputSize * blocksSize];    
           int i = 0;    
           while (data.length - i * blockSize > 0) {    
               if (data.length - i * blockSize > blockSize)    
                   cipher.doFinal(data, i * blockSize, blockSize, raw, i    
                           * outputSize);    
               else    
                   cipher.doFinal(data, i * blockSize, data.length - i    
                           * blockSize, raw, i * outputSize);    
               // 这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把byte[]放到    
               // ByteArrayOutputStream中，而最后doFinal的时候才将所有的byte[]进行加密，可是到了此时加密块大小很可能已经超出了    
               // OutputSize所以只好用dofinal方法。    
   
               i++;    
           }    
           return raw;    
       } catch (Exception e) {    
           throw new Exception(e.getMessage());    
       }    
   }    
        public static byte[] decrypt(PrivateKey pk, byte[] raw) throws Exception {    
       try {    
           Cipher cipher = Cipher.getInstance("RSA",    
                   new org.bouncycastle.jce.provider.BouncyCastleProvider());    
           cipher.init(cipher.DECRYPT_MODE, pk);    
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
        
        /** * 16进制 To byte[] * @param hexString * @return byte[] */
        
        public static byte[] hexStringToBytes(String hexString) {
        	if (hexString == null || hexString.equals("")) {
                return null;
            }
    
            hexString = hexString.toUpperCase();
   
            int length = hexString.length() / 2;
            char[] hexChars = hexString.toCharArray();
            byte[] d = new byte[length];
            for (int i = 0; i < length; i++) {
                int pos = i * 2;
                d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
            }
            return d;
        } 
        /** * Convert char to byte * @param c char * @return byte */
        private static byte charToByte(char c) {
            return (byte) "0123456789ABCDEF".indexOf(c);
        }
        
        /**
         * 解密
         * @param rsapassword 密文
         * @param prik 私钥
         * @return
         */
        public static String rsaToPassword(String rsapassword,RSAPrivateKey prik){
        	String password = null;
        	try{  

                byte[] en_identityCode = hexStringToBytes(rsapassword);    

                byte[] de_identityCode = decrypt(prik, en_identityCode);    

                StringBuffer sb1 = new StringBuffer();    

                sb1.append(new String(de_identityCode));    

                password = sb1.reverse().toString();   

           } catch(Exception ex){  
        	   ex.printStackTrace();
           }
        	return password;
        }
}
