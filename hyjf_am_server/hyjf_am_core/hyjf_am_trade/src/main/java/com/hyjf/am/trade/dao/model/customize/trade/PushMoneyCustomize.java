/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.model.customize.trade;

import java.math.BigDecimal;

/**
 * @author zdj
 * @version PushMoneyCustomize, v0.1 2018/6/23 17:17
 */
public class PushMoneyCustomize {

    private int limitStart = -1;
    private int limitEnd = -1;

    //项目编号
    public String borrowNid;
    // 项目标题
    public String borrowName;
    // 项目还款方式  = endday 天   !=endday 个月
    public String borrowStyle;
    // 融资期限
    public String borrowPeriod;
    // 融资金额
    public String account;
    // 提成总额
    public String commission;
    // 放款时间
    public String recoverLastTime;

    // 提成列表用
    // 提成id
    private Integer id;
    // 融资期限
    private String rzqx;
    // 分公司
    private String regionName;
    // 分部
    private String branchName;
    // 团队
    private String departmentName;
    // 提成人
    private String username;
    // 电子账号
    private String accountId;
    // 提成人用户属性0=>无主单 1=>有主单 2=>线下员工 3=>线上员工
    private String attribute;
    // 投资人
    private String usernameTender;
    // 投资金额
    private BigDecimal accountTender;
    // 状态
    private String statusName;
    // 投资时间
    private String tenderTimeView;
    // 提成发放时间
    private String sendTimeView;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getBorrowStyle() {
        return borrowStyle;
    }

    public void setBorrowStyle(String borrowStyle) {
        this.borrowStyle = borrowStyle;
    }

    public String getBorrowPeriod() {
        return borrowPeriod;
    }

    public void setBorrowPeriod(String borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getRecoverLastTime() {
        return recoverLastTime;
    }

    public void setRecoverLastTime(String recoverLastTime) {
        this.recoverLastTime = recoverLastTime;
    }

    public String getRzqx() {
        return rzqx;
    }

    public void setRzqx(String rzqx) {
        this.rzqx = rzqx;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getUsernameTender() {
        return usernameTender;
    }

    public void setUsernameTender(String usernameTender) {
        this.usernameTender = usernameTender;
    }

    public BigDecimal getAccountTender() {
        return accountTender;
    }

    public void setAccountTender(BigDecimal accountTender) {
        this.accountTender = accountTender;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getTenderTimeView() {
        return tenderTimeView;
    }

    public void setTenderTimeView(String tenderTimeView) {
        this.tenderTimeView = tenderTimeView;
    }

    public String getSendTimeView() {
        return sendTimeView;
    }

    public void setSendTimeView(String sendTimeView) {
        this.sendTimeView = sendTimeView;
    }
}
