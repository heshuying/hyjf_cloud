package com.hyjf.callcenter.beans;

/**
 * @author libin
 * @version CallcenterBaseController, v0.1 2018/6/5
 * Description:（类功能描述-必填） 需要在每个方法前添加业务描述，方便业务业务行为的BI工作
 */
public class BaseFormBean {
	
	/**
	 * 调用接口的唯一识别号
	 */
	String uniqueNo;
	/**
	 * 请求时间戳
	 */
	private String timeStamp;
	/**
	 * md5校验码
	 */
	private String sign;
	
	public String getUniqueNo() {
		return uniqueNo;
	}
	public void setUniqueNo(String uniqueNo) {
		this.uniqueNo = uniqueNo;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
}
