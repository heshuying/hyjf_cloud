package com.hyjf.cs.user.result;

import com.hyjf.common.constants.ErrorCode;

/**
 * @author sss
 * @version ApiResult, v0.1 2018/5/31 14:39
 */
public class AppResult<T> {

	public static final String STATUS_SUCCESS = "0";
	public static final String STATUS_FAIL = "1";
	public static final String STATUS_SUCCESS_DESC = "成功";
	public static final String STATUS_FAIL_DESC = "失败";

	public AppResult() {
		this.status = STATUS_SUCCESS;
		this.statusDesc = STATUS_SUCCESS_DESC;
	}

	public AppResult(String status, String statusDesc) {
		this.status = status;
		this.statusDesc = statusDesc;
	}

	public AppResult(ErrorCode code) {
		this.status = code.getErrCode();
		this.statusDesc = code.getMessage();
	}

	private T result;

	private String status;

	private String statusDesc;

	// 跳转前端地址
	private String callBackAction;

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public String getCallBackAction() {
		return callBackAction;
	}

	public void setCallBackAction(String callBackAction) {
		this.callBackAction = callBackAction;
	}
}
