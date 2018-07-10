/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.model.customize.admin;

import java.io.Serializable;

/**
 * @author wangjun
 * @version BorrowFullCustomize, v0.1 2018/7/6 10:02
 */
public class BorrowFullCustomize implements Serializable {
    /**
     * 借款编码
     */
    private String borrowNid;
    /**
     * 借款标题
     */
    private String borrowName;
    /**
     * 借款用户
     */
    private String username;
    /**
     * 借款金额
     */
    private String account;
    /**
     * 年利率
     */
    private String borrowApr;
    /**
     * 借款期限
     */
    private String borrowPeriod;
    /**
     * 借到金额
     */
    private String borrowAccountYes;
    /**
     * 有效时间
     */
    private String borrowValidTime;
    /**
     * 状态
     */
    private String status;
    /**
     * 状态名称
     */
    private String statusName;

    /**项目复审状态*/
    private String reverifyStatus;

    /**项目复审状态*/
    private String reverifyStatusName;

    /**
     * 融资服务费
     */
    private String serviceScale;
    /**
     * 账户管理费率
     */
    private String managerScale;
    /**
     * 发标时间
     */
    private String ontime;
    /**
     * 满表时间
     */
    private String overTime;
    /**
     * 还款方式
     */
    private String borrowStyle;
    /**
     * 还款方式名称
     */
    private String borrowStyleName;
    /**
     * 项目类型
     */
    private String projectType;
    /**
     * 项目类型名称
     */
    private String projectTypeName;
    /**
     * 项目类型名称
     */
    private String managerScaleEnd;
    /**
     * 爆标标识
     */
    private String accountFlag;

    /**
     * 借款金额合计
     */
    private String sumAccount;

    /**
     * 借到金额合计
     */
    private String sumBorrowAccountYes;

    /**
     * 服务费合计
     */
    private String sumServiceScale;

    /**
     * 投资人
     */
    private String investor;

    /**
     * 投资金额（元）
     */
    private String investmentAmount;

    /**
     * 应放款金额
     */
    private String loanAmount;

    /**
     * 应收服务费
     */
    private String serviceCharge;
    /**
     * 操作平台
     */
    private String operatingDeck;

    /**
     * 操作时间
     */
    private String operatingTime;

    /**
     * 是否使用引擎 0:否 , 1：是
     */
    private String isEngineUsed;

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getBorrowName() {
        return borrowName;
    }

    public void setBorrowName(String borrowName) {
        this.borrowName = borrowName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    public String getBorrowAccountYes() {
        return borrowAccountYes;
    }

    public void setBorrowAccountYes(String borrowAccountYes) {
        this.borrowAccountYes = borrowAccountYes;
    }

    public String getBorrowValidTime() {
        return borrowValidTime;
    }

    public void setBorrowValidTime(String borrowValidTime) {
        this.borrowValidTime = borrowValidTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getReverifyStatus() {
        return reverifyStatus;
    }

    public void setReverifyStatus(String reverifyStatus) {
        this.reverifyStatus = reverifyStatus;
    }

    public String getReverifyStatusName() {
        return reverifyStatusName;
    }

    public void setReverifyStatusName(String reverifyStatusName) {
        this.reverifyStatusName = reverifyStatusName;
    }

    public String getServiceScale() {
        return serviceScale;
    }

    public void setServiceScale(String serviceScale) {
        this.serviceScale = serviceScale;
    }

    public String getManagerScale() {
        return managerScale;
    }

    public void setManagerScale(String managerScale) {
        this.managerScale = managerScale;
    }

    public String getOntime() {
        return ontime;
    }

    public void setOntime(String ontime) {
        this.ontime = ontime;
    }

    public String getOverTime() {
        return overTime;
    }

    public void setOverTime(String overTime) {
        this.overTime = overTime;
    }

    public String getBorrowStyle() {
        return borrowStyle;
    }

    public void setBorrowStyle(String borrowStyle) {
        this.borrowStyle = borrowStyle;
    }

    public String getBorrowStyleName() {
        return borrowStyleName;
    }

    public void setBorrowStyleName(String borrowStyleName) {
        this.borrowStyleName = borrowStyleName;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getProjectTypeName() {
        return projectTypeName;
    }

    public void setProjectTypeName(String projectTypeName) {
        this.projectTypeName = projectTypeName;
    }

    public String getManagerScaleEnd() {
        return managerScaleEnd;
    }

    public void setManagerScaleEnd(String managerScaleEnd) {
        this.managerScaleEnd = managerScaleEnd;
    }

    public String getAccountFlag() {
        return accountFlag;
    }

    public void setAccountFlag(String accountFlag) {
        this.accountFlag = accountFlag;
    }

    public String getSumAccount() {
        return sumAccount;
    }

    public void setSumAccount(String sumAccount) {
        this.sumAccount = sumAccount;
    }

    public String getSumBorrowAccountYes() {
        return sumBorrowAccountYes;
    }

    public void setSumBorrowAccountYes(String sumBorrowAccountYes) {
        this.sumBorrowAccountYes = sumBorrowAccountYes;
    }

    public String getSumServiceScale() {
        return sumServiceScale;
    }

    public void setSumServiceScale(String sumServiceScale) {
        this.sumServiceScale = sumServiceScale;
    }

    public String getInvestor() {
        return investor;
    }

    public void setInvestor(String investor) {
        this.investor = investor;
    }

    public String getInvestmentAmount() {
        return investmentAmount;
    }

    public void setInvestmentAmount(String investmentAmount) {
        this.investmentAmount = investmentAmount;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(String serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public String getOperatingDeck() {
        return operatingDeck;
    }

    public void setOperatingDeck(String operatingDeck) {
        this.operatingDeck = operatingDeck;
    }

    public String getOperatingTime() {
        return operatingTime;
    }

    public void setOperatingTime(String operatingTime) {
        this.operatingTime = operatingTime;
    }

    public String getIsEngineUsed() {
        return isEngineUsed;
    }

    public void setIsEngineUsed(String isEngineUsed) {
        this.isEngineUsed = isEngineUsed;
    }
}
