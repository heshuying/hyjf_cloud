/**
 * Description:（类功能描述-必填） 需要在每个方法前添加业务描述，方便业务业务行为的BI工作
 * Copyright: Copyright (HYJF Corporation)2017
 * Company: HYJF Corporation
 * @author: lb
 * @version: 1.0
 * Created at: 2017年9月12日 上午9:45:06
 * Modification History:
 * Modified by : 
 */
	
package com.hyjf.common.exception;

/**
 * @author liubin
 */

public class CheckException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	/** 错误id */
	private String code;
	/** 错误返回url */
	private String url;
	/** 错误返回url */
	private Object data;	
	
	public CheckException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
		
	public CheckException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param message
	 * @param cause
	 */
		
	public CheckException(String message, Throwable cause) {
		super(message, cause);
	}

	
	
	/**
	 * @param message
	 */
		
	public CheckException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
		
	public CheckException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
			
	}

	public CheckException(String code, String message) {
		super(message);
		this.code = code;
	}
	
	public CheckException(String code, String message, String url) {
		super(message);
		this.code = code;
		this.url = url;
	}
	
	public CheckException(String code, String message, Object data) {
		super(message);
		this.code = code;
		this.data = data;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Object getData() {
		return data;
	}
	
	public void setData(Object data) {
		this.data = data;
	}
}

	