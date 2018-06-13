package com.hyjf.am.user.dao.model.customize.callcenter;

import java.io.Serializable;

/**
 * 同步另外实体类AdminUserListCustomize
 * @author libin
 */
public class CallcenterUserBaseCustomize implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	//用戶id
    public String userId;
    //用戶名
    public String userName;
    //真实姓名
    public String realName;
    //用户手机号
    public String mobile;
    //vip类型
    public String vipType;
    //用戶角色
    public String userRole;
    //用户类型
    public String userType;
    //用戶属性
    public String userProperty;
    //推荐人名称
    public String recommendName;
    //是否51老用户
    public String is51;
    //开户状态
    public String accountStatus;
    //用户状态
    public String userStatus;
    //注册平台
    public String registPlat;
    //注册时间
    public String regTime;
    //借款人类型
    public String borrowerType;
    // 性别
	public String sex;
	// 生日
	public String birthday;
	// 注册所在地
	public String registArea;
	// 身份证号
	public String idcard;
    private String openAccount;
    
    private String bankOpenAccount;
    
    private String bankOpenTime;
    //江西银行电子账号
    private String bankAccount;
    
	public String getBorrowerType() {
		return borrowerType;
	}

	public void setBorrowerType(String borrowerType) {
		this.borrowerType = borrowerType;
	}

	/** 大区 */
	private String regionName;
	/** 分公司 */
	private String branchName;
	/** 部门 */
	private String departmentName;
	/** 部门 */
	private String combotreeSrch;
	/** 部门 */
	private String[] combotreeListSrch;

	/**
	 * 构造方法不带参数
	 */

	public CallcenterUserBaseCustomize() {
		super();
	}

	/**
	 * 获取用户id userId
	 * 
	 * @return the userId
	 */

	public String getUserId() {
		return userId;
	}

	/**
	 * 设置用户id
	 * 
	 * @param userId
	 *            the userId to set
	 */

	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 获取用户名 userName
	 * 
	 * @return the userName
	 */

	public String getUserName() {
		return userName;
	}

	/**
	 * 设置用户名
	 * 
	 * @param userName
	 *            the userName to set
	 */

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 获取用户角色 userRole
	 * 
	 * @return the userRole
	 */

	public String getUserRole() {
		return userRole;
	}

	/**
	 * 设置用户角色
	 * 
	 * @param userRole
	 *            the userRole to set
	 */

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	/**
	 * 获取用户属性 userProperty
	 * 
	 * @return the userProperty
	 */

	public String getUserProperty() {
		return userProperty;
	}

	/**
	 * 设置用户属性
	 * 
	 * @param userProperty
	 *            the userProperty to set
	 */

	public void setUserProperty(String userProperty) {
		this.userProperty = userProperty;
	}

	/**
	 * 获取推荐人 recommendName
	 * 
	 * @return the recommendName
	 */

	public String getRecommendName() {
		return recommendName;
	}

	/**
	 * 设置推荐人
	 * 
	 * @param recommendName
	 *            the recommendName to set
	 */

	public void setRecommendName(String recommendName) {
		this.recommendName = recommendName;
	}

	/**
	 * accountStatus
	 * 
	 * @return the accountStatus
	 */

	public String getAccountStatus() {
		return accountStatus;
	}

	/**
	 * @param accountStatus
	 *            the accountStatus to set
	 */

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	/**
	 * registPlat
	 * 
	 * @return the registPlat
	 */

	public String getRegistPlat() {
		return registPlat;
	}

	/**
	 * @param registPlat
	 *            the registPlat to set
	 */

	public void setRegistPlat(String registPlat) {
		this.registPlat = registPlat;
	}

	/**
	 * 获取用户注册时间 regTime
	 * 
	 * @return the regTime
	 */

	public String getRegTime() {
		return regTime;
	}

	/**
	 * 设置注册时间
	 * 
	 * @param regTime
	 *            the regTime to set
	 */

	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}

	/**
	 * is51
	 * 
	 * @return the is51
	 */

	public String getIs51() {
		return is51;
	}

	/**
	 * @param is51
	 *            the is51 to set
	 */

	public void setIs51(String is51) {
		this.is51 = is51;
	}

	/**
	 * userStatus
	 * 
	 * @return the userStatus
	 */

	public String getUserStatus() {
		return userStatus;
	}

	/**
	 * @param userStatus
	 *            the userStatus to set
	 */

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	/**
	 * realName
	 * 
	 * @return the realName
	 */

	public String getRealName() {
		return realName;
	}

	/**
	 * @param realName
	 *            the realName to set
	 */

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * regionName
	 * 
	 * @return the regionName
	 */

	public String getRegionName() {
		return regionName;
	}

	/**
	 * @param regionName
	 *            the regionName to set
	 */

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	/**
	 * branchName
	 * 
	 * @return the branchName
	 */

	public String getBranchName() {
		return branchName;
	}

	/**
	 * @param branchName
	 *            the branchName to set
	 */

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	/**
	 * departmentName
	 * 
	 * @return the departmentName
	 */

	public String getDepartmentName() {
		return departmentName;
	}

	/**
	 * @param departmentName
	 *            the departmentName to set
	 */

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	/**
	 * combotreeSrch
	 * 
	 * @return the combotreeSrch
	 */

	public String getCombotreeSrch() {
		return combotreeSrch;
	}

	/**
	 * @param combotreeSrch
	 *            the combotreeSrch to set
	 */

	public void setCombotreeSrch(String combotreeSrch) {
		this.combotreeSrch = combotreeSrch;
	}

	/**
	 * combotreeListSrch
	 * 
	 * @return the combotreeListSrch
	 */

	public String[] getCombotreeListSrch() {
		return combotreeListSrch;
	}

	/**
	 * @param combotreeListSrch
	 *            the combotreeListSrch to set
	 */

	public void setCombotreeListSrch(String[] combotreeListSrch) {
		this.combotreeListSrch = combotreeListSrch;
	}

	public String getVipType() {
		return vipType;
	}

	public void setVipType(String vipType) {
		this.vipType = vipType;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

    public String getBankOpenAccount() {
        return bankOpenAccount;
    }

    public void setBankOpenAccount(String bankOpenAccount) {
        this.bankOpenAccount = bankOpenAccount;
    }

    public String getBankOpenTime() {
        return bankOpenTime;
    }

    public void setBankOpenTime(String bankOpenTime) {
        this.bankOpenTime = bankOpenTime;
    }

    public String getOpenAccount() {
        return openAccount;
    }

    public void setOpenAccount(String openAccount) {
        this.openAccount = openAccount;
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

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

}

