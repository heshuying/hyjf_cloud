/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.wbs.qvo;

/**
 * 快速授权页面客户信息
 * @author cui
 * @version WbsUserAuthInfo, v0.1 2019/4/19 9:54
 */
public class WbsUserAuthInfo {

    //用户名
    private String userName;

    //联系电话
    private String mobile;

    //注册时间 2019年11月12日 19:39
    private String registeDatetime;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRegisteDatetime() {
        return registeDatetime;
    }

    public void setRegisteDatetime(String registeDatetime) {
        this.registeDatetime = registeDatetime;
    }
}
