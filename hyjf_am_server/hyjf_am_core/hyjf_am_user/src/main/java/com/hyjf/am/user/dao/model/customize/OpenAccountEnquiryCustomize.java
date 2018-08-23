package com.hyjf.am.user.dao.model.customize;

import java.io.Serializable;

/**
 * @version OpenAccountEnquiryCustomize, v0.1 2018/8/21 14:59
 * @Author: Zha Daojian
 */
public class OpenAccountEnquiryCustomize implements Serializable {
    private static final long serialVersionUID = 1L;

    // 用戶id
    public String userid;
    // 用户手机号
    public String phone;
    // 用户身份证号
    public String idcard;
    // 开户状态
    public String accountStatus;
    // 开户时间
    public String regTimeEnd;
    //电子账号
    public  String accountString;
    //开户平台
    public int platform;
    //用户姓名
    public String username;
    //用户手机号码
    public String mobile;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getRegTimeEnd() {
        return regTimeEnd;
    }

    public void setRegTimeEnd(String regTimeEnd) {
        this.regTimeEnd = regTimeEnd;
    }

    public String getAccountString() {
        return accountString;
    }

    public void setAccountString(String accountString) {
        this.accountString = accountString;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
