package com.hyjf.cs.trade.vo;

/**
 * @author pangchengchao
 * @version BankWithdrawVO, v0.1 2018/7/30 19:16
 */
public class BankWithdrawVO {
    // 提现银行卡号
    private String widCard;
    // 交易金额
    private String withdrawmoney;
    // 银联行号
    private String payAllianceCode;

    //订单号
    private String logOrdId;

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

    public String getLogOrdId() {
        return logOrdId;
    }

    public void setLogOrdId(String logOrdId) {
        this.logOrdId = logOrdId;
    }
}
