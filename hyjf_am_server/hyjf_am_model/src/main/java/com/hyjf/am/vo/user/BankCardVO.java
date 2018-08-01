package com.hyjf.am.vo.user;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户绑定的银行卡VO
 * @author hesy
 */
public class BankCardVO extends BaseVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

    private Integer userId;

    private String userName;

    private String cardNo;

    private String cardNoNotEncrypt;

    private Integer bankId;

    private String mobile;

    private String payAllianceCode;

    private String bank;

    private Integer status;

    private Date createTime;

    private Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Integer getBankId() {
		return bankId;
	}

	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPayAllianceCode() {
		return payAllianceCode;
	}

	public void setPayAllianceCode(String payAllianceCode) {
		this.payAllianceCode = payAllianceCode;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getCardNoNotEncrypt() {
		return cardNoNotEncrypt;
	}

	public void setCardNoNotEncrypt(String cardNoNotEncrypt) {
		this.cardNoNotEncrypt = cardNoNotEncrypt;
	}
}
