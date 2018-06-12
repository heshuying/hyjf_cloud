/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.common.cache;

import java.util.Map;

/**
 * @author dxj
 * @version CacheUtil.java, v0.1 2018年6月12日 下午5:32:26
 */
public class CacheUtil {

	/**
	 * 获取param 制定类型所有key-value
	 * @param
	 * @return
	 */
	public static Map<String, String> getParamNameMap(String nameClass) {
		
		return RedisUtils.hgetall(nameClass);
	}

	/**
	 * 获取param 制定key value
	 * @param key
	 * @return
	 */
	public static String getParamName(String nameClass, String key) {
		
		return RedisUtils.hget(nameClass, key);
	}
}
