package com.hyjf.cs.user.bean;

public class ApiBankOpenRequestBean extends BaseBean{

    // 手机号
    private String mobile;
    // 姓名
    private String trueName;
    // 身份证号
    private String idNo;
    // 性别
    private  String gender;
    // 身份属性  1：出借角色 2：借款角色 3：代偿角色
    private  String identity;
    /*00000-普通账户
    10000-红包账户（只能有一个）
    01000-手续费账户（只能有一个）
    00100-担保账户*/
    private  String acctUse;
    // 商户名称
    private  String coinstName;

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

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
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
