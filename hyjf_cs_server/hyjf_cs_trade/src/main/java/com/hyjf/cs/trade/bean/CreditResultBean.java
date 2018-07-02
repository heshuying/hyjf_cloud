package com.hyjf.cs.trade.bean;

import com.hyjf.am.vo.trade.ExpectCreditFeeVO;
import com.hyjf.am.vo.trade.TenderCreditCustomizeVO;

import java.io.Serializable;

public class CreditResultBean implements Serializable
{
	private static final long serialVersionUID = -1229013393923143695L;

	private String resultFlag;
	private String msg;
	private TenderCreditCustomizeVO data;
	private String mobile;
	private ExpectCreditFeeVO calData;


	public String getResultFlag() {
		return resultFlag;
	}

	public void setResultFlag(String resultFlag) {
		this.resultFlag = resultFlag;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public TenderCreditCustomizeVO getData() {
		return data;
	}

	public void setData(TenderCreditCustomizeVO data) {
		this.data = data;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public ExpectCreditFeeVO getCalData() {
		return calData;
	}

	public void setCalData(ExpectCreditFeeVO calData) {
		this.calData = calData;
	}

}
