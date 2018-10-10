package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class HjhBailConfigInfo implements Serializable {
    private Integer id;

    private String instCode;

    private String borrowStyle;

    private Integer isNewCredit;

    private Integer isLoanCredit;

    private Integer repayCapitalType;

    private Integer createUserId;

    private Integer updateUserId;

    private Date createTime;

    private Date updateTime;

    private Integer delFlg;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode == null ? null : instCode.trim();
    }

    public String getBorrowStyle() {
        return borrowStyle;
    }

    public void setBorrowStyle(String borrowStyle) {
        this.borrowStyle = borrowStyle == null ? null : borrowStyle.trim();
    }

    public Integer getIsNewCredit() {
        return isNewCredit;
    }

    public void setIsNewCredit(Integer isNewCredit) {
        this.isNewCredit = isNewCredit;
    }

    public Integer getIsLoanCredit() {
        return isLoanCredit;
    }

    public void setIsLoanCredit(Integer isLoanCredit) {
        this.isLoanCredit = isLoanCredit;
    }

    public Integer getRepayCapitalType() {
        return repayCapitalType;
    }

    public void setRepayCapitalType(Integer repayCapitalType) {
        this.repayCapitalType = repayCapitalType;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(Integer delFlg) {
        this.delFlg = delFlg;
    }
}