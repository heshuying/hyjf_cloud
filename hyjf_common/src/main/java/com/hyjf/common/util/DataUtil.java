/*
 * @Copyright: 2005-2017 www.2345.com. All rights reserved.
 */
package com.hyjf.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;

/**
 * @author xiasq
 * @version DataUtil, v0.1 2017/4/11 16:05
 */
public class DataUtil {

	private static Logger logger = LoggerFactory.getLogger(DataUtil.class);

	// 数组转对象
	public static Object ByteToObject(byte[] bytes) {

		if (bytes.length < 1) {
			return null;
		}

		Object obj = null;
		ByteArrayInputStream bis = null;
		ObjectInputStream ois = null;
		try {

			bis = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bis);
			obj = ois.readObject();
			bis.close();
			ois.close();
		} catch (Exception e) {
			logger.error("bytearray to object error...", e);
		}
		return obj;
	}

	// 对象转数组
	public static byte[] toByteArray(Object obj) {
		byte[] bytes = null;
		ByteArrayOutputStream bos = null;
		ObjectOutputStream oos = null;
		try {
			bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			oos.flush();
			bytes = bos.toByteArray();
			oos.close();
			bos.close();
		} catch (IOException e) {
			logger.error("object to bytearray error...", e);
		}
		return bytes;
	}

	// 逗号分隔的字符串转换为整形数组 "1,2,3,4" --> [1,2,3,4]
	public static Integer[] convertStringToIntegerArray(String srt) {
		String[] strTypes = srt.split(",");
		Integer[] array = new Integer[strTypes.length];
		for (int i = 0; i < strTypes.length; i++) {
			try {
				array[i] = Integer.parseInt(strTypes[i]);
			} catch (Exception e) {
				logger.error("convertStringToArray error...", e);
			}
		}
		return array;
	}

	public static int[] convertStringToIntArray(String srt) {
		String[] strTypes = srt.split(",");
		int[] array = new int[strTypes.length];
		for (int i = 0; i < strTypes.length; i++) {
			try {
				array[i] = Integer.parseInt(strTypes[i]);
			} catch (Exception e) {
				logger.error("convertStringToIntArray error...", e);
			}
		}
		return array;
	}

	/**
	 * 获取object 的里的字段值
	 * @param object
	 * @return
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 */
	public static <T> T getField(Object object, String fieldName, Class T) throws NoSuchFieldException, IllegalAccessException {
		Class<? extends Object> clazz = object.getClass();;

		while (clazz != Object.class) {
			try {
				Field id = clazz.getDeclaredField(fieldName);
				id.setAccessible(true);
				return (T)id.get(object);
			} catch (NoSuchFieldException ex) {
				clazz = clazz.getSuperclass();
			}
		}

		return null;
	}

}
