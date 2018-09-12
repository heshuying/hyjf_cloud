/**
 * Description:（类功能描述-必填） 需要在每个方法前添加业务描述，方便业务业务行为的BI工作
 * Copyright: Copyright (HYJF Corporation)2017
 * Company: HYJF Corporation
 * @author: lb
 * @version: 1.0
 * Created at: 2017年8月23日 下午2:59:35
 * Modification History:
 * Modified by : 
 */
	
package com.hyjf.cs.trade.bean;



public class OntimeCheckBean {
	//返回状态 
	//  0：成功
	// -1：未到发标时间！
	// -2：锁等待中！ 
	// -3 ：该标的非自动发标标的或者未到发标时间！
	// -4 ：定时标的状态修改失败！
	Integer status;
	//返回状态信息
	String statusInfo;
	//定时发标的时间戳
	Integer ontime;
	//服务器时间
	Integer nowtime;
	
	/**
	 * status
	 * @return the status
	 */
	
	public Integer getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * statusInfo
	 * @return the statusInfo
	 */
	
	public String getStatusInfo() {
		return statusInfo;
	}
	/**
	 * @param statusInfo the statusInfo to set
	 */
	
	public void setStatusInfo(String statusInfo) {
		this.statusInfo = statusInfo;
	}
	/**
	 * ontime
	 * @return the ontime
	 */
	
	public Integer getOntime() {
		return ontime;
	}
	/**
	 * @param ontime the ontime to set
	 */
	
	public void setOntime(Integer ontime) {
		this.ontime = ontime;
	}
	/**
	 * nowtime
	 * @return the nowtime
	 */
	
	public Integer getNowtime() {
		return nowtime;
	}
	/**
	 * @param nowtime the nowtime to set
	 */
	
	public void setNowtime(Integer nowtime) {
		this.nowtime = nowtime;
	}
	
}

	