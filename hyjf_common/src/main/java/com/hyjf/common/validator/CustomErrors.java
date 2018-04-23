package com.hyjf.common.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.KeyValueBean;

/**
 * <p>
 * 共同错误文件
 * </p>
 *
 * @author gogtz
 * @version 1.0.0
 */
public class CustomErrors {
	private static final String PREFIX = "validateError-";

	/**
	 * 添加错误信息
	 * 
	 * @param mav
	 * @param key
	 */
	@SuppressWarnings("unchecked")
	public static void add(ModelAndView mav, String itemName, String key, String message) {
		if (mav == null) {
			return;
		}
		if (StringUtils.isNotBlank(itemName) && StringUtils.isNotBlank(key)) {
			Object obj = mav.getModel().get(PREFIX + itemName);
			List<KeyValueBean> list = null;
			if (obj == null) {
				list = new ArrayList<KeyValueBean>();
			} else {
				list = (List<KeyValueBean>) obj;
			}
			list.add(new KeyValueBean(key, message));

			mav.addObject(PREFIX + itemName, list);
		}
		mav.addObject(CustomConstants.FORM_HAS_ERROR, CustomConstants.FORM_HAS_ERROR);
	}

	/**
	 * 添加错误信息
	 * 
	 * @param mav
	 * @param key
	 */
	public static void add(JSONObject info, String itemName, String key, String message) {
		if (StringUtils.isNotBlank(itemName) && StringUtils.isNotBlank(key)) {
			if(StringUtils.isNoneBlank(message)){
				message=message.replace("{label}", itemName);
			}
			info.put(key, message);
		}
	}
	
	/**
	 * 添加错误信息
	 * 
	 * @param mav
	 * @param key
	 */
	public static void add(JSONObject info,String key, String message) {
		if (StringUtils.isNotBlank(key)) {
			info.put(key, message);
		}
	}
	/**
	 * 判断错误信息是否为空
	 * 
	 * @param mav
	 * @param key
	 */
	public static boolean isEmpty(ModelAndView mav) {
		if (mav == null) {
			return true;
		}

		for (String key : mav.getModel().keySet()) {
			if (StringUtils.isNotBlank(key) && key.startsWith(PREFIX)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 判断错误信息是否为空
	 * 
	 * @param mav
	 * @param key
	 */
	public static boolean isEmpty(JSONObject info) {
		if (info == null) {
			return true;
		}
		for (Entry<String, Object> entry : info.entrySet()) {
			String key = entry.getKey();
			if (StringUtils.isNotBlank(key)) {
				return false;
			}
		}
		return true;
	}
}
