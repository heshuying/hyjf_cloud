/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.dao.model.customize;

/**
 * @author nxl
 * @version UserBankOpenAccountCustomize, v0.1 2018/6/21 21:38
 */
public class UserBankOpenAccountCustomize {
    //用户银行账户
    private String account;
    //用户开户平台
    private String openAccountPlat;
    //用户开户时间
    private String openAccountTime;
    //用户开户类型
    private String userType;
    //用户预留手机号
    private String mobile;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getOpenAccountPlat() {
        return openAccountPlat;
    }

    public void setOpenAccountPlat(String openAccountPlat) {
        this.openAccountPlat = openAccountPlat;
    }

    public String getOpenAccountTime() {
        return openAccountTime;
    }

    public void setOpenAccountTime(String openAccountTime) {
        this.openAccountTime = openAccountTime;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
