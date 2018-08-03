package com.hyjf.cs.trade.vo;

/**
 * @author pangchengchao
 * @version BankRechargeVO, v0.1 2018/7/30 18:17
 */
public class BankRechargeVO {
    //充值手机号
    private String mobile;
    //充值金额
    private String money;

    //订单号
    private String logOrdId;


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getLogOrdId() {
        return logOrdId;
    }

    public void setLogOrdId(String logOrdId) {
        this.logOrdId = logOrdId;
    }
}
