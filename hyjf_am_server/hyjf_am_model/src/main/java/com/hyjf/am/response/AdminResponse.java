package com.hyjf.am.response;

import com.hyjf.am.vo.BaseVO;

import java.util.List;

/**
 * @author xiasq
 * @version Response, v0.1 2018/1/21 22:18
 */
public class AdminResponse<T extends BaseVO> {
	public static final String SUCCESS = "00";
	public static final String SUCCESS_MSG = "成功";
	public static final String FAIL = "99";
	public static final String FAIL_MSG = "失败";

	private String rtn;
	private String message;
	private T result;
	private List<T> resultList;
	//admin增加返回总数
	private String recordTotal;

	public String getRecordTotal() {
		return recordTotal;
	}

	public void setRecordTotal(String recordTotal) {
		this.recordTotal = recordTotal;
	}

	public AdminResponse() {
		this.rtn = SUCCESS;
		this.message = SUCCESS_MSG;
	}

	public AdminResponse(String rtn, String message) {
		this.rtn = rtn;
		this.message = message;
	}

	public AdminResponse(String rtn, String message, T result) {
		this.rtn = rtn;
		this.message = message;
		this.result = result;
	}

	/**
	 * 校验返回值状态公用方法
	 * @param res
	 * @return
	 */
	public static boolean isSuccess(AdminResponse res){
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
