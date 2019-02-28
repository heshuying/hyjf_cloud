package com.hyjf.common.jwt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xiasq
 * @version Token, v0.1 2019-02-28 10:52
 */
public class Token {
	private static final Logger logger = LoggerFactory.getLogger(Token.class);

	public static String generate(String serialNo) {
		String uuid = UUID.randomUUID().toString();
		String timestamp = String.valueOf(System.currentTimeMillis());
		StringBuffer tokenStr = new StringBuffer().append(uuid).append(timestamp).append(serialNo);
		return MD5(tokenStr.toString()).toUpperCase();
	}

	public static String MD5(String input) {
		try {
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(input.getBytes());
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			StringBuffer hexString = new StringBuffer();
			// 字节数组转换为 十六进制 数
			for (int i = 0; i < md.length; i++) {
				String shaHex = Integer.toHexString(md[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			logger.error("MD5 error", e);
		}
		return null;
	}
}
