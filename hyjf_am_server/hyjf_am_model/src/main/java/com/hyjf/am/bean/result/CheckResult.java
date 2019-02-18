package com.hyjf.am.bean.result;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于保存银行充值返回结果
 * @author jijun 20180616
 */
public class CheckResult{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean resultBool; // 结果true或者false
	private String resultMsg; // 单条结果描述(单结果返回)
	private Map<Object, Object> resultMsgMap = new HashMap<Object, Object>(); // 多条结果描述(多结果返回)

	public CheckResult() {
	}

	public CheckResult(boolean resultBool) {
		this.resultBool = resultBool;
	}

	public CheckResult(boolean resultBool, String resultMsg) {
		this.resultBool = resultBool;
		this.resultMsg = resultMsg;
	}

	public CheckResult(boolean resultBool, Map<Object, Object> resultMsgMap) {
		this.resultBool = resultBool;
		this.resultMsgMap = resultMsgMap;
	}

	public Map<Object, Object> getResultMsgMap() {
		return resultMsgMap;
	}

	public void setResultMsgMap(Map<Object, Object> resultMsgMap) {
		this.resultMsgMap = resultMsgMap;
	}

	public void putResultMsg(Object key, Object val) {
		this.resultMsgMap.put(key, val);
	}

	public boolean isResultBool() {
		return resultBool;
	}

	public boolean getResultBool() {
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

	/**
	 * 执行前每个方法前需要添加BusinessDesc描述
	 * @return
	 * @author LiuBin
	 */

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}

	