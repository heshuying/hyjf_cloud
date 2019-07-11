package com.hyjf.am.vo.user;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 用户待还标的债转详情 标的详细信息
 * @Author : huanghui
 */
public class WebUserTransferBorrowInfoCustomizeVO2 extends BaseVO implements Serializable {

    /** 标的 NID*/
    private String borrowNid;

    /** 计划编号 */
    private String planNid;

    /** 项目期限 */
    private String borrowPeriod;

    /** 还款方式*/
    private String borrowStyle;

    /** 借款金额 */
    private BigDecimal account;

    /** 到账金额 */
    private BigDecimal sucSmount;

    /** 历史年回报率 */
    private String borrowApr;

    /** 开标时间 */
    private String sendTime;

    /** 满标时间 */
    private String borrowFullTime;

    /** 放款时间 */
    private String recoverLastTime;

    /** 借款类型 */
    private int projectType;

    /** 标的状态 */
    private int borrowStatus;

    /** 加息 */
//    private BigDecimal borrowExtraYield;

    /** 服务费 */
    private BigDecimal serviceFee;

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getPlanNid() {
        return planNid;
    }

    public void setPlanNid(String planNid) {
        this.planNid = planNid;
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

    public BigDecimal getAccount() {
        return account;
    }

    public void setAccount(BigDecimal account) {
        this.account = account;
    }

    public BigDecimal getSucSmount() {
        return sucSmount;
    }

    public void setSucSmount(BigDecimal sucSmount) {
        this.sucSmount = sucSmount;
    }

    public String getBorrowApr() {
        return borrowApr;
    }

    public void setBorrowApr(String borrowApr) {
        this.borrowApr = borrowApr;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getBorrowFullTime() {
        return borrowFullTime;
    }

    public void setBorrowFullTime(String borrowFullTime) {
        this.borrowFullTime = borrowFullTime;
    }

    public String getRecoverLastTime() {
        return recoverLastTime;
    }

    public void setRecoverLastTime(String recoverLastTime) {
        this.recoverLastTime = recoverLastTime;
    }

    public int getProjectType() {
        return projectType;
    }

    public void setProjectType(int projectType) {
        this.projectType = projectType;
    }

    public int getBorrowStatus() {
        return borrowStatus;
    }

    public void setBorrowStatus(int borrowStatus) {
        this.borrowStatus = borrowStatus;
    }

    public BigDecimal getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(BigDecimal serviceFee) {
        this.serviceFee = serviceFee;
    }
}
