/**
 * Description:（类功能描述-必填） 需要在每个方法前添加业务描述，方便业务业务行为的BI工作
 * Copyright: Copyright (HYJF Corporation)2017
 * Company: HYJF Corporation
 * @author: lb
 * @version: 1.0
 * Created at: 2017年9月19日 下午2:11:38
 * Modification History:
 * Modified by : 
 */
	
package com.hyjf.common.security.utils;

import org.apache.commons.lang.StringUtils;

import com.hyjf.common.util.PropUtils;

/**
 * 接口验签工具类
 * @author liubin
 */

public class SignUtil {
	public static final String DEFAULT_ACCESSKEY = "aop.interface.accesskey";
	
	/**
	 * 验证签名(默认密钥)
	 * @param chkValue 调用方传入签名
	 * @param params 调用方传入验签参数
	 * @return
	 * @author liubin
	 */
    public static boolean checkSignDefaultKey(String chkValue, Object...params) {
		String sign = getSign(SignUtil.DEFAULT_ACCESSKEY, params);
		return StringUtils.equals(sign, chkValue) ? true : false;
	}
    
	/**
	 * 验证签名(自定义密钥)
	 * @param chkValue 自定义密钥
	 * @param chkValue 调用方传入签名
	 * @param params 调用方传入验签参数
	 * @return
	 * @author liubin
	 */
    public static boolean checkSignCustomizeKey(String accesskey, String chkValue, Object...params) {
		String sign = getSign(accesskey, params);
		return StringUtils.equals(sign, chkValue) ? true : false;
	}
    
	/**
	 * 接口规则（MD5）加密结果
	 * @param params
	 * @return
	 * @author liubin
	 */
	public static String getSign(String accesskey, Object...params) {
		String sign = StringUtils.EMPTY;
		String accessKey = PropUtils.getSystem(accesskey);

		//字符串拼接（accessKey + param1 + param2 + ... + accessKey)）
		StringBuilder builder = new StringBuilder();
		builder.append(accessKey);
		for (Object param : params) {
			if (params != null) {
				builder.append(String.valueOf(param));
			}
		}
		builder.append(accessKey);
		
		sign = builder.toString();
		return StringUtils.lowerCase(MD5.toMD5Code(sign));
	}
	
}

	