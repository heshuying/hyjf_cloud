package com.hyjf.admin.beans;

import java.io.Serializable;

/**
 * 债权人债权信息bean
 * @author zhangyk
 * @date 2018/7/13 18:03
 */
public class CreditUserInfoBean implements Serializable {

    /*标的号*/
    private String productId;

    /*投标日期*/
    private String buyDate;

    /*电子账户*/
    private String accountId;

    /*真实姓名*/
    private String name;

    /*投标金额*/
    private String txAmount;

    /*预期收益*/
    private String forIncome;

    /*状态1:投标中; 2:计息中; 4:本息已返回; 8:审核中; 9:已撤销*/
    private String state;

    /*授权码*/
    private String authCode;

    /*响应代码*/
    private String retCode;

    /*响应描述*/
    private String retMsg;


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(String buyDate) {
        this.buyDate = buyDate;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTxAmount() {
        return txAmount;
    }

    public void setTxAmount(String txAmount) {
        this.txAmount = txAmount;
    }

    public String getForIncome() {
        return forIncome;
    }

    public void setForIncome(String forIncome) {
        this.forIncome = forIncome;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }
}
