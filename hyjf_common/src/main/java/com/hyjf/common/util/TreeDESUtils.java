/**
 * Description:des3重加密
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: b
 * @version: 1.0
 * Created at: 2015年12月17日 上午11:44:20
 * Modification History:
 * Modified by : 
 */

package com.hyjf.common.util;

import java.net.URLEncoder;
import java.util.Properties;

import com.hyjf.common.security.utils.ThreeDESUtils;

/**
 * @author 郭勇
 */

public class TreeDESUtils {

	public static String getEncrypt(String timestamp, String data) {
		Properties properties = PropUtils.getSystemResourcesProperties();
		String key = properties.getProperty("hyjf.3des.key").trim();
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
