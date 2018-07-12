package com.hyjf.am.user.dao.model.customize;

/**
 * 查询用户信息（更新用户保存log用）
 * @author nxl
 */

public class UserInfoForLogCustomize {
    
    //用户id
    private Integer userId;
    
    private String userName;
    
    private String realName;
    
    private String mobile;
    
    private Integer userRole;
    
    private Integer attribute;
    
    private Integer openAccount;
    
    private Integer is51;
    
    private Integer userStatus;
    
    private String recommendName;
    
    private Integer recommendUserId;
    
    private Integer regTime;
    
    private Integer bankOpenAccount;
    
    private String idCard;
    
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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getUserRole() {
        return userRole;
    }

    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }

    public Integer getAttribute() {
        return attribute;
    }

    public void setAttribute(Integer attribute) {
        this.attribute = attribute;
    }

    public Integer getOpenAccount() {
        return openAccount;
    }

    public void setOpenAccount(Integer openAccount) {
        this.openAccount = openAccount;
    }

    public Integer getIs51() {
        return is51;
    }

    public void setIs51(Integer is51) {
        this.is51 = is51;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public String getRecommendName() {
        return recommendName;
    }

    public void setRecommendName(String recommendName) {
        this.recommendName = recommendName;
    }

    public Integer getRecommendUserId() {
        return recommendUserId;
    }

    public void setRecommendUserId(Integer recommendUserId) {
        this.recommendUserId = recommendUserId;
    }

    public Integer getRegTime() {
        return regTime;
    }

    public void setRegTime(Integer regTime) {
        this.regTime = regTime;
    }

    public Integer getBankOpenAccount() {
        return bankOpenAccount;
    }

    public void setBankOpenAccount(Integer bankOpenAccount) {
        this.bankOpenAccount = bankOpenAccount;
    }

	/**
	 * idCard
	 * @return the idCard
	 */
		
	public String getIdCard() {
		return idCard;
			
	}

	/**
	 * @param idCard the idCard to set
	 */
		
	public void setIdCard(String idCard) {
		this.idCard = idCard;
			
	}

    
}

    