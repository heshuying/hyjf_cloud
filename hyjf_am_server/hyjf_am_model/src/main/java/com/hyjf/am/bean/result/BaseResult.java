/**
 * Description:（类功能描述-必填） 需要在每个方法前添加业务描述，方便业务业务行为的BI工作
 * Copyright: Copyright (HYJF Corporation)2017
 * Company: HYJF Corporation
 * @author: liubin
 * @version: 1.0
 * Created at: 2017年9月11日 下午4:48:51
 * Modification History:
 * Modified by : 
 */
	
package com.hyjf.am.bean.result;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hyjf.common.constants.MsgCode;
import com.hyjf.common.util.StringUtil;

import java.io.Serializable;

/**
 * 返回前端结果基类
 * @author liubin
 */
@JsonInclude(JsonInclude.Include.ALWAYS)
public class BaseResult<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String SUCCESS = "000";
	public static final String SUCCESS_DESC = "成功";
	public static final String ERROR = "-1";
	public static final String ERROR_DESC = "异常";
	public static final String FAIL = "1";
	public static final String FAIL_DESC = "失败";
	public static final String NO_PERMISSION = "2";
	public static final String NO_PERMISSION_DESC = "无权限";

	private String status = SUCCESS;
	private String statusDesc = SUCCESS_DESC;
	private String isGuess = "0";
	private T data;

	public BaseResult() {
		super();
	}

	public BaseResult(T data) {
		super();
		this.data = data;
	}

	public BaseResult(Throwable e) {
		super();
		this.status = ERROR;
		this.statusDesc = e.toString();
	}

	public BaseResult(String status, String statusDesc) {
		super();
		this.status = status;
		this.statusDesc = statusDesc;
	}

	public BaseResult(MsgCode msgCode, Object... params) {
		super();
		this.status = msgCode.getCode();
		this.statusDesc = StringUtil.getMessage(msgCode.getMsg(), params);
	}

	/**
	 * statusDesc
	 * @return the statusDesc
	 */
	
	public String getStatusDesc() {
		return statusDesc;
	}

	/**
	 * @param statusDesc the statusDesc to set
	 */
	public void setStatusInfo(String status, String statusDesc) {
		this.status = status;
		this.statusDesc = statusDesc;
	}

	/**
	 * 用枚举类型设定status和statusDesc，可选拼接参数
	 * @param msgCode
	 * @param params
	 */
	public void setStatusInfo(MsgCode msgCode, Object... params) {
		this.status = msgCode.getCode();
		this.statusDesc = StringUtil.getMessage(msgCode.getMsg(), params);
	}

	/**
	 * @param statusDesc the statusDesc to set
	 */
	
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	/**
	 * status
	 * @return the status
	 */
	
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * data
	 * @return the data
	 */
	
	public T getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(T data) {
		this.data = data;
	}

	public String getIsGuess() {
		return isGuess;
	}

	public void setIsGuess(String isGuess) {
		this.isGuess = isGuess;
	}

	/**
	 * @return
	 */
	@Override
	public String toString() {
		return JSON.toJSONString(this, SerializerFeature.WriteNullStringAsEmpty);
	}
}
