/**
 * Description:债权转让提交实体类
 * Copyright: Copyright (HYJF Corporation)2016
 * Company: HYJF Corporation
 * @author: Administrator
 * @version: 1.0
 * Created at: 2016年3月30日 上午11:24:10
 * Modification History:
 * Modified by : 
 */

package com.hyjf.cs.trade.bean;

/**
 * @author sunss
 */

public class TenderBorrowCreditCustomize extends CreditDetailsRequestBean{

	/**
	 * serialVersionUID:
	 */

	private static final long serialVersionUID = 1L;
	/**
	 * 折价率
	 */
	private String creditDiscount;
	/**
	 * 转让价格
	 */
	private String creditPrice;
	/**
	 * 服务费
	 */
	private String creditFee;
	/**
	 * 手机验证码
	 */
	private String telcode;
	/**
	 * 手机验证码 (和前端核对过  android 和ios都是传的code，但是老系统确实是用telcode接受的的，暂时问题原因不明，增加code字段)
	 */
	private String code;
	/**
	 * 持有天数
	 */
	private String tenderPeriod;

	private Integer platform;

	// 截止时间
	private Integer creditEndTime;

	// 转让本金
	private String creditCapital;

	private String ip;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getCreditFee() {
		return creditFee;
	}

	public void setCreditFee(String creditFee) {
		this.creditFee = creditFee;
	}

	public String getTelcode() {
		return telcode;
	}

	public void setTelcode(String telcode) {
		this.telcode = telcode;
	}

	@Override
    public String getTenderPeriod() {
		return tenderPeriod;
	}

	@Override
    public void setTenderPeriod(String tenderPeriod) {
		this.tenderPeriod = tenderPeriod;
	}

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}

	public Integer getCreditEndTime() {
		return creditEndTime;
	}

	public void setCreditEndTime(Integer creditEndTime) {
		this.creditEndTime = creditEndTime;
	}

	public String getCreditCapital() {
		return creditCapital;
	}

	public void setCreditCapital(String creditCapital) {
		this.creditCapital = creditCapital;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
}
