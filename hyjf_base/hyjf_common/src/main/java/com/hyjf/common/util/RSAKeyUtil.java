package com.hyjf.common.util;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Enumeration;

public class RSAKeyUtil {

	private static final Logger logger = LoggerFactory.getLogger(RSAKeyUtil.class);
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
			logger.error(e.getMessage());
		} catch (CertificateException e) {
			System.out.println("获取证书失败");
			logger.error(e.getMessage());
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
			logger.error(e.getMessage());
		} catch (InvalidKeySpecException e) {
			System.out.println("初始化公钥时报错");
			logger.error(e.getMessage());
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
	 * @paramp12Path 文件路径
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
			logger.error(e.getMessage());
		} catch (InvalidKeySpecException e) {
			logger.error(e.getMessage());
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
			logger.error(e.getMessage());
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
	 * @parampwds
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
}
