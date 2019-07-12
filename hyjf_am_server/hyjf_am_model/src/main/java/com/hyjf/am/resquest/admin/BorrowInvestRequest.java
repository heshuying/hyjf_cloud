/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;

import java.io.Serializable;

/**
 * @author wangjun
 * @version BorrowInvestRequest, v0.1 2018/7/10 14:25
 */
public class BorrowInvestRequest extends BasePage implements Serializable {
    private int limitStart = -1;

    private int limitEnd = -1;
    /**
     * 借款编号 检索条件
     */
    private String borrowNidSrch;

    /**
     * 借款期限
     */
    private String borrowPeriod;

    /**
     * 用户名 检索条件
     */
    private String usernameSrch;

    /**
     * 推荐人 检索条件
     */
    private String referrerNameSrch;

    /**
     * 还款方式 检索条件
     */
    private String borrowStyleSrch;

    /**
     * 操作平台 检索条件
     */
    private String clientSrch;

    /**
     * 出借时间 检索条件
     */
    private String timeStartSrch;
    /**
     * 出借时间 检索条件
     */
    private String timeEndSrch;

    /**
     * 出借类别
     */
    private String investType;

    /**
     * 检索条件 计划编号
     */
    private String planNidSrch;

    /**
     * 汇计划加入订单号
     */
    private String accedeOrderIdSrch;

    /**
     * 是否复投投标  1；是   0：否
     */
    private String tenderType;

    /**
     * 复审通过时间(查询开始)
     */
    private String reAuthPassStartTime;
    /**
     * 复审通过时间(查询结束)
     */
    private String reAuthPassEndTime;

    /**
     * 资产来源
     */
    private String instCodeSrch;

    /**
     * 产品类型
     */
    private String productType;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 标的编号
     */
    private String borrowNid;

    /**
     * 出借订单号
     */
    private String nid;

    /** 债权结束状态*/
    private String stateSrch;

    public int getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    public int getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(int limitEnd) {
        this.limitEnd = limitEnd;
    }

    public String getBorrowNidSrch() {
        return borrowNidSrch;
    }

    public void setBorrowNidSrch(String borrowNidSrch) {
        this.borrowNidSrch = borrowNidSrch;
    }

    public String getBorrowPeriod() {
        return borrowPeriod;
    }

    public void setBorrowPeriod(String borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }

    public String getUsernameSrch() {
        return usernameSrch;
    }

    public void setUsernameSrch(String usernameSrch) {
        this.usernameSrch = usernameSrch;
    }

    public String getReferrerNameSrch() {
        return referrerNameSrch;
    }

    public void setReferrerNameSrch(String referrerNameSrch) {
        this.referrerNameSrch = referrerNameSrch;
    }

    public String getBorrowStyleSrch() {
        return borrowStyleSrch;
    }

    public void setBorrowStyleSrch(String borrowStyleSrch) {
        this.borrowStyleSrch = borrowStyleSrch;
    }

    public String getClientSrch() {
        return clientSrch;
    }

    public void setClientSrch(String clientSrch) {
        this.clientSrch = clientSrch;
    }

    public String getTimeStartSrch() {
        return timeStartSrch;
    }

    public void setTimeStartSrch(String timeStartSrch) {
        this.timeStartSrch = timeStartSrch;
    }

    public String getTimeEndSrch() {
        return timeEndSrch;
    }

    public void setTimeEndSrch(String timeEndSrch) {
        this.timeEndSrch = timeEndSrch;
    }

    public String getInvestType() {
        return investType;
    }

    public void setInvestType(String investType) {
        this.investType = investType;
    }

    public String getPlanNidSrch() {
        return planNidSrch;
    }

    public void setPlanNidSrch(String planNidSrch) {
        this.planNidSrch = planNidSrch;
    }

    public String getAccedeOrderIdSrch() {
        return accedeOrderIdSrch;
    }

    public void setAccedeOrderIdSrch(String accedeOrderIdSrch) {
        this.accedeOrderIdSrch = accedeOrderIdSrch;
    }

    public String getTenderType() {
        return tenderType;
    }

    public void setTenderType(String tenderType) {
        this.tenderType = tenderType;
    }

    public String getReAuthPassStartTime() {
        return reAuthPassStartTime;
    }

    public void setReAuthPassStartTime(String reAuthPassStartTime) {
        this.reAuthPassStartTime = reAuthPassStartTime;
    }

    public String getReAuthPassEndTime() {
        return reAuthPassEndTime;
    }

    public void setReAuthPassEndTime(String reAuthPassEndTime) {
        this.reAuthPassEndTime = reAuthPassEndTime;
    }

    public String getInstCodeSrch() {
        return instCodeSrch;
    }

    public void setInstCodeSrch(String instCodeSrch) {
        this.instCodeSrch = instCodeSrch;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public String getStateSrch() {
        return stateSrch;
    }

    public void setStateSrch(String stateSrch) {
        this.stateSrch = stateSrch;
    }
}
