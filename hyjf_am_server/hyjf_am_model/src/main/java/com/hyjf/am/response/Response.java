package com.hyjf.am.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiasq
 * @version Response, v0.1 2018/1/21 22:18
 */
@SuppressWarnings("unchecked")
public class Response<T> implements Serializable {
	public static final String SUCCESS = "0";
	public static final String SUCCESS_MSG = "成功";
	public static final String FAIL = "1";
	public static final String FAIL_MSG = "失败";
	public static final String ERROR = "99";
	public static final String ERROR_MSG = "未知错误";

	private String rtn;
	private String message;
	private T result;

	private List<T> resultList = new ArrayList();


	public Response() {
		this.rtn = SUCCESS;
		this.message = SUCCESS_MSG;
	}

	public Response(T result) {
		this.rtn = SUCCESS;
		this.message = SUCCESS_MSG;
		this.result = result;
	}

	public Response(String rtn, String message) {
		this.rtn = rtn;
		this.message = message;
	}

	public Response(String rtn, String message, T result) {
		this.rtn = rtn;
		this.message = message;
		this.result = result;
	}

	/**
	 * 校验返回值状态公用方法
	 * @param res
	 * @return
	 */
	public static boolean isSuccess(Response res){
		if (res != null && SUCCESS.equals(res.rtn)){
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public String getRtn() {
		return rtn;
	}

	public void setRtn(String rtn) {
		this.rtn = rtn;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public List<T> getResultList() {
		return resultList;
	}

	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}

}
