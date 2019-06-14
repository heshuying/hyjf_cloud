/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.model.customize;

import java.io.Serializable;

/**
 * @author wangjun
 * @version BorrowRegistCustomize, v0.1 2018/6/29 18:40
 */
public class BorrowRegistCustomize implements Serializable {
    /** 借款编码 */
    private String borrowNid;

    /** 借款标题 */
    private String borrowName;

    /** 借款用户 */
    private String username;

    /** 项目类型 */
    private String projectType;

    /** 项目类型名称 */
    private String projectTypeName;

    /** 借款金额 */
    private String account;

    /** 借款期限 */
    private String borrowPeriod;

    /** 年利率 */
    private String borrowApr;

    /** 还款方式 */
    private String borrowStyle;

    /** 还款方式 */
    private String borrowStyleName;

    /** 备案状态 */
    private String registStatus;

    /** 备案状态名称 */
    private String registStatusName;

    /** 添加时间 */
    private String createTime;

    /** 银行备案结果描述*/
    private String registBankmsg;

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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getBorrowPeriod() {
        return borrowPeriod;
    }

    public void setBorrowPeriod(String borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }

    public String getBorrowApr() {
        return borrowApr;
    }

    public void setBorrowApr(String borrowApr) {
        this.borrowApr = borrowApr;
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

    public String getRegistStatus() {
        return registStatus;
    }

    public void setRegistStatus(String registStatus) {
        this.registStatus = registStatus;
    }

    public String getRegistStatusName() {
        return registStatusName;
    }

    public void setRegistStatusName(String registStatusName) {
        this.registStatusName = registStatusName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRegistBankmsg() {
        return registBankmsg;
    }

    public void setRegistBankmsg(String registBankmsg) {
        this.registBankmsg = registBankmsg;
    }
}
