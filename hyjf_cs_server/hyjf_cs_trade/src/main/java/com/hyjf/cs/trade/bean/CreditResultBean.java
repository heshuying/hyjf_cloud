package com.hyjf.cs.trade.bean;

import com.hyjf.am.vo.config.DebtConfigVO;
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
	//债转新配置表
	private DebtConfigVO debtConfigVO;

	private Integer paymentAuthStatus;
	private Integer paymentAuthOn;
	private String isCheckUserRole;
	private String roleId;

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

	public DebtConfigVO getDebtConfigVO() {
		return debtConfigVO;
	}

	public void setDebtConfigVO(DebtConfigVO debtConfigVO) {
		this.debtConfigVO = debtConfigVO;
	}

	public Integer getPaymentAuthStatus() {
		return paymentAuthStatus;
	}

	public void setPaymentAuthStatus(Integer paymentAuthStatus) {
		this.paymentAuthStatus = paymentAuthStatus;
	}

	public Integer getPaymentAuthOn() {
		return paymentAuthOn;
	}

	public void setPaymentAuthOn(Integer paymentAuthOn) {
		this.paymentAuthOn = paymentAuthOn;
	}

	public String getIsCheckUserRole() {
		return isCheckUserRole;
	}

	public void setIsCheckUserRole(String isCheckUserRole) {
		this.isCheckUserRole = isCheckUserRole;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
}
