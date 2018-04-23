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
	
package com.hyjf.common.result;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hyjf.common.util.ApiSignUtil;

/**
 * API结果返回Bean
 * 成功返回“000”，返回值加签chkValue
 * @author liubin
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultApiBean<T> extends ResultBean<T> implements Serializable {
	private static final long serialVersionUID = 5413541226545232L;
	public static final String SUCCESS = "000";
	
	private String chkValue = null; // response时的签名，可选

	{
		// 成功码变更 "0" → "000"
		if (super.getStatus() == ResultBean.SUCCESS) {
			super.setStatus(ResultApiBean.SUCCESS);
		}
		// 返回值加签
		this.chkValue = ApiSignUtil.encryptByRSA(super.getStatus());
	}

	public ResultApiBean() {
		super();
	}

	public ResultApiBean(T data) {
		super(data);
	}

	public ResultApiBean(Throwable e) {
		super(e);
	}

	public ResultApiBean(String status, String statusDesc) {
		super(status,statusDesc);
	}
	
	/**
	 * @param statusDesc the statusDesc to set
	 */
	public void setStatusInfo(String status, String StatusDesc) {
		super.setStatus(status);
		super.setStatusDesc(StatusDesc);
		// 返回值加签
		this.setChkValue(status);
	}

	public void setStatus(String status) {
		super.setStatus(status);
		// 返回值加签
		this.setChkValue(status);
	}

	public String getChkValue() {
		return chkValue;
	}
	
	public void setChkValue(String... encPramas) {
		//加签
		this.chkValue = ApiSignUtil.encryptByRSA(encPramas);
	}
}
