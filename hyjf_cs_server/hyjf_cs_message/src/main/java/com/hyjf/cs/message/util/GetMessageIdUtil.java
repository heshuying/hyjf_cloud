package com.hyjf.cs.message.util;

import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.GetDate;

/**
 * @author：xiasq
 * @Date: 2018/10/22 16:39
 */
public class GetMessageIdUtil {

	private static int EXPIRE_SECONDS = 24 * 60 * 60 * 2;

	public static synchronized String getNewMsgCode(String tagCode) {
		String key = tagCode + "_" + GetDate.getDate("yyyyMMdd") + "_";
		Long value = RedisUtils.incr(key);
		RedisUtils.expire(key, EXPIRE_SECONDS);
		int result = 1;
		if (value == null) {
		} else {
			result = value.intValue();
		}
		return key.concat(String.format("%05d", result));
	}
}
