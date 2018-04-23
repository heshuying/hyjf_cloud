/**
 * Description:通用性返回结果Bean
 * Copyright: Copyright (HYJF Corporation)2017
 * Company: HYJF Corporation
 * @author: LiuBin
 * @version: 1.0
 * Created at: 2017年8月7日 上午9:37:48
 * Modification History:
 * Modified by : 
 */
	
package com.hyjf.common.result;

/**
 * @author LiuBin
 */

public class BaseResult {
	private boolean resultBool;
	private Integer resultInt;
	private String resultStr;
	private String resultMsg;
	/**
	 * resultBool
	 * @return the resultBool
	 */
	
	public boolean isResultBool() {
		return resultBool;
	}
	/**
	 * @param resultBool the resultBool to set
	 */
	
	public void setResultBool(boolean resultBool) {
		this.resultBool = resultBool;
	}
	/**
	 * resultInt
	 * @return the resultInt
	 */
	
	public Integer getResultInt() {
		return resultInt;
	}
	/**
	 * @param resultInt the resultInt to set
	 */
	
	public void setResultInt(Integer resultInt) {
		this.resultInt = resultInt;
	}
	/**
	 * resultStr
	 * @return the resultStr
	 */
	
	public String getResultStr() {
		return resultStr;
	}
	/**
	 * @param resultStr the resultStr to set
	 */
	
	public void setResultStr(String resultStr) {
		this.resultStr = resultStr;
	}
	/**
	 * resultMsg
	 * @return the resultMsg
	 */
	
	public String getResultMsg() {
		return resultMsg;
	}
	/**
	 * @param resultMsg the resultMsg to set
	 */
	
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

}

	