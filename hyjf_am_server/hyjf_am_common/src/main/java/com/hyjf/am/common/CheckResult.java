package com.hyjf.am.common;

import com.alibaba.fastjson.JSON;
import com.hyjf.common.constants.MsgCode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jijun 20180616
 */

public class CheckResult extends BaseResult{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CheckResult() {
	}

	public CheckResult(Object data) {
		super(data);
	}

	public CheckResult(Throwable e) {
		super(e);
	}

	public CheckResult(String status, String statusDesc) {
		super(status, statusDesc);
	}

	public CheckResult(MsgCode msgCode, Object... params) {
		super(msgCode, params);
	}

	private Map<String, String> infoMap = new HashMap<String, String>();

	private boolean resultBool;
	private String resultMsg;
	
	/**
	 * infoMap
	 * @return the infoMap
	 */
	
	public Map<String, String> getInfoMap() {
		return infoMap;
	}

	/**
	 * @param infoMap the infoMap to set
	 */
	
	public void setInfoMap(Map<String, String> infoMap) {
		this.infoMap = infoMap;
	}

	/**
	 * 执行前每个方法前需要添加BusinessDesc描述
	 * @return
	 * @author LiuBin
	 */
		
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

	public boolean isResultBool() {
		return resultBool;
	}

	public void setResultBool(boolean resultBool) {
		this.resultBool = resultBool;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

}

	