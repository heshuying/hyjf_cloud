package com.hyjf.common.util;

import java.util.UUID;

/**
 * 
 * UUID是1.5中新增的一个类，在java.util下，用它可以产生一个号称全球唯一的ID。
 * 标准的UUID格式为：xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxx (8-4-4-4-12)
 * 
 * @author MZS
 * 
 */
public class CreateUUID {

	/**
	 * 获取UUID
	 * 
	 * @return uuidStr
	 */
	public static String getUUID() {
		String uuidStr = createUUID();
		return uuidStr;

	}

	/**
	 * 创建一个UUID
	 * 
	 * @return uuid
	 */
	public static String createUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

}
