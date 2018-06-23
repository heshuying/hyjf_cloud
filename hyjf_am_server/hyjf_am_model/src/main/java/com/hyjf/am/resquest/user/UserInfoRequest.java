package com.hyjf.am.resquest.user;

import java.util.Date;

/**
 * @author nxl
 * @version UserInfoRequest, v0.1 2018/6/23 11:07
 */
public class UserInfoRequest {

    private Integer userId;

    private Integer roleId;

    private String birthday;

    private Integer sex;

    private String truename;

    private String idcard;

    private Integer truenameIsapprove;

    private Integer mobileIsapprove;

    private Integer emailIsapprove;

    private String province;

    private String city;

    private String area;

    private String address;

    private Integer attribute;

    private Integer isContact;

    private Integer borrowerType;

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
