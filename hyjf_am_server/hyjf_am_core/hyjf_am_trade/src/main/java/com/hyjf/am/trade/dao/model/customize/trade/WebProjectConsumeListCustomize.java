package com.hyjf.am.trade.dao.model.customize.trade;

/**
 * @author jijun
 * @date 20180624
 */

public class WebProjectConsumeListCustomize {

	public String id;
	// 用户名
	public String personName;
	// 融资金额
	public String account;
	// 用户身份证号
	public String idCard;

	/**
	 * 构造方法
	 */

	public WebProjectConsumeListCustomize() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

}
