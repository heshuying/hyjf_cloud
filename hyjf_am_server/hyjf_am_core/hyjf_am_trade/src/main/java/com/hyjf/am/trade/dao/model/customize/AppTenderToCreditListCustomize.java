package com.hyjf.am.trade.dao.model.customize;

import java.io.Serializable;

/**
 * @Description App债权转让--可转让列表
 * @Author pangchengchao
 * @Version v0.1
 * @Date
 */
public class AppTenderToCreditListCustomize implements Serializable {

    /**
     * 此处为属性说明
     */
    private static final long serialVersionUID = -3203050476725161683L;

    /***
     * 借款编号
     */
    private String bidNid;

    /**
     * 预期年化收益率
     */
    private String borrowApr;

    /**
     * 项目期限
     */
    private String borrowPeriod;

    /**
     * 可转让本金
     */
    private String creditAccount;

    /**
     * 剩余天数
     */
    private String lastDays;

    /**
     * 时间
     */
    private String recoverTime;

    private String tenderCreditUrl;

    public String getBidNid() {
        return bidNid;
    }

    public void setBidNid(String bidNid) {
        this.bidNid = bidNid;
    }

    public String getBorrowApr() {
        return borrowApr;
    }

    public void setBorrowApr(String borrowApr) {
        this.borrowApr = borrowApr;
    }

    public String getBorrowPeriod() {
        return borrowPeriod;
    }

    public void setBorrowPeriod(String borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }

    public String getCreditAccount() {
        return creditAccount;
    }

    public void setCreditAccount(String creditAccount) {
        this.creditAccount = creditAccount;
    }

    public String getLastDays() {
        return lastDays;
    }

    public void setLastDays(String lastDays) {
        this.lastDays = lastDays;
    }

    public String getTenderCreditUrl() {
        return tenderCreditUrl;
    }

    public void setTenderCreditUrl(String tenderCreditUrl) {
        this.tenderCreditUrl = tenderCreditUrl;
    }

    public String getRecoverTime() {
        return recoverTime;
    }

    public void setRecoverTime(String recoverTime) {
        this.recoverTime = recoverTime;
    }

}
