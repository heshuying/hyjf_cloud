package com.hyjf.callcenter.beans;

import java.io.Serializable;

/**
 * Description:江西银行账户实体类
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: LIBIN
 * @version: 1.0
 *           Created at: 2017年07月07日 下午2:33:39
 *           Modification History:
 *           Modified by :
 */
public class SrchTransferInfoBean implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 请求时间戳(暂时)
	 */
	private String timestamp;
	/**
	 * md5校验码(暂时)
	 */
	private String sign;
	
	//以下为需求定义字段
	/**
	 * 序号（自增的，暂时）
	 */
	private String creditId;
	/**
	 * 1.用户名
	 */
	private String username;
	/**
	 * 2.手机号
	 */
	private String mobile;
	/**
	 * 3.债转编号(eg.HZR1707110001)
	 */
	private String creditNid;
	/**
	 * 4.原始项目编号（admin项目编号）
	 */
	private String bidNid;
	/**
	 * 5.债权本金
	 */
	private String creditCapital;
	/**
	 * 6.转让本金
	 */
	private String creditCapitalPrice;
	/**
	 * 7.折让率
	 */
	private String creditDiscount;
	/**
	 * 8.转让价格
	 */
	private String creditPrice;
	/**
	 * 9.已转让金额
	 */
	private String creditCapitalAssigned;
	/**
	 * 10.转让状态(1:停止 ，2:完全承接, 其他：进行中)
	 */
	private String creditStatus;
	/**
	 * 11.操作平台 0:pc, 1:ios, 2:Android, 3:wechat
	 */
	private String client;
	/**
	 * 12.转让时间（eg.2017-07-11 17:08:07）
	 */
	private String addTime;
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getCreditId() {
		return creditId;
	}
	public void setCreditId(String creditId) {
		this.creditId = creditId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCreditNid() {
		return creditNid;
	}
	public void setCreditNid(String creditNid) {
		this.creditNid = creditNid;
	}
	public String getBidNid() {
		return bidNid;
	}
	public void setBidNid(String bidNid) {
		this.bidNid = bidNid;
	}
	public String getCreditCapital() {
		return creditCapital;
	}
	public void setCreditCapital(String creditCapital) {
		this.creditCapital = creditCapital;
	}
	public String getCreditCapitalPrice() {
		return creditCapitalPrice;
	}
	public void setCreditCapitalPrice(String creditCapitalPrice) {
		this.creditCapitalPrice = creditCapitalPrice;
	}
	public String getCreditDiscount() {
		return creditDiscount;
	}
	public void setCreditDiscount(String creditDiscount) {
		this.creditDiscount = creditDiscount;
	}
	public String getCreditPrice() {
		return creditPrice;
	}
	public void setCreditPrice(String creditPrice) {
		this.creditPrice = creditPrice;
	}
	public String getCreditCapitalAssigned() {
		return creditCapitalAssigned;
	}
	public void setCreditCapitalAssigned(String creditCapitalAssigned) {
		this.creditCapitalAssigned = creditCapitalAssigned;
	}
	public String getCreditStatus() {
		return creditStatus;
	}
	public void setCreditStatus(String creditStatus) {
		this.creditStatus = creditStatus;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
}
