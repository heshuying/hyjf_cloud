package com.hyjf.am.trade.bean;

import java.math.BigDecimal;

/**
 * @author xiasq
 * @version TzjDayReportBean, v0.1 2018/7/9 9:33
 */
public class TzjDayReportBean {
    /** 每日充值人数 */
    private int rechargeCount;
    /** 每日投资人数 */
    private int tenderCount;
    /** 每日首投人数 */
    private int tenderFirstCount;
    /** 每日复投人数 */
    private int tenderAgainCount;
    /** 每日投资金额 */
    private BigDecimal tenderMoney;
    /** 每日首投金额  */
    private BigDecimal tenderFirstMoney;
    /** 每日新充人数 */
    private int rechargeNewCount;
    /** 每日新投人数 - 新用户，当天注册 */
    private int tenderNewCount;

    public int getRechargeCount() {
        return rechargeCount;
    }

    public void setRechargeCount(int rechargeCount) {
        this.rechargeCount = rechargeCount;
    }

    public int getTenderCount() {
        return tenderCount;
    }

    public void setTenderCount(int tenderCount) {
        this.tenderCount = tenderCount;
    }

    public int getTenderFirstCount() {
        return tenderFirstCount;
    }

    public void setTenderFirstCount(int tenderFirstCount) {
        this.tenderFirstCount = tenderFirstCount;
    }

    public int getTenderAgainCount() {
        return tenderAgainCount;
    }

    public void setTenderAgainCount(int tenderAgainCount) {
        this.tenderAgainCount = tenderAgainCount;
    }

    public BigDecimal getTenderMoney() {
        return tenderMoney;
    }

    public void setTenderMoney(BigDecimal tenderMoney) {
        this.tenderMoney = tenderMoney;
    }

    public BigDecimal getTenderFirstMoney() {
        return tenderFirstMoney;
    }

    public void setTenderFirstMoney(BigDecimal tenderFirstMoney) {
        this.tenderFirstMoney = tenderFirstMoney;
    }

    public int getRechargeNewCount() {
        return rechargeNewCount;
    }

    public void setRechargeNewCount(int rechargeNewCount) {
        this.rechargeNewCount = rechargeNewCount;
    }

    public int getTenderNewCount() {
        return tenderNewCount;
    }

    public void setTenderNewCount(int tenderNewCount) {
        this.tenderNewCount = tenderNewCount;
    }
}
