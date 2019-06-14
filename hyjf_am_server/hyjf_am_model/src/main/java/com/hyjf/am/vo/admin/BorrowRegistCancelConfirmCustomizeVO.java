package com.hyjf.am.vo.admin;

/**
 * 标的备案撤销确认页面数据
 * @author hesy
 */
public class BorrowRegistCancelConfirmCustomizeVO {
    /** 借款编号*/
    private String borrowNid;
    /** 借款名称*/
    private String borrowName;
    /** 借款人*/
    private String borrowUserName;
    /** 借款金额*/
    private String account;
    /** 标的利率*/
    private String borrowApr;
    /** 标的期限*/
    private String borrowPeriod;
    /** 还款方式*/
    private String borrowStyle;
    /** 还款方式名称*/
    private String borrowStyleName;
    /** 项目类型名称*/
    private String projectTypeName;
    /** 垫付机构用户名*/
    private String repayOrgName;
    /** 创建时间*/
    private String createTime;
    /** 标的备案时间*/
    private String registTime;
    /** 标的备案用户名*/
    private String registUserName;
    /** 标的状态名称*/
    private String borrowStatus;
    /** 备案状态名称*/
    private String registStatus;

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

    public String getBorrowUserName() {
        return borrowUserName;
    }

    public void setBorrowUserName(String borrowUserName) {
        this.borrowUserName = borrowUserName;
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

    public String getProjectTypeName() {
        return projectTypeName;
    }

    public void setProjectTypeName(String projectTypeName) {
        this.projectTypeName = projectTypeName;
    }

    public String getRepayOrgName() {
        return repayOrgName;
    }

    public void setRepayOrgName(String repayOrgName) {
        this.repayOrgName = repayOrgName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRegistTime() {
        return registTime;
    }

    public void setRegistTime(String registTime) {
        this.registTime = registTime;
    }

    public String getRegistUserName() {
        return registUserName;
    }

    public void setRegistUserName(String registUserName) {
        this.registUserName = registUserName;
    }

    public String getBorrowStatus() {
        return borrowStatus;
    }

    public void setBorrowStatus(String borrowStatus) {
        this.borrowStatus = borrowStatus;
    }

    public String getRegistStatus() {
        return registStatus;
    }

    public void setRegistStatus(String registStatus) {
        this.registStatus = registStatus;
    }
}
