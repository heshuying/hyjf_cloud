package com.hyjf.cs.user.util;

import com.hyjf.common.util.ThreeDESUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.net.URLEncoder;

/**
 * @author xiasq
 * @version TreeDESUtils, v0.1 2018/4/21 15:24
 */
public class TreeDESUtils {

	private static final Logger logger = LoggerFactory.getLogger(TreeDESUtils.class);

	@Value("${hyjf.3des.key}")
	private static String key;

	public static String getEncrypt(String timestamp, String data) {
		String kkey = key + timestamp;
		String encodeData = "";
		try {
			encodeData = ThreeDESUtils.Encrypt3DES(kkey, data);
			encodeData = URLEncoder.encode(encodeData, "UTF-8");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return encodeData;
	}
}
