/**
 * Description:用户开户列表前端显示查询所用po
 * Copyright: Copyright (HYJF Corporation) 2015
 * Company: HYJF Corporation
 * @author: 王坤
 * @version: 1.0
 * Created at: 2015年11月11日 下午2:17:31
 * Modification History:
 * Modified by : 
 */

package com.hyjf.am.trade.dao.model.customize.admin;

/**
 * @author 王坤
 */

public class AdminAccountListCustomize {

	// 用戶id
	public String userId;
	// 用户名
	public String userName;
	//用户手机号
	private String mobile;
	/** 用户真实姓名 */
	public String realName;
	/** 用户身份证号码 */
	public String idCard;
	// 用户属性
	public String userProperty;
	// 汇付帐号
	public String account;
	// 汇付客户号
	public String customerAccount;
	// 开户状态
	public String accountStatus;
	// 开户状态
	public String accountStatusName;
	// 开户平台
	public String openAccountPlat;
	// 开户时间
	public String openTime;
	// 性别
	public String sex;
	// 生日
	public String birthday;
	// 注册所在地
	public String registArea;
	// 身份证号
	public String idcard;

	/**
	 * 构造方法
	 */

	public AdminAccountListCustomize() {
		super();
	}

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
	 * openAccount
	 * 
	 * @return the openAccount
	 */

	public String getAccountStatus() {
		return accountStatus;
	}

	/**
	 * @param openAccount
	 *            the openAccount to set
	 */

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	/**
	 * openTime
	 * 
	 * @return the openTime
	 */

	public String getOpenTime() {
		return openTime;
	}

	/**
	 * @param openTime
	 *            the openTime to set
	 */

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	/**
	 * openAccountPlat
	 * 
	 * @return the openAccountPlat
	 */

	public String getOpenAccountPlat() {
		return openAccountPlat;
	}

	/**
	 * @param openAccountPlat
	 *            the openAccountPlat to set
	 */

	public void setOpenAccountPlat(String openAccountPlat) {
		this.openAccountPlat = openAccountPlat;
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

	public String getAccountStatusName() {
		return accountStatusName;
	}

	public void setAccountStatusName(String accountStatusName) {
		this.accountStatusName = accountStatusName;
	}

	public String getCustomerAccount() {
		return customerAccount;
	}

	public void setCustomerAccount(String customerAccount) {
		this.customerAccount = customerAccount;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}


    public String getMobile() {
        return mobile;
    }


    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

	public String getUserProperty() {
		return userProperty;
	}

	public void setUserProperty(String userProperty) {
		this.userProperty = userProperty;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getRegistArea() {
		return registArea;
	}

	public void setRegistArea(String registArea) {
		this.registArea = registArea;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

}
