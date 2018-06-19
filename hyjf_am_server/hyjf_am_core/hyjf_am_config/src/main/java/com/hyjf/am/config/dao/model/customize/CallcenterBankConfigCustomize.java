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
	private Integer bankName;
	// 银行代号
	private Integer bankCode;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getBankName() {
		return bankName;
	}
	public void setBankName(Integer bankName) {
		this.bankName = bankName;
	}
	public Integer getBankCode() {
		return bankCode;
	}
	public void setBankCode(Integer bankCode) {
		this.bankCode = bankCode;
	}
}
