/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.bean;

/**
 * AEMS系统:用户开户请求Bean
 *
 * @author liuyang
 * @version AemsBankOpenEncryptPageRequestBean, v0.1 2018/12/10 11:12
 */
public class AemsBankOpenEncryptPageRequestBean extends BaseBean {

    // 手机号
    private String mobile;
    // 姓名
    private String trueName;
    // 性别
    private String gender;
    // 身份属性  1：出借角色 2：借款角色 3：代偿角色
    private String identity;
    /*00000-普通账户
    10000-红包账户（只能有一个）
    01000-手续费账户（只能有一个）
    00100-担保账户*/
    private String acctUse;
    // 商户名称
    private String coinstName;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getAcctUse() {
        return acctUse;
    }

    public void setAcctUse(String acctUse) {
        this.acctUse = acctUse;
    }

    public String getCoinstName() {
        return coinstName;
    }

    public void setCoinstName(String coinstName) {
        this.coinstName = coinstName;
    }
}
