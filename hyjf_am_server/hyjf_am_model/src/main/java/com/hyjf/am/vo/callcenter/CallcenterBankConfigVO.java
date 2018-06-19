package com.hyjf.am.vo.callcenter;

import java.io.Serializable;

import com.hyjf.am.vo.BaseVO;

/**
 * @author libin
 * @version CallcenterBankConfigVO, v0.1 2018/6/16 17:22
 */
public class CallcenterBankConfigVO extends BaseVO implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	// 主键
	private Integer id;
    // 银行代码
    private String bankCode;		
    // 银行名称
    private String bankName;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	} 
}
