/**
 * Description:（类功能描述-必填） 需要在每个方法前添加业务描述，方便业务业务行为的BI工作
 * Copyright: Copyright (HYJF Corporation)2017
 * Company: HYJF Corporation
 * @author: lb
 * @version: 1.0
 * Created at: 2017年9月14日 上午8:51:53
 * Modification History:
 * Modified by : 
 */
	
package com.hyjf.common.validator;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.support.RequestContext;

import com.hyjf.common.enums.utils.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.util.GetSessionOrRequestUtils;

/**
 * @author liubin
 */

public class CheckUtil {
	/** 错误信息前缀 */
	private static final String PREFIX = "errors.";
	
	private static Object data = null;

	/**
	 * data
	 * @return the data
	 */
	
	public static Object getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	
	public static void setData(Object data) {
		CheckUtil.data = data;
	}

	/**
     * 验证结果为false时，抛出自定义Check异常(适用 信息码和信息在properties中)
     * @param condition	验证结果
     * @param errKey	信息key
     * @param params    信息用参数集
     */
    public static void check(boolean condition, String errKey, Object... params) {
    	if (!condition) {
    		throwCheckErrMsg(errKey, params);
    	}
    }

    /**
     * 抛出自定义Check异常(适用 信息码和信息在properties中)
     * @param errKey	
     * @param params
     */
    private static void throwCheckErrMsg(String errKey, Object... params) {
    	if (data != null) {
    		throw new CheckException(errKey, getErrorMessage(errKey, params), data);
    	}else {
    		throw new CheckException(errKey, getErrorMessage(errKey, params));
    	}
    }
    
	/**
	 * 验证结果为false时，抛出自定义Check异常(适用 信息码和信息在枚举中)
	 * @param condition	验证结果
	 * @param msgEnum   枚举信息
     * @param params 	信息用参数集
	 */
		
	public static void check(boolean condition, MsgEnum msgEnum, Object... params) {
    	if (!condition) {
    		throwCheckErrMsg(msgEnum, params);
    	}
	}
    
    /**
     * 抛出自定义Check异常(适用 信息码和信息在枚举中)
     * @param msgEnum	
     * @param params
     */
    private static void throwCheckErrMsg(MsgEnum msgEnum, Object... params) {
    	if (data != null) {
    		throw new CheckException(msgEnum.getCode(), getEnumMessage(msgEnum.getMsg(), params), data);
    	}else {
    		throw new CheckException(msgEnum.getCode(), getEnumMessage(msgEnum.getMsg(), params));
    	}
    }
    
	/**
	 * 取得错误信息
	 * 
	 * @param errKey
	 * @param param
	 * @return
	 */
	public static String getErrorMessage(String errKey, Object... params) {
		HttpServletRequest request = GetSessionOrRequestUtils.getRequest();

		RequestContext requestContext = new RequestContext(request);
		String message = requestContext.getMessage(PREFIX + errKey);

		if (Validator.isNotNull(message)) {
			if (message.contains("{0}")) {
				if (Validator.isNotNull(params)) {
					int i = 0;
					for (Object p : params) {
						message = message.replace("{" + i + "}", p == null ? "" : String.valueOf(p));
						i++;
					}
				}
			}
		}

		return message;
	}
	
	/**
	 * 取得错误信息
	 * 
	 * @param errKey
	 * @param param
	 * @return
	 */
	public static String getEnumMessage(String message, Object... params) {

		if (Validator.isNotNull(message)) {
			if (message.contains("{0}")) {
				if (Validator.isNotNull(params)) {
					int i = 0;
					for (Object p : params) {
						message = message.replace("{" + i + "}", p == null ? "" : String.valueOf(p));
						i++;
					}
				}
			}
		}

		return message;
	}
}

	