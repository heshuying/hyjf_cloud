package com.hyjf.cs.user.result;

/**
 * @author xiasq
 * @version ApiResult, v0.1 2018/4/25 19:47
 */
public class ApiResult<T> {

	public static final String STATUS_SUCCESS = "0";
	public static final String STATUS_FAIL = "1";
	public static final String STATUS_SUCCESS_DESC = "成功";
	public static final String STATUS_FAIL_DESC = "失败";

	public ApiResult() {
		this.status = STATUS_SUCCESS;
		this.statusDesc = STATUS_SUCCESS_DESC;
	}

	public ApiResult(String status, String statusDesc) {
		this.status = status;
		this.statusDesc = statusDesc;
	}

	private T result;

	private String status;

	private String statusDesc;

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
}
