package com.hyjf.common.util;

import com.hyjf.common.cache.RedisUtils;

/**
 * 
 * <p>
 * Title:GetMessageIdUtil
 * </p>
 * <p>
 * Description: 消息推送,获取最新一条消息ID
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author 青狐小宝
 * @date 2016年8月5日 上午10:43:16
 */
public class GetMessageIdUtil {
	public static synchronized String getNewMsgCode(String tagCode) {
		String key = tagCode + "_" + GetDate.getDate("yyyyMMdd") + "_";
		if (RedisUtils.get(key) == null) {
			RedisUtils.set(key, "0");
		}
		Integer value = Integer.parseInt(RedisUtils.get(key));
		value++;
		RedisUtils.set(key, value + "");
		key = key + "00000";
		key = key.substring(0, key.length() - (value + "").length()) + value;
		return key;
	}
}
