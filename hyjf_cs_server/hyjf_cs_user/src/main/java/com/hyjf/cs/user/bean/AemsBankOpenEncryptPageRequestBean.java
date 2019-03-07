/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.bean;

import java.util.LinkedHashMap;
import java.util.Map;

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

    // 返回信息
    private String returnMsg;

    // 用户是否开户
    private String isOpenAccount;
    // 电子账户号
    private String accountId;
    //银行卡联行号
    private String payAllianceCode;
    private String callBackAction;
    private String  acqRes;
    /**
     * 参数Map
     */
    private Map<String, String> paramMap = new LinkedHashMap<String, String>();


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

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public String getIsOpenAccount() {
        return isOpenAccount;
    }

    public void setIsOpenAccount(String isOpenAccount) {
        this.isOpenAccount = isOpenAccount;
    }

    @Override
    public String getAccountId() {
        return accountId;
    }

    @Override
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getPayAllianceCode() {
        return payAllianceCode;
    }

    public void setPayAllianceCode(String payAllianceCode) {
        this.payAllianceCode = payAllianceCode;
    }

    public String getCallBackAction() {
        return callBackAction;
    }

    public void setCallBackAction(String callBackAction) {
        this.callBackAction = callBackAction;
    }

    @Override
    public String getAcqRes() {
        return acqRes;
    }

    @Override
    public void setAcqRes(String acqRes) {
        this.acqRes = acqRes;
    }

    /**
     * 设置属性值
     *
     * @param key
     * @param value
     */
    public void set(String key, String value) {
        paramMap.put(key, value);
    }
    /**
     * 根据Key取得值
     *
     * @param key
     * @return
     */
    public String get(String key) {
        return paramMap.get(key);
    }
}
