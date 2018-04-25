package com.hyjf.cs.user.result;

/**
 * @author xiasq
 * @version ApiResult, v0.1 2018/4/25 19:47
 */
public class ApiResult<T> {

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
