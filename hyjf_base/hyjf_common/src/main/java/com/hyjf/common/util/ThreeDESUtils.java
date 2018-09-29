/**
 * Description:（类功能描述-必填） 需要在每个方法前添加业务描述，方便业务业务行为的BI工作
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: b
 * @version: 1.0
 * Created at: 2015年12月8日 下午12:00:25
 * Modification History:
 * Modified by : 
 */

package com.hyjf.common.util;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;

/**
 * 三重加解密工具类
 */
public class ThreeDESUtils {
	private static final String Algorithm = "DESede"; // 定义 加密算法,可用

	public static String Decrypt3DES(String key, String value) throws Exception {
		byte[] b = decrypt(GetKeyBytes(key), Base64.decode(value));
		return new String(b);
	}

	public static String Encrypt3DES(String key, String value) throws Exception {
		String str = byte2Base64(encrypt(GetKeyBytes(key), value.getBytes()));
		return str;
	}
	
	//不对key进行MD5加密
	public static String Encrypt3DESWithOutMD5(String key, String value) throws Exception {
		String str = byte2Base64(encrypt(key.getBytes(), value.getBytes()));
		return str;
	}

	// 计算24位长的字符串值
	public static byte[] GetKeyBytes(String strKey) throws Exception {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
		try {
			byte[] strTemp = strKey.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
            char[] str = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str).substring(0, 24).getBytes();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 字节数组转16位字符串
	 * 
	 * @param digest
	 * @return
	 */
	public static String bytetoHexString(byte[] digest) {
		StringBuffer sb = new StringBuffer();
		for (byte b : digest) {
			int i = b & 0xff;
			if (i < 0xf) {
				sb.append(0);
			}
			sb.append(Integer.toHexString(i));
		}
		return sb.toString();
	}

	// keybyte为加密密钥
	// src为加密后的缓冲区
	public static byte[] encrypt(byte[] keybyte, byte[] src) {
		try {
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm); // 加密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (Exception e3) {
			e3.printStackTrace();
		}
		return null;

	}

	// keybyte为加密密钥
	// src为加密后的缓冲区
	public static byte[] decrypt(byte[] keybyte, byte[] src) {
		try { // 生成密钥
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
			// 解密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.DECRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	// 转换成base64编码
	public static String byte2Base64(byte[] b) {
		return Base64.encode(b);
	}

}
