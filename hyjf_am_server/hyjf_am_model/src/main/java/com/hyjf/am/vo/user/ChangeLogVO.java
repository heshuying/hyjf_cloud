package com.hyjf.am.vo.user;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

public class ChangeLogVO extends BaseVO implements Serializable {

    private Integer id;

    private Integer userId;

    private String username;

    private String realName;

    private String mobile;

    private Integer role;

    private String attribute;

    private String recommendUser;

    private Integer is51;

    private Integer status;

    private Integer changeUserid;

    private String changeUser;
    
    private Integer changeType;

    private String changeTime;

    private String remark;
    
    private String borrowerType;
    
    private String idCard;
    //合规四期(添加邮箱) add by nxl
    private String email;

    //注册渠道
    private String utmName;

    private static final long serialVersionUID = 1L;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getRecommendUser() {
        return recommendUser;
    }

    public void setRecommendUser(String recommendUser) {
        this.recommendUser = recommendUser == null ? null : recommendUser.trim();
    }

    public Integer getIs51() {
        return is51;
    }

    public void setIs51(Integer is51) {
        this.is51 = is51;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getChangeUserid() {
        return changeUserid;
    }

    public void setChangeUserid(Integer changeUserid) {
        this.changeUserid = changeUserid;
    }

    public String getChangeUser() {
        return changeUser;
    }

    public void setChangeUser(String changeUser) {
        this.changeUser = changeUser == null ? null : changeUser.trim();
    }

    public String getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(String changeTime) {
        this.changeTime = changeTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getChangeType() {
        return changeType;
    }

    public void setChangeType(Integer changeType) {
        this.changeType = changeType;
    }

    public String getBorrowerType() {
        return borrowerType;
    }

    public void setBorrowerType(String borrowerType) {
        this.borrowerType = borrowerType;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUtmName() {
        return utmName;
    }

    public void setUtmName(String utmName) {
        this.utmName = utmName;
    }
}




	