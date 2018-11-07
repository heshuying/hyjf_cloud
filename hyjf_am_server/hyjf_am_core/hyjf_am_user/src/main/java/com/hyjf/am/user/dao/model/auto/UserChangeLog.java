package com.hyjf.am.user.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class UserChangeLog implements Serializable {
    /**
     * 主键
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 用户id
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 用户登录名
     *
     * @mbggenerated
     */
    private String username;

    /**
     * 真实姓名
     *
     * @mbggenerated
     */
    private String realName;

    /**
     * 手机号
     *
     * @mbggenerated
     */
    private String mobile;

    /**
     * 身份证号
     *
     * @mbggenerated
     */
    private String idcard;

    /**
     * 用户角色1投资人2借款人
     *
     * @mbggenerated
     */
    private Integer role;

    /**
     * 用户属性 0=>无主单 1=>有主单 2=>线下员工 3=>线上员工
     *
     * @mbggenerated
     */
    private Integer attribute;

    /**
     * 推荐人
     *
     * @mbggenerated
     */
    private String recommendUser;

    /**
     * 是否51老用户，1：是
     *
     * @mbggenerated
     */
    private Integer is51;

    /**
     * 用户是否锁定,0未锁定,1锁定
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 修改说明
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * 借款人类型 1：内部机构 2：外部机构
     *
     * @mbggenerated
     */
    private Integer borrowerType;

    /**
     * 修改类型 2用户信息修改  1推荐人修改
     *
     * @mbggenerated
     */
    private Integer updateType;

    /**
     * 修改人
     *
     * @mbggenerated
     */
    private Integer updateUserId;

    /**
     * 修改人名字
     *
     * @mbggenerated
     */
    private String updateUser;

    /**
     * 更新时间
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * 邮箱
     *
     * @mbggenerated
     */
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
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }
}