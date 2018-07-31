package com.hyjf.cs.trade.vo;

/**
 * @author pangchengchao
 * @version BankWithdrawVO, v0.1 2018/7/30 19:16
 */
public class BankWithdrawVO {

    private String widCard;
    private String withdrawmoney;
    private String payAllianceCode;

    public String getWidCard() {
        return widCard;
    }

    public void setWidCard(String widCard) {
        this.widCard = widCard;
    }

    public String getWithdrawmoney() {
        return withdrawmoney;
    }

    public void setWithdrawmoney(String withdrawmoney) {
        this.withdrawmoney = withdrawmoney;
    }

    public String getPayAllianceCode() {
        return payAllianceCode;
    }

    public void setPayAllianceCode(String payAllianceCode) {
        this.payAllianceCode = payAllianceCode;
    }
}
