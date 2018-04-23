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

/**
 * @author liubin
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultBean<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String SUCCESS = "0";
	public static final String ERROR = "-1";
	public static final String FAIL = "1";
	public static final String NO_PERMISSION = "2";
	private String statusDesc = "成功";
	private String status = SUCCESS;
	private T data;

	public ResultBean() {
		super();
	}

	public ResultBean(T data) {
		super();
		this.data = data;
	}

	public ResultBean(Throwable e) {
		super();
		this.status = ERROR;
		this.statusDesc = e.toString();
	}

	public ResultBean(String status, String statusDesc) {
		super();
		this.status = status;
		this.statusDesc = statusDesc;
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


}
