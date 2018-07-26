
package com.hyjf.cs.trade.bean.app;

import java.io.Serializable;

/**
 * @author Administrator
 */

public class AppTransferDetailBean implements Serializable {

    /**
     * serialVersionUID:
     */

    private static final long serialVersionUID = 1L;
    /**出让金额*/
    private String borrowRemain;
    /**项目进度 (不带百分号)*/
    private String borrowProgress;
    /**转让编号*/
    private String transferId;
    /**发起转让时间*/
    private String onTime;
    /**折让率 (不带百分号)*/
    private String transferDiscount;
    /**项目金额 (总金额)*/
    private String account;
    /**年化利率*/
    private String borrowApr;
    /**原项目编号*/
    private String borrowId;
    /**债权持有天数*/
    private String transferLeft;
    /**项目进行状态 如 转让状态，0.进行中，1.停止*/
    private String status;
    /**项目进度状态 0:发起转让 1:投资人认购 2:支付资金 3:计息 4:回款*/
    private String borrowProgressStatus;
    /**剩余期限*/
    private String borrowPeriod;
    /**项目期限单位*/
    private String borrowPeriodUnit;
    /**项目类型*/
    private String type;
    /**项目类型标签*/
    private String tag;
    /**还款方式*/
    private String repayStyle;
    public String getBorrowRemain() {
        return borrowRemain;
    }
    public void setBorrowRemain(String borrowRemain) {
        this.borrowRemain = borrowRemain;
    }
    public String getBorrowProgress() {
        return borrowProgress;
    }
    public void setBorrowProgress(String borrowProgress) {
        this.borrowProgress = borrowProgress;
    }
    public String getTransferId() {
        return transferId;
    }
    public void setTransferId(String transferId) {
        this.transferId = transferId;
    }
    public String getOnTime() {
        return onTime;
    }
    public void setOnTime(String onTime) {
        this.onTime = onTime;
    }
    public String getTransferDiscount() {
        return transferDiscount;
    }
    public void setTransferDiscount(String transferDiscount) {
        this.transferDiscount = transferDiscount;
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
    public String getBorrowId() {
        return borrowId;
    }
    public void setBorrowId(String borrowId) {
        this.borrowId = borrowId;
    }
    public String getTransferLeft() {
        return transferLeft;
    }
    public void setTransferLeft(String transferLeft) {
        this.transferLeft = transferLeft;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getBorrowProgressStatus() {
        return borrowProgressStatus;
    }
    public void setBorrowProgressStatus(String borrowProgressStatus) {
        this.borrowProgressStatus = borrowProgressStatus;
    }
    public String getBorrowPeriod() {
        return borrowPeriod;
    }
    public void setBorrowPeriod(String borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }
    public String getBorrowPeriodUnit() {
        return borrowPeriodUnit;
    }
    public void setBorrowPeriodUnit(String borrowPeriodUnit) {
        this.borrowPeriodUnit = borrowPeriodUnit;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getTag() {
        return tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }
    public String getRepayStyle() {
        return repayStyle;
    }
    public void setRepayStyle(String repayStyle) {
        this.repayStyle = repayStyle;
    }

}
