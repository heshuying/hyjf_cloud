package com.hyjf.admin.beans.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class UserChangeLogCustomizeVO implements Serializable {
    private Integer id;
    @ApiModelProperty(value = "用戶id")
    private Integer userId;
    @ApiModelProperty(value = "用户登录名")
    private String username;
    @ApiModelProperty(value = "真实姓名")
    private String realName;
    @ApiModelProperty(value = "手机号")
    private String mobile;
    @ApiModelProperty(value = "身份证号")
    private String idcard;
    @ApiModelProperty(value = "用户角色 1 => 投资人 2 => 借款人 3 => 担保机构")
    private Integer role;
    @ApiModelProperty(value = "用户属性 0=>无主单 1=>有主单 2=>线下员工 3=>线上员工")
    private Integer attribute;
    @ApiModelProperty(value = "推荐人")
    private String recommendUser;
    @ApiModelProperty(value = "是否51老用户，1：是")
    private Integer is51;
    @ApiModelProperty(value = "用户是否锁定,0未锁定,1锁定")
    private Integer status;

    @ApiModelProperty(value = "修改说明")
    private String remark;
    @ApiModelProperty(value = "借款人类型 1：内部机构 2：外部机构")
    private Integer borrowerType;

    @ApiModelProperty(value = "修改类型 2用户信息修改  1推荐人修改")
    private Integer updateType;

    @ApiModelProperty(value = "修改人id")
    private Integer updateUserId;

    @ApiModelProperty(value = "修改人名字")
    private String updateUser;

    @ApiModelProperty(value = "更新时间")
    private String updateTime;
    @ApiModelProperty(value = "邮箱")
    private String email;

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

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard == null ? null : idcard.trim();
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getAttribute() {
        return attribute;
    }

    public void setAttribute(Integer attribute) {
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

    public Integer getUpdateType() {
        return updateType;
    }

    public void setUpdateType(Integer updateType) {
        this.updateType = updateType;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getBorrowerType() {
        return borrowerType;
    }

    public void setBorrowerType(Integer borrowerType) {
        this.borrowerType = borrowerType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}