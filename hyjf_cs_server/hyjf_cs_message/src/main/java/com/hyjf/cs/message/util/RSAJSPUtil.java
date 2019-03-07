package com.hyjf.cs.message.util;

import com.hyjf.common.util.RSAKeyUtil;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

@Component
public class RSAJSPUtil {

	private static final Logger logger = LoggerFactory.getLogger(RSAJSPUtil.class);
	private static String RSAKeyStore = "RSAKey.txt";


	private static String HYJF_REQ_PRI_KEY_PATH;

	private static String HYJF_REQ_PUB_KEY_PATH;

	// 私钥文件名
	private static final String PRIK_NAME = "prik.crt";
	// 公钥文件名
	private static final String PUBK_NAME = "pubk.crt";


	public static String getHyjfReqPriKeyPath() {
		return RSAJSPUtil.HYJF_REQ_PRI_KEY_PATH;
	}

	@Value("${hyjf.req.pri.key}")
	public void setHyjfReqPriKeyPath(String hyjfReqPriKeyPath) {
		RSAJSPUtil.HYJF_REQ_PRI_KEY_PATH = hyjfReqPriKeyPath;
	}

	public static String getHyjfReqPubKeyPath() {
		return RSAJSPUtil.HYJF_REQ_PUB_KEY_PATH;
	}

	@Value("${hyjf.req.pub.key}")
	public void setHyjfReqPubKeyPath(String hyjfReqPubKeyPath) {
		RSAJSPUtil.HYJF_REQ_PUB_KEY_PATH = hyjfReqPubKeyPath;
	}

	/**
	 * 生成秘钥对
	 * 
	 * @return
	 * @throws Exception
	 */
	public static KeyPair generateKeyPair() throws Exception {
		try {
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA",
					new org.bouncycastle.jce.provider.BouncyCastleProvider());
			// 没什么好说的了，这个值关系到块加密的大小，可以更改，但是不要太大，否则效率会低
			final int KEY_SIZE = 1024;
			keyPairGen.initialize(KEY_SIZE, new SecureRandom());
			KeyPair keyPair = keyPairGen.generateKeyPair();

			saveKeyPair(keyPair);
			return keyPair;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}

	public static KeyPair getKeyPair() throws Exception {
		FileInputStream fis = new FileInputStream(RSAKeyStore);
		KeyPair kp = null;
		try (ObjectInputStream oos = new ObjectInputStream(fis)) {
			kp = (KeyPair) oos.readObject();
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
		return kp;
	}

	public static void saveKeyPair(KeyPair kp) throws Exception {
		FileOutputStream fos = new FileOutputStream(RSAKeyStore);
		try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(kp);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			if (fos != null) {
				fos.close();
			}
		}
	}

	public static RSAPublicKey generateRSAPublicKey(byte[] modulus, byte[] publicExponent) throws Exception {
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

	public static RSAPrivateKey generateRSAPrivateKey(byte[] modulus, byte[] privateExponent) throws Exception {
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

	public static byte[] encrypt(PublicKey pk, byte[] data) throws Exception {
		try {
			Cipher cipher = Cipher.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
			cipher.init(Cipher.ENCRYPT_MODE, pk);
			int blockSize = cipher.getBlockSize();// 获得加密块大小，如：加密前数据为128个byte，而key_size=1024
			// 加密块大小为127
			// byte,加密后为128个byte;因此共有2个加密块，第一个127
			// byte第二个为1个byte
			int outputSize = cipher.getOutputSize(data.length);// 获得加密块加密后块大小
			int leavedSize = data.length % blockSize;
			int blocksSize = leavedSize != 0 ? data.length / blockSize + 1 : data.length / blockSize;
			byte[] raw = new byte[outputSize * blocksSize];
			int i = 0;
			while (data.length - i * blockSize > 0) {
				if (data.length - i * blockSize > blockSize) {
					cipher.doFinal(data, i * blockSize, blockSize, raw, i * outputSize);
				} else {
                    cipher.doFinal(data, i * blockSize, data.length - i * blockSize, raw, i * outputSize);
                }
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
			Cipher cipher = Cipher.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
			cipher.init(Cipher.DECRYPT_MODE, pk);
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
		if (hexString == null || "".equals(hexString)) {
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
	 * 
	 * @param rsapassword
	 *            密文
	 * @return
	 */
	public static String rsaToPassword(String rsapassword) {
		String password = null;
		try {
			String prik = "";
			byte[] keyBytes3 =
			Base64.decodeBase64("MIIBNgIBADANBgkqhkiG9w0BAQEFAASCASAwggEcAgEAAoGBALVbwYZxzBE6" +
							"Dgxx/ZYCO2XJyH9Dj1mRN7TiCCqNU1JiJM9rgUW0aHFcMc+iSWGSIGyrQiT0" +
							"EA7moBsTfekwGRXIv16KNXTkDX1G8lzgmkFCpXNxEHAzyle/jHdqJ82dBzDa" +
							"rfHNSnZJPHNUcB3n5FojRty+eJZkuWwq/wmRGWEDAgEAAoGAckTPCfP3nT8U" +
							"DPlhyzu6yya5op4h21BpZhopBQ6o2jamdN6KxC2oxQxPAkGBtO2Kao35jikN" +
							"WSYs6QJ+CghZNNXSW8XlrnFqHQNTlpwehsdxpqH8N4OcPC3jl/ZkZdYhvfoY" +
					"CkG9ljdDE06fVSJZ40x3LSqXJCYbvkS2ppywkLkCAQACAQACAQACAQACAQA=");

			/*String req = getHyjfReqPriKeyPath();
			prik = readToString(req + PRIK_NAME);
			byte[] keyBytes3 = Base64.decodeBase64(prik);*/

			PrivateKey privateKey = RSAKeyUtil.getPrivateKey(keyBytes3);

			byte[] en_identityCode = hexStringToBytes(rsapassword);

			byte[] de_identityCode = decrypt(privateKey, en_identityCode);

			StringBuffer sb1 = new StringBuffer();

			sb1.append(new String(de_identityCode));

			password = sb1.reverse().toString();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return password;
	}

	/**
	 * 获取公钥
	 * 
	 * @param公钥存放路径
	 * @return
	 */
	public static String getPunlicKeys() {
		String filePath = getHyjfReqPubKeyPath() + PUBK_NAME;
		logger.info("公钥存放路径"+filePath);
		return readToString(filePath);
	}

	/**
	 * 读取文件内容
	 * 
	 * @param
	 * @return
	 */
	public static String readToString(String filePath) {
		String encoding = "UTF-8";
		File file = new File(filePath);
		Long filelength = file.length();
		logger.info("filelength:"+filelength);
		byte[] filecontent = new byte[filelength.intValue()];
		try (FileInputStream in = new FileInputStream(file)) {
			in.read(filecontent);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			return new String(filecontent, encoding);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
			return null;
		}
	}
}
