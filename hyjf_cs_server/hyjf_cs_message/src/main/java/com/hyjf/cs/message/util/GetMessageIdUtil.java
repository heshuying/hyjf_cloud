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
		Integer value = RedisUtils.getObj(key, Integer.class);
		if (value == null) {
			value = 1;
		} else {
			value = value + 1;
		}
		RedisUtils.setObjEx(key, value, EXPIRE_SECONDS);

		return key.concat(String.format("%05d", value));
	}
}
