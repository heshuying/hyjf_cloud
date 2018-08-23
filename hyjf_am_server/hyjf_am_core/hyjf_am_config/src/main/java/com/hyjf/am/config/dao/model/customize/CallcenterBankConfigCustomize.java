package com.hyjf.am.config.dao.model.customize;

import java.io.Serializable;

/**
 * 呼叫中心在config自定义的model
 * @author libin
 */
public class CallcenterBankConfigCustomize implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	// 主键
	private Integer id;
	// 银行名称
	private String bankName;
	// 银行代号
	private String bankCode;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
}
