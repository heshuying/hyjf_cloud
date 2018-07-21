/**
 * Description:银行卡绑定列表前端显示所用PO
 * Copyright: Copyright (HYJF Corporation) 2015
 * Company: HYJF Corporation
 * @author: 王坤
 * @version: 1.0
 * Created at: 2015年11月11日 下午2:17:31
 * Modification History:
 * Modified by : 
 */

package com.hyjf.admin.beans.vo;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author nxl
 */

public class BankcardManagerCustomizeVO extends BaseVO {

	// 用戶id
	@ApiModelProperty(value = "用戶id")
	public String userId;
	// 用戶名
	@ApiModelProperty(value = "用戶名")
	public String userName;

    //用户手机号
	@ApiModelProperty(value = "用户手机号")
	private String mobile;
    /** 用户真实姓名 */
	@ApiModelProperty(value = "用户真实姓名")
	private String realName;
    /** 身份证号 */
	@ApiModelProperty(value = "身份证号")
	private String idcard;
    /** 银行预留手机号 */
	@ApiModelProperty(value = "银行预留手机号")
	private String bankMobile;
    
	// 银行账户
	@ApiModelProperty(value = "银行账户")
	public String account;
	/**专门添加为了调用汇付接口,从而核对银行卡信息*/
	//汇付客户号
	@ApiModelProperty(value = "汇付客户号")
	public String customerAccount;
	// 银行
	@ApiModelProperty(value = "银行")
	public String bank;
	// 银行卡是否是默认提现卡
	@ApiModelProperty(value = "银行卡是否是默认提现卡")
	public String cardType;
	// 银行卡是否是快捷支付卡
	@ApiModelProperty(value = "银行卡是否是快捷支付卡")
	public String cardProperty;
	// 添加时间
	@ApiModelProperty(value = "添加时间")
	public String addTime;

	/**
	 * userId
	 * 
	 * @return the userId
	 */

	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */

	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * userName
	 * 
	 * @return the userName
	 */

	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * account
	 * 
	 * @return the account
	 */

	public String getAccount() {
		return account;
	}

	/**
	 * @param account
	 *            the account to set
	 */

	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * bank
	 * 
	 * @return the bank
	 */

	public String getBank() {
		return bank;
	}

	/**
	 * @param bank
	 *            the bank to set
	 */

	public void setBank(String bank) {
		this.bank = bank;
	}

	/**
	 * addTime
	 * 
	 * @return the addTime
	 */

	public String getAddTime() {
		return addTime;
	}

	/**
	 * @param addTime
	 *            the addTime to set
	 */

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	/**
	 * cardProperty
	 * @return the cardProperty
	 */
	
	public String getCardProperty() {
		return cardProperty;
	}

	/**
	 * @param cardProperty the cardProperty to set
	 */
	
	public void setCardProperty(String cardProperty) {
		this.cardProperty = cardProperty;
	}

	/**
	 * customerAccount
	 * @return the customerAccount
	 */
	
	public String getCustomerAccount() {
		return customerAccount;
	}

	/**
	 * @param customerAccount the customerAccount to set
	 */
	
	public void setCustomerAccount(String customerAccount) {
		this.customerAccount = customerAccount;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getBankMobile() {
        return bankMobile;
    }

    public void setBankMobile(String bankMobile) {
        this.bankMobile = bankMobile;
    }
}
