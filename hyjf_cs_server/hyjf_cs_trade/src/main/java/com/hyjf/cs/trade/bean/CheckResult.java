/**
 * Description:（类功能描述-必填） 需要在每个方法前添加业务描述，方便业务业务行为的BI工作
 * Copyright: Copyright (HYJF Corporation)2017
 * Company: HYJF Corporation
 * @author: LiuBin
 * @version: 1.0
 * Created at: 2017年8月7日 上午9:55:24
 * Modification History:
 * Modified by : 
 */
	
package com.hyjf.cs.trade.bean;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.hyjf.cs.common.bean.result.BaseResult;

/**
 * @author jijun 20180616
 */

public class CheckResult extends BaseResult{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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

	