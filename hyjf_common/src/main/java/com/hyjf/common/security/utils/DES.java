package com.hyjf.common.security.utils;

import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.hyjf.common.validator.Validator;

/**
 * DES加密
 */
public class DES {
	private static byte[] iv = { 1, 2, 3, 4, 5, 6, 7, 8 };

	// 加密模式
	public static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";
	public static final String ALGORITHM_DES_ECB = "DES";
	public static final String ALGORITHM_DES_ECB_NOPADDING = "DES/ECB/NoPadding";

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
		if (message == null || message.equals("")) {
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
			e.printStackTrace();
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
			e.printStackTrace();
			return null;
		}
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
			return new String(BASE64.encode(encryptedData));
		} catch (Exception e) {
			e.printStackTrace();
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
			e.printStackTrace();
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
			e.printStackTrace();
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

	/**
	 * 测试 获取所有加密数据
	 * 
	 * @param secretKey
	 */
	public static void printResult(String secretKey, String mobile) {
		String appKey = "A9A9BA4E";
		System.out.println("secretKey=" + encryptDES_ECB(secretKey, appKey));
		String time = "20161108010101999";
		System.out.println("timestamp=" + encryptDES_ECB(time, appKey));
		String asciiresult = sortByAscii(secretKey + time);
		System.out.println("secretKey的明文与timeStamp的明文先ASCII升序排序： " + sortByAscii(secretKey + time));
		System.out.println("uniqueSign=" + MD5Utils.MD5(asciiresult));
		String requestobject = encryptDES_ECB("{\"mobile\":\"" + mobile + "\",\"platform\":\"微可\"}", secretKey);
		System.out.println("requestObject=" + requestobject);
	}

	/**
	 * 字符串按照ascii排序
	 *
	 * @param content
	 * @return
	 */
	public static String sortByAscii(String content) {

		if (Validator.isNull(content)) {
			return "";
		}
		char[] ch = content.toCharArray();
		Arrays.sort(ch);

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ch.length; i++) {
			sb.append(ch[i]);
		}

		return sb.toString();
	}

	public static void main(String[] args) {
		// 解密
		// String a=
		// decodeValue("1234abcd","0JUOPdC/NSE1roN4COvRLJC5JfIqNbTlfPkHNeE0CJlQVI7PuzHDva++7324
		// nDc0dHtGjDTGX10=");
		// System.out.println(a);

		// String b=
		// encode("1234abcd","{\"mobile\":\"110\",\"platform\":\"微可车贷\"}");
		// System.out.println(b);
		// 加密
		String c = decodeValue("p3nm3e64", "L5euOglC9qz39P/fkplszo0ejJZyN7ByhdxDsGPSYwgC3nfD0v8V5nx3LInv07BL6kwukYOcjEZGNbi26uK4+jiuYR3bnhzcaL//HUIK0gn8U7atBa002LZwyheoivln2nGswoiTeU1wFIvAjXI6W/UISi+ZoT5A8H+X36ojdSRB4THDyN3+lwGNWEgRfGRbUOosPnafCGvOZGzU+RY/41BaVBZ7brChaZQC4HPJEM+XALdsolFb3jEtnI5zz8iJ0p6DeiCal60KO0ajz/jMsA9ZTZrWIWu4akPnCUO6J7xQ7fEtRx/AV0mHikpHGWdrJo854ukpwdYf01GMulC2J2I4ZAxbel5EVlLN0bHSg5uOnyuBXutaoGRgdOv3XnSuFipGECPITZa/1fzOTwhcNhZEJReJth2Qvn4dTU4NpcEBUkP15eojdMRqvoHL/s0SQ6SLYNoURrvKpCPXNLPjrPJhhoY0LXMk6/Ye8xJ1NHZdlmHWPxzOSw==");
		System.out.println(c);
		// String
		// e=encryptDES_ECB("{\"userId\":\"22301357\",\"platform\":\"微可车贷\"}","1234abcd");
		// System.out.println(e);
		// String
		// f=encryptDES_ECB("{\"userId\":\"22301376\",\"platform\":\"微可车贷\"}","aaaa1234");
		// System.out.println(f);

//		String secretKey = encryptDES_ECB("xtd9j7te", "A9A9BA4E");
//		System.out.println(secretKey);
//		secretKey = encryptDES_ECB("20161108114458027", "A9A9BA4E");
//		System.out.println(secretKey);

		// String uniqueSign=
		// MD5Utils.MD5(SecretUtil.sortByAscii("aaaa1234"+"201611081129");

	}

}
