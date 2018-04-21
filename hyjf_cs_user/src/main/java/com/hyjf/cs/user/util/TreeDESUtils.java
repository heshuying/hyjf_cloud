package com.hyjf.cs.user.util;

import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Value;

import com.hyjf.common.utils.ThreeDESUtils;

/**
 * @author xiasq
 * @version TreeDESUtils, v0.1 2018/4/21 15:24
 */
public class TreeDESUtils {

	@Value("${hyjf.3des.key}")
	private static String key;

	public static String getEncrypt(String timestamp, String data) {
		String kkey = key + timestamp;
		String encodeData = "";
		try {
			encodeData = ThreeDESUtils.Encrypt3DES(kkey, data);
			encodeData = URLEncoder.encode(encodeData, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encodeData;
	}
}
