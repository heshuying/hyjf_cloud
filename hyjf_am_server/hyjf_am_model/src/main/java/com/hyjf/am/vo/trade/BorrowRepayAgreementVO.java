package com.hyjf.am.vo.trade;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @version BorrowRepayAgreementVO, v0.1 2018/8/10 15:40
 * @Author: Zha Daojian
 */
public class BorrowRepayAgreementVO extends BaseVO implements Serializable {

    /**
     * 实际还款人(垫付机构)id
     */
    private String repayUserId;

    /**
     * 实际还款人(垫付机构)
     */
    private String repayUsername;

    /**
     * 借款编号
     */
    private String borrowNid;

    /**
     * nid
     */
    private String nid;
    /**
     * 还款状态
     */
    private String repayStatus;


    /**
     * 是否是分期
     */
    private boolean isMonth;
    /**
     * 是否申请了垫付协议
     */
    private int applyagreements;

    /**
     * 资产来源
     */
    private String borrowProjectSource;

    /**
     * 期数
     */
    private int repayPeriod;

    /**
     * 垫付时间
     */
    private String repayYseTime;

    /**
     * 垫付总额
     */
    private BigDecimal repayCapital;

    /**
     * 借款人ID
     */
    private int userId;
    /**
     * 借款人
     */
    private String userName;

    public String getRepayUserId() {
        return repayUserId;
    }

    public void setRepayUserId(String repayUserId) {
        this.repayUserId = repayUserId;
    }

    public String getRepayUsername() {
        return repayUsername;
    }

    public void setRepayUsername(String repayUsername) {
        this.repayUsername = repayUsername;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getRepayStatus() {
        return repayStatus;
    }

    public void setRepayStatus(String repayStatus) {
        this.repayStatus = repayStatus;
    }

    public boolean isMonth() {
        return isMonth;
    }

    public void setMonth(boolean month) {
        isMonth = month;
    }

    public int getApplyagreements() {
        return applyagreements;
    }

    public void setApplyagreements(int applyagreements) {
        this.applyagreements = applyagreements;
    }

    public String getBorrowProjectSource() {
        return borrowProjectSource;
    }

    public void setBorrowProjectSource(String borrowProjectSource) {
        this.borrowProjectSource = borrowProjectSource;
    }

    public int getRepayPeriod() {
        return repayPeriod;
    }

    public void setRepayPeriod(int repayPeriod) {
        this.repayPeriod = repayPeriod;
    }

    public String getRepayYseTime() {
        return repayYseTime;
    }

    public void setRepayYseTime(String repayYseTime) {
        this.repayYseTime = repayYseTime;
    }

    public BigDecimal getRepayCapital() {
        return repayCapital;
    }

    public void setRepayCapital(BigDecimal repayCapital) {
        this.repayCapital = repayCapital;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
