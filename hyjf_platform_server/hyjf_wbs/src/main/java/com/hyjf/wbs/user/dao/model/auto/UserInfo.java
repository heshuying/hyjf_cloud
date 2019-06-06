package com.hyjf.wbs.user.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class UserInfo implements Serializable {
    /**
     * 用户id
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 用户角色1出借人2借款人3担保机构
     *
     * @mbggenerated
     */
    private Integer roleId;

    /**
     * 生日
     *
     * @mbggenerated
     */
    private String birthday;

    /**
     * 性别:0未知,1男,2女
     *
     * @mbggenerated
     */
    private Integer sex;

    /**
     * 真实姓名
     *
     * @mbggenerated
     */
    private String truename;

    /**
     * 身份证号
     *
     * @mbggenerated
     */
    private String idcard;

    /**
     * 实名是已否认证
     *
     * @mbggenerated
     */
    private Integer truenameIsapprove;

    /**
     * 手机是已否认证
     *
     * @mbggenerated
     */
    private Integer mobileIsapprove;

    /**
     * 邮件是已否认证
     *
     * @mbggenerated
     */
    private Integer emailIsapprove;

    /**
     * 省份
     *
     * @mbggenerated
     */
    private String province;

    /**
     * 城市
     *
     * @mbggenerated
     */
    private String city;

    /**
     * 区域
     *
     * @mbggenerated
     */
    private String area;

    /**
     * 地址
     *
     * @mbggenerated
     */
    private String address;

    /**
     * 用户属性 0=>无主单 1=>有主单 2=>线下员工 3=>线上员工
     *
     * @mbggenerated
     */
    private Integer attribute;

    private Integer isContact;

    /**
     * 借款人类型 1：内部机构 2：外部机构
     *
     * @mbggenerated
     */
    private Integer borrowerType;

    /**
     * 更新时间
     *
     * @mbggenerated
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday == null ? null : birthday.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename == null ? null : truename.trim();
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard == null ? null : idcard.trim();
    }

    public Integer getTruenameIsapprove() {
        return truenameIsapprove;
    }

    public void setTruenameIsapprove(Integer truenameIsapprove) {
        this.truenameIsapprove = truenameIsapprove;
    }

    public Integer getMobileIsapprove() {
        return mobileIsapprove;
    }

    public void setMobileIsapprove(Integer mobileIsapprove) {
        this.mobileIsapprove = mobileIsapprove;
    }

    public Integer getEmailIsapprove() {
        return emailIsapprove;
    }

    public void setEmailIsapprove(Integer emailIsapprove) {
        this.emailIsapprove = emailIsapprove;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Integer getAttribute() {
        return attribute;
    }

    public void setAttribute(Integer attribute) {
        this.attribute = attribute;
    }

    public Integer getIsContact() {
        return isContact;
    }

    public void setIsContact(Integer isContact) {
        this.isContact = isContact;
    }

    public Integer getBorrowerType() {
        return borrowerType;
    }

    public void setBorrowerType(Integer borrowerType) {
        this.borrowerType = borrowerType;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}