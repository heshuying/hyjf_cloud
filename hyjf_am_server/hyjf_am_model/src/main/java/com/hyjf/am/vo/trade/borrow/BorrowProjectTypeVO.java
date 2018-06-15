/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.trade.borrow;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.util.Date;

/**
 * @author fuqiang
 * @version BorrowProjectTypeVO, v0.1 2018/6/12 17:49
 */
public class BorrowProjectTypeVO extends BaseVO implements Serializable {
    private Integer id;

    private String borrowProjectType;

    private String borrowCd;

    private String borrowName;

    private String borrowClass;

    private String investUserType;

    private String status;

    private String investStart;

    private String investEnd;

    private String remark;

    private String createGroupId;

    private String createUserId;

    private Date createTime;

    private String updateGroupId;

    private String updateUserId;

    private Date updateTime;

    private Long increaseMoney;

    private Integer interestCoupon;

    private Integer tasteMoney;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBorrowProjectType() {
        return borrowProjectType;
    }

    public void setBorrowProjectType(String borrowProjectType) {
        this.borrowProjectType = borrowProjectType == null ? null : borrowProjectType.trim();
    }

    public String getBorrowCd() {
        return borrowCd;
    }

    public void setBorrowCd(String borrowCd) {
        this.borrowCd = borrowCd == null ? null : borrowCd.trim();
    }

    public String getBorrowName() {
        return borrowName;
    }

    public void setBorrowName(String borrowName) {
        this.borrowName = borrowName == null ? null : borrowName.trim();
    }

    public String getBorrowClass() {
        return borrowClass;
    }

    public void setBorrowClass(String borrowClass) {
        this.borrowClass = borrowClass == null ? null : borrowClass.trim();
    }

    public String getInvestUserType() {
        return investUserType;
    }

    public void setInvestUserType(String investUserType) {
        this.investUserType = investUserType == null ? null : investUserType.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getInvestStart() {
        return investStart;
    }

    public void setInvestStart(String investStart) {
        this.investStart = investStart == null ? null : investStart.trim();
    }

    public String getInvestEnd() {
        return investEnd;
    }

    public void setInvestEnd(String investEnd) {
        this.investEnd = investEnd == null ? null : investEnd.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getCreateGroupId() {
        return createGroupId;
    }

    public void setCreateGroupId(String createGroupId) {
        this.createGroupId = createGroupId == null ? null : createGroupId.trim();
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId == null ? null : createUserId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateGroupId() {
        return updateGroupId;
    }

    public void setUpdateGroupId(String updateGroupId) {
        this.updateGroupId = updateGroupId == null ? null : updateGroupId.trim();
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId == null ? null : updateUserId.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getIncreaseMoney() {
        return increaseMoney;
    }

    public void setIncreaseMoney(Long increaseMoney) {
        this.increaseMoney = increaseMoney;
    }

    public Integer getInterestCoupon() {
        return interestCoupon;
    }

    public void setInterestCoupon(Integer interestCoupon) {
        this.interestCoupon = interestCoupon;
    }

    public Integer getTasteMoney() {
        return tasteMoney;
    }

    public void setTasteMoney(Integer tasteMoney) {
        this.tasteMoney = tasteMoney;
    }
}
