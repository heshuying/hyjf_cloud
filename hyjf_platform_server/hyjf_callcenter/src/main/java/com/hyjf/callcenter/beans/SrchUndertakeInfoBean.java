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
public class SrchUndertakeInfoBean implements Serializable {

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
	 * 序号（暂时）
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
	 * 3.承接订单号
	 */
	private String assignNid;
	/**
	 * 4.债转编号
	 */
	private String creditNid;
	/**
	 * 5.出让人
	 */
	private String creditUsername;
	/**
	 * 6.原始项目编号
	 */
	private String bidNid;
	/**
	 * 7.承接本金（admin转让本金）
	 */
	private String assignCapital;
	/**
	 * 8.折让率
	 */
	private String creditDiscount;
	/**
	 * 9.认购价格（admin认购金额）
	 */
	private String assignPrice;
	/**
	 * 10.垫付利息
	 */
	private String assignInterestAdvance;
	/**
	 * 11.服务费
	 */
	private String creditFee;
	/**
	 * 12.承接平台：客户端 0pc 1ios 2Android 3微信
	 */
	private String client;
	/**
	 * 13.承接时间(发布时间)
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
	public String getAssignNid() {
		return assignNid;
	}
	public void setAssignNid(String assignNid) {
		this.assignNid = assignNid;
	}
	public String getCreditNid() {
		return creditNid;
	}
	public void setCreditNid(String creditNid) {
		this.creditNid = creditNid;
	}
	public String getCreditUsername() {
		return creditUsername;
	}
	public void setCreditUsername(String creditUsername) {
		this.creditUsername = creditUsername;
	}
	public String getBidNid() {
		return bidNid;
	}
	public void setBidNid(String bidNid) {
		this.bidNid = bidNid;
	}
	public String getAssignCapital() {
		return assignCapital;
	}
	public void setAssignCapital(String assignCapital) {
		this.assignCapital = assignCapital;
	}
	public String getCreditDiscount() {
		return creditDiscount;
	}
	public void setCreditDiscount(String creditDiscount) {
		this.creditDiscount = creditDiscount;
	}
	public String getAssignPrice() {
		return assignPrice;
	}
	public void setAssignPrice(String assignPrice) {
		this.assignPrice = assignPrice;
	}
	public String getAssignInterestAdvance() {
		return assignInterestAdvance;
	}
	public void setAssignInterestAdvance(String assignInterestAdvance) {
		this.assignInterestAdvance = assignInterestAdvance;
	}
	public String getCreditFee() {
		return creditFee;
	}
	public void setCreditFee(String creditFee) {
		this.creditFee = creditFee;
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
