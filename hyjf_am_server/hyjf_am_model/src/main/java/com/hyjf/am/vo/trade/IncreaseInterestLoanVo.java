/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.trade;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author ${yaoy}
 * @version IncreaseInterestLoanVo, v0.1 2018/6/13 18:26
 */
public class IncreaseInterestLoanVo extends BaseVO implements Serializable {
    private Integer id;

    private Integer userId;

    private String userName;

    private Integer investId;

    private String investOrderId;

    private BigDecimal investAccount;

    private Integer borrowUserId;

    private String borrowUserName;

    private String borrowNid;

    private BigDecimal borrowApr;

    private BigDecimal borrowExtraYield;

    private Integer borrowPeriod;

    private String borrowStyle;

    private String borrowStyleName;

    private BigDecimal loanInterest;

    private Integer remainPeriod;

    private Integer repayPeriod;

    private String repayTime;

    private Integer repayActionTime;

    private BigDecimal repayInterestYes;

    private BigDecimal repayInterestWait;

    private Integer repayStatus;

    private String addip;

    private Integer web;

    private String repayOrderId;

    private String repayOrderDate;

    private Integer createTime;

    private Integer createUserId;

    private String createUserName;

    private Integer updateTime;

    private Integer updateUserId;

    private String updateUserName;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Integer getInvestId() {
        return investId;
    }

    public void setInvestId(Integer investId) {
        this.investId = investId;
    }

    public String getInvestOrderId() {
        return investOrderId;
    }

    public void setInvestOrderId(String investOrderId) {
        this.investOrderId = investOrderId == null ? null : investOrderId.trim();
    }

    public BigDecimal getInvestAccount() {
        return investAccount;
    }

    public void setInvestAccount(BigDecimal investAccount) {
        this.investAccount = investAccount;
    }

    public Integer getBorrowUserId() {
        return borrowUserId;
    }

    public void setBorrowUserId(Integer borrowUserId) {
        this.borrowUserId = borrowUserId;
    }

    public String getBorrowUserName() {
        return borrowUserName;
    }

    public void setBorrowUserName(String borrowUserName) {
        this.borrowUserName = borrowUserName == null ? null : borrowUserName.trim();
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid == null ? null : borrowNid.trim();
    }

    public BigDecimal getBorrowApr() {
        return borrowApr;
    }

    public void setBorrowApr(BigDecimal borrowApr) {
        this.borrowApr = borrowApr;
    }

    public BigDecimal getBorrowExtraYield() {
        return borrowExtraYield;
    }

    public void setBorrowExtraYield(BigDecimal borrowExtraYield) {
        this.borrowExtraYield = borrowExtraYield;
    }

    public Integer getBorrowPeriod() {
        return borrowPeriod;
    }

    public void setBorrowPeriod(Integer borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }

    public String getBorrowStyle() {
        return borrowStyle;
    }

    public void setBorrowStyle(String borrowStyle) {
        this.borrowStyle = borrowStyle == null ? null : borrowStyle.trim();
    }

    public String getBorrowStyleName() {
        return borrowStyleName;
    }

    public void setBorrowStyleName(String borrowStyleName) {
        this.borrowStyleName = borrowStyleName == null ? null : borrowStyleName.trim();
    }

    public BigDecimal getLoanInterest() {
        return loanInterest;
    }

    public void setLoanInterest(BigDecimal loanInterest) {
        this.loanInterest = loanInterest;
    }

    public Integer getRemainPeriod() {
        return remainPeriod;
    }

    public void setRemainPeriod(Integer remainPeriod) {
        this.remainPeriod = remainPeriod;
    }

    public Integer getRepayPeriod() {
        return repayPeriod;
    }

    public void setRepayPeriod(Integer repayPeriod) {
        this.repayPeriod = repayPeriod;
    }

    public String getRepayTime() {
        return repayTime;
    }

    public void setRepayTime(String repayTime) {
        this.repayTime = repayTime == null ? null : repayTime.trim();
    }

    public Integer getRepayActionTime() {
        return repayActionTime;
    }

    public void setRepayActionTime(Integer repayActionTime) {
        this.repayActionTime = repayActionTime;
    }

    public BigDecimal getRepayInterestYes() {
        return repayInterestYes;
    }

    public void setRepayInterestYes(BigDecimal repayInterestYes) {
        this.repayInterestYes = repayInterestYes;
    }

    public BigDecimal getRepayInterestWait() {
        return repayInterestWait;
    }

    public void setRepayInterestWait(BigDecimal repayInterestWait) {
        this.repayInterestWait = repayInterestWait;
    }

    public Integer getRepayStatus() {
        return repayStatus;
    }

    public void setRepayStatus(Integer repayStatus) {
        this.repayStatus = repayStatus;
    }

    public String getAddip() {
        return addip;
    }

    public void setAddip(String addip) {
        this.addip = addip == null ? null : addip.trim();
    }

    public Integer getWeb() {
        return web;
    }

    public void setWeb(Integer web) {
        this.web = web;
    }

    public String getRepayOrderId() {
        return repayOrderId;
    }

    public void setRepayOrderId(String repayOrderId) {
        this.repayOrderId = repayOrderId == null ? null : repayOrderId.trim();
    }

    public String getRepayOrderDate() {
        return repayOrderDate;
    }

    public void setRepayOrderDate(String repayOrderDate) {
        this.repayOrderDate = repayOrderDate == null ? null : repayOrderDate.trim();
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName == null ? null : createUserName.trim();
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName == null ? null : updateUserName.trim();
    }
}
