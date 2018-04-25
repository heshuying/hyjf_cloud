package com.hyjf.common.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Enumeration;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

public class RSAKeyUtil {
	private PublicKey publicKey;
	private PrivateKey privateKey;

	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}

	public RSAKeyUtil(File certFile) throws FileNotFoundException {
		InputStream is = new FileInputStream(certFile);
		this.publicKey = getPublicKey(is);
	}

	public RSAKeyUtil(String certPath) throws FileNotFoundException {
		FileInputStream fis = new FileInputStream(certPath);
		this.publicKey = getPublicKey(fis);
	}

	public RSAKeyUtil(File keysFile, String pwd) throws GeneralSecurityException, IOException {
		InputStream is = new FileInputStream(keysFile);
		char[] pwds = pwd.toCharArray();
		KeyStore ks = getKeyStore(is, pwds);
		String alias = getKeyAlias(ks);
		if (alias != null) {
			this.privateKey = (PrivateKey) ks.getKey(alias, pwds);
			this.publicKey = ks.getCertificate(alias).getPublicKey();
		}
	}

	public RSAKeyUtil(String keysPath, String pwd) throws GeneralSecurityException, IOException {
		InputStream is = getClass().getClassLoader().getResourceAsStream(keysPath);
		char[] pwds = pwd.toCharArray();
		KeyStore ks = getKeyStore(is, pwds);
		String alias = getKeyAlias(ks);
		if (alias != null) {
			this.privateKey = (PrivateKey) ks.getKey(alias, pwds);
			this.publicKey = ks.getCertificate(alias).getPublicKey();
		}
	}

	/**
	 * 获取公钥 
	 * publickey:
	 * @param certPath
	 * @return
	 */
	public static PublicKey getPublicKey(String certPath) {
		return getPublicKey(getStream(certPath));
	}

	public static PublicKey getPublicKeyBase64(String keyEncode) {
		return getPublicKey(Base64.decodeBase64(keyEncode));
	}

	/**
	 * 获取公钥
	 * @param certStream
	 * @return
	 */
	public static PublicKey getPublicKey(InputStream certStream) {
		try {
			// 开始获取公钥
			if (certStream != null) {
				// 通过加密算法获取公钥
				Certificate cert = null;
				try {
					CertificateFactory cf = CertificateFactory.getInstance("X.509"); // 指定证书类型
					cert = cf.generateCertificate(certStream); // 获取证书
					return cert.getPublicKey(); // 获得公钥
				} finally {
					if (certStream != null) {
						certStream.close();
					}
				}
			}
		} catch (IOException e) {
			System.out.println("无法获取url连接");
			e.printStackTrace();
		} catch (CertificateException e) {
			System.out.println("获取证书失败");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取公钥
	 * @param keyBytes
	 * @return PublicKey
	 */
	public static PublicKey getPublicKey(byte[] keyBytes) {
		try {
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PublicKey publicKey = keyFactory.generatePublic(keySpec);
			return publicKey;
		} catch (NoSuchAlgorithmException e) {
			System.out.println("初始化加密算法时报错");
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			System.out.println("初始化公钥时报错");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 从公钥数据取得公钥
	 * 
	 * @param keyBytes
	 *            only keyBytes, no header
	 * @return PublicKey
	 */
	public static PublicKey getPublicKey1(byte[] keyBytes) {
		byte[] bX509PubKeyHeader = { 48, -127, -97, 48, 13, 6, 9, 42, -122, 72, -122, -9, 13, 1, 1, 1, 5, 0, 3, -127,
				-115, 0 };
		byte[] bPubKey = new byte[keyBytes.length + bX509PubKeyHeader.length];
		System.arraycopy(bX509PubKeyHeader, 0, bPubKey, 0, bX509PubKeyHeader.length);
		System.arraycopy(keyBytes, 0, bPubKey, bX509PubKeyHeader.length, keyBytes.length);
		return getPublicKey(keyBytes);
	}

	/**
	 * 获取公钥
	 * @param keyFile 
	 * 		the file of RSA Private Key file
	 * @return PrivateKey
	 * @throws IOException
	 */
	public static PrivateKey getPrivateKey(String keyFile) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(getStream(keyFile)));
		String keyEncode = "";
		String line = br.readLine();
		line = br.readLine();
		while (line.charAt(0) != '-') {
			keyEncode += line;// + "\r";
			line = br.readLine();
			if (line == null) {
				break;
			}
		}
		br.close();
		System.out.println(keyEncode);
		return getPrivateKey(Base64.decodeBase64(keyEncode));
	}

	/**
	 * 获取私钥
	 * @param p12Path 文件路径
	 * @param pwd 私钥密码
	 *            the pcks12 keystore file path in class path
	 * @return PrivateKey
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	public static PrivateKey getPrivateKey(String keysPath, String pwd) throws GeneralSecurityException, IOException {
		return getPrivateKey(getStream(keysPath), pwd);
	}

	/**
	 * 获取私钥
	 * @param is 输入流
	 *            keystore inutstream
	 * @param pwd 私钥密码
	 * @return
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	public static PrivateKey getPrivateKey(InputStream is, String pwd) throws GeneralSecurityException, IOException {
		char[] pwds = pwd.toCharArray();
		KeyStore ks = getKeyStore(is, pwds);
		String alias = getKeyAlias(ks);
		if (alias != null) {
			return (PrivateKey) ks.getKey(alias, pwds);
		}
		return null;
	}

	public static PrivateKey getPrivateKeyBase64(String keyEncode) {
		return getPrivateKey(Base64.decodeBase64(keyEncode));
	}

	/**
	 * 获取私钥
	 * @param keyBytes
	 *            PKCS8Encoded key bytes
	 * @return PrivateKey
	 */
	public static PrivateKey getPrivateKey(byte[] keyBytes) {
		try {
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
			return privateKey;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取私钥
	 * @param keyFile
	 *            key pem file in class path
	 * @return PrivateKey
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	public static PrivateKey getPrivateKey2(String keyFile) throws GeneralSecurityException, IOException {
		return getPrivateKey2(getStream(keyFile));
	}

	/**
	 * 获取私钥
	 * @param is
	 *            encode key inputstream
	 * @return PrivateKey
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	public static PrivateKey getPrivateKey2(InputStream is) {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		int count = 0;
		try {
			while ((count = is.read(buf)) != -1) {
				bout.write(buf, 0, count);
				buf = new byte[1024];
			}
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		PrivateKey prikey = getPrivateKey(bout.toByteArray());
		return prikey;
	}

	/**
	 * 获取文件流
	 * @param path
	 * @return
	 */
	private static InputStream getStream(String path) {
		return RSAKeyUtil.class.getClassLoader().getResourceAsStream(path);
	}

	/**
	 * 根据输入流密码获取keystore
	 * @param is
	 * @param pwds
	 * @return
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	private static KeyStore getKeyStore(InputStream is, char[] pwd) throws IOException, GeneralSecurityException {
		KeyStore ks = KeyStore.getInstance("PKCS12");
		ks.load(is, pwd);
		is.close();
		return ks;
	}

	/**
	 * 获取key串
	 * @param ks
	 * @return
	 * @throws KeyStoreException
	 */
	private static String getKeyAlias(KeyStore ks) throws KeyStoreException {
		Enumeration<String> enuml = ks.aliases();
		String keyAlias = null;
		if (enuml.hasMoreElements()) {
			keyAlias = (String) enuml.nextElement();
			if (ks.isKeyEntry(keyAlias)) {
				return keyAlias;
			}
		}
		return null;
	}

	/////////////////////// test method//////////////////////
	public static void testGenerateKeyPair() {
		try {
			KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA");
			keygen.initialize(1024, new SecureRandom("credit2go".getBytes()));
			KeyPair keys = keygen.genKeyPair();
			PublicKey publicKey = keys.getPublic();
			PrivateKey privateKey = keys.getPrivate();

			System.out.println("publicKey : " + new String(Hex.encodeHex(publicKey.getEncoded())));
			System.out.println("publicKey : " + Base64.encodeBase64String(publicKey.getEncoded()));

			System.out.println("privateKey: " + new String(Hex.encodeHex(privateKey.getEncoded())));
			System.out.println("privateKey: " + Base64.encodeBase64String(privateKey.getEncoded()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void testGenerateKeys() throws Exception {
		byte[] keyBytes1 = Hex.decodeHex(
				"30819f300d06092a864886f70d010101050003818d0030818902818100886a4ba7ad410db5e579244afefa34dbd42dbfa0aa9d9d7079940b95d1b4e8f8f2d598c45ace42fb5ccc15941300d4723f22acc90c222ae5631fe9a4c7e5a6d8cc5333da22404a4fd2ba0dd58e1e77be12b590077e6ac3ee30658619669760036b7721ad6229289b13a6801dfcf55fefa20b43fbdbbfbc3034a15bf9dc8b970f0203010001"
						.toCharArray());
		PublicKey publicKey1 = getPublicKey(keyBytes1);
		System.out.println("PublicKey1: " + new String(Hex.encodeHex(publicKey1.getEncoded())));
		System.out.println("PublicKey1: " + Base64.encodeBase64String(publicKey1.getEncoded()));

		byte[] keyBytes2 = Base64.decodeBase64(
				"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDGR1Tvv5OUwUuonkTgqbzN53+4wqB5mr+k6mMFKqUJ7Gu0t22urHBDC27zyARwMf45V/CRoRvmJCQi2VfntEuKkhAiSDjBz3AUuHrcpnSV0d+iGFSQ2IcbWVhI2MoUuWCiGFoRWbsQa5cUjztuXJfakvROcLYNhXTnGlCq83LFmQIDAQAB");
		PublicKey publicKey2 = getPublicKey(keyBytes2);
		System.out.println("PublicKey2: " + new String(Hex.encodeHex(publicKey2.getEncoded())));
		System.out.println("PublicKey2: " + Base64.encodeBase64String(publicKey2.getEncoded()));

		byte[] keyBytes3 = Base64.decodeBase64(
				"MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMZHVO+/k5TBS6ieROCpvM3nf7jCoHmav6TqYwUqpQnsa7S3ba6scEMLbvPIBHAx/jlX8JGhG+YkJCLZV+e0S4qSECJIOMHPcBS4etymdJXR36IYVJDYhxtZWEjYyhS5YKIYWhFZuxBrlxSPO25cl9qS9E5wtg2FdOcaUKrzcsWZAgMBAAECgYEAsTKEdLJoKPPKMsom0gS/Z+Bwo2TEV4j1pmOV1MM0tTalVt7q1cTjmvc31AO3+7Ch+wtdQjiSH03DQaWtSrQ1IPINOpl22wiA5pS1iHSOJQtCIxqvAwoEs78E5D1/0TUs8O4P1SfZbpMabb0pLgFhtBQWGwvJN6zETSH/jUa1pNUCQQDjCikIh06qGA4Ws2l2H760n5FTzVXZhDMFZSqVEFsJa3ocumbdcQVPFE3eLBv3ysMv15MJ5JbtlpctSf0slrOHAkEA35H/UkNf+lR58nPZ5Wgu7tE3siFsou9RcNxIj2c5w4wj4gqyD8q3g+wZLUtvLZomlfMTn+a0MUz4luB0OC9F3wJALbflDWeZypyvcZjNOtEnqN2c+SAXEocRcxutGHlDq8DtxQ0wG5VfuU3gZEXDmAFHjsG9RTe3wL4bUS5eAfWSzwJBAJP7PCfERtwYXFt6CWlKa35R5261pwW2KI7uj+yzo81mjj3JXJENWiks9oE/pAhoN1AbhArcFrtnOkgLq4Gg8LsCQEKy7soJ4tcYvWED8uKq8x5fPoPHPk2OdJ3G+OVhKx1/yyfUFLjkTeN3+YHAmg89fyb4Yj/lQjPOoUJvonjIp4M=");
		PrivateKey privateKey = getPrivateKey(keyBytes3);
		System.out.println("privateKey3: " + new String(Hex.encodeHex(privateKey.getEncoded())));
		System.out.println("privateKey3: " + Base64.encodeBase64String(privateKey.getEncoded()));

		RSAKeyUtil ru1 = new RSAKeyUtil("certs/credit2go.p12", "credit2go");
		System.out.println("PublicKey5: " + new String(Hex.encodeHex(ru1.getPublicKey().getEncoded())));
		System.out.println("PublicKey5: " + Base64.encodeBase64String(ru1.getPublicKey().getEncoded()));
		System.out.println("privateKey6: " + new String(Hex.encodeHex(ru1.getPrivateKey().getEncoded())));
		System.out.println("privateKey6: " + Base64.encodeBase64String(ru1.getPrivateKey().getEncoded()));

		RSAKeyUtil ru2 = new RSAKeyUtil(new File("S:/work/dev/openssl/_cedit2go/certs/credit2go.crt"));
		System.out.println("PublicKey7: " + new String(Hex.encodeHex(ru2.getPublicKey().getEncoded())));
		System.out.println("PublicKey7: " + Base64.encodeBase64String(ru2.getPublicKey().getEncoded()));
	}

	public static void main(String[] args) {
		try {
			// getPrivateKey("certs/credit2go.p12","pwd");
			testGenerateKeyPair();
			testGenerateKeys();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
