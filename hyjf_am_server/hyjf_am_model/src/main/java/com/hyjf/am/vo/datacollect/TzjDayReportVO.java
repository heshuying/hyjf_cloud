package com.hyjf.am.vo.datacollect;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xiasq
 * @version TZJDayReportVO, v0.1 2018/7/6 10:41 投之家日报
 */
public class TzjDayReportVO extends BaseVO implements Serializable {
    private static final long serialVersionUID = 9121172241814811165L;
    /** 当前日期 */
    private String day;
    /** 每日注册人数 */
    private int registCount;
    /** 每日开户人数 */
    private int openCount;
    /** 每日绑卡人数 */
    private int cardBindCount;
    /** 每日充值人数 */
    private int rechargeCount;
    /** 每日新充人数 */
    private int rechargeNewCount;

    /** 每日投资人数 */
	private int tenderCount;
    /** 每日新投人数 - 新用户，当天注册 */
	private int tenderNewCount;
    /** 每日首投人数 */
	private int tenderFirstCount;
    /** 每日复投人数 */
	private int tenderAgainCount;

    /** 每日投资金额 */
	private BigDecimal tenderMoney;
    /** 每日首投金额  */
	private BigDecimal tenderFirstMoney;

    public int getTenderCount() {
        return tenderCount;
    }

    public void setTenderCount(int tenderCount) {
        this.tenderCount = tenderCount;
    }

    public int getTenderNewCount() {
        return tenderNewCount;
    }

    public void setTenderNewCount(int tenderNewCount) {
        this.tenderNewCount = tenderNewCount;
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

    public int getRegistCount() {
        return registCount;
    }

    public void setRegistCount(int registCount) {
        this.registCount = registCount;
    }

    public int getOpenCount() {
        return openCount;
    }

    public void setOpenCount(int openCount) {
        this.openCount = openCount;
    }

    public int getCardBindCount() {
        return cardBindCount;
    }

    public void setCardBindCount(int cardBindCount) {
        this.cardBindCount = cardBindCount;
    }

    public int getRechargeCount() {
        return rechargeCount;
    }

    public void setRechargeCount(int rechargeCount) {
        this.rechargeCount = rechargeCount;
    }

    public int getRechargeNewCount() {
        return rechargeNewCount;
    }

    public void setRechargeNewCount(int rechargeNewCount) {
        this.rechargeNewCount = rechargeNewCount;
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

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return "TzjDayReportVO{" +
                "day='" + day + '\'' +
                ", registCount=" + registCount +
                ", openCount=" + openCount +
                ", cardBindCount=" + cardBindCount +
                ", rechargeCount=" + rechargeCount +
                ", rechargeNewCount=" + rechargeNewCount +
                ", tenderCount=" + tenderCount +
                ", tenderNewCount=" + tenderNewCount +
                ", tenderFirstCount=" + tenderFirstCount +
                ", tenderAgainCount=" + tenderAgainCount +
                ", tenderMoney=" + tenderMoney +
                ", tenderFirstMoney=" + tenderFirstMoney +
                '}';
    }
}
