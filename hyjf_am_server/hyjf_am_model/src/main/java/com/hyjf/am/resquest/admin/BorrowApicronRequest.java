/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BaseVO;

import java.math.BigDecimal;

/**
 * @author zdj
 * @version TenderCommissionRequest, v0.1 2018/7/3 14:16
 *          资金中心->直投提成管理-更新借款API表(请求参数）
 */
public class BorrowApicronRequest extends BaseVO {
    private Integer id;

    private String nid;

    private Integer userId;

    private String borrowNid;

    private Integer status;

    private Integer webStatus;

    private Integer apiType;

    private Integer repayStatus;

    private Integer createTime;

    private Integer updateTime;

    private Integer periodNow;

    private Integer creditRepayStatus;

    private Integer extraYieldStatus;

    private Integer extraYieldRepayStatus;

    private Integer isRepayOrgFlag;

    private String userName;

    private BigDecimal borrowAccount;

    private Integer borrowPeriod;

    private String batchNo;

    private BigDecimal batchAmount;

    private Integer batchCounts;

    private BigDecimal batchServiceFee;

    private BigDecimal txAmount;

    private Integer txCounts;

    private Byte failTimes;

    private Integer sucCounts;

    private BigDecimal sucAmount;

    private Integer failCounts;

    private BigDecimal failAmount;

    private BigDecimal serviceFee;

    private Integer txDate;

    private Integer txTime;

    private Integer seqNo;

    private String bankSeqNo;

    private String planNid;

    private String ordid;

    private String data;

    private Integer agreementStatus;

    private static final long serialVersionUID = 1L;

    public Integer getAgreementStatus() {
        return agreementStatus;
    }

    public void setAgreementStatus(Integer agreementStatus) {
        this.agreementStatus = agreementStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid == null ? null : nid.trim();
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
        this.borrowNid = borrowNid == null ? null : borrowNid.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getWebStatus() {
        return webStatus;
    }

    public void setWebStatus(Integer webStatus) {
        this.webStatus = webStatus;
    }

    public Integer getApiType() {
        return apiType;
    }

    public void setApiType(Integer apiType) {
        this.apiType = apiType;
    }

    public Integer getRepayStatus() {
        return repayStatus;
    }

    public void setRepayStatus(Integer repayStatus) {
        this.repayStatus = repayStatus;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getPeriodNow() {
        return periodNow;
    }

    public void setPeriodNow(Integer periodNow) {
        this.periodNow = periodNow;
    }

    public Integer getCreditRepayStatus() {
        return creditRepayStatus;
    }

    public void setCreditRepayStatus(Integer creditRepayStatus) {
        this.creditRepayStatus = creditRepayStatus;
    }

    public Integer getExtraYieldStatus() {
        return extraYieldStatus;
    }

    public void setExtraYieldStatus(Integer extraYieldStatus) {
        this.extraYieldStatus = extraYieldStatus;
    }

    public Integer getExtraYieldRepayStatus() {
        return extraYieldRepayStatus;
    }

    public void setExtraYieldRepayStatus(Integer extraYieldRepayStatus) {
        this.extraYieldRepayStatus = extraYieldRepayStatus;
    }

    public Integer getIsRepayOrgFlag() {
        return isRepayOrgFlag;
    }

    public void setIsRepayOrgFlag(Integer isRepayOrgFlag) {
        this.isRepayOrgFlag = isRepayOrgFlag;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public BigDecimal getBorrowAccount() {
        return borrowAccount;
    }

    public void setBorrowAccount(BigDecimal borrowAccount) {
        this.borrowAccount = borrowAccount;
    }

    public Integer getBorrowPeriod() {
        return borrowPeriod;
    }

    public void setBorrowPeriod(Integer borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo == null ? null : batchNo.trim();
    }

    public BigDecimal getBatchAmount() {
        return batchAmount;
    }

    public void setBatchAmount(BigDecimal batchAmount) {
        this.batchAmount = batchAmount;
    }

    public Integer getBatchCounts() {
        return batchCounts;
    }

    public void setBatchCounts(Integer batchCounts) {
        this.batchCounts = batchCounts;
    }

    public BigDecimal getBatchServiceFee() {
        return batchServiceFee;
    }

    public void setBatchServiceFee(BigDecimal batchServiceFee) {
        this.batchServiceFee = batchServiceFee;
    }

    public BigDecimal getTxAmount() {
        return txAmount;
    }

    public void setTxAmount(BigDecimal txAmount) {
        this.txAmount = txAmount;
    }

    public Integer getTxCounts() {
        return txCounts;
    }

    public void setTxCounts(Integer txCounts) {
        this.txCounts = txCounts;
    }

    public Byte getFailTimes() {
        return failTimes;
    }

    public void setFailTimes(Byte failTimes) {
        this.failTimes = failTimes;
    }

    public Integer getSucCounts() {
        return sucCounts;
    }

    public void setSucCounts(Integer sucCounts) {
        this.sucCounts = sucCounts;
    }

    public BigDecimal getSucAmount() {
        return sucAmount;
    }

    public void setSucAmount(BigDecimal sucAmount) {
        this.sucAmount = sucAmount;
    }

    public Integer getFailCounts() {
        return failCounts;
    }

    public void setFailCounts(Integer failCounts) {
        this.failCounts = failCounts;
    }

    public BigDecimal getFailAmount() {
        return failAmount;
    }

    public void setFailAmount(BigDecimal failAmount) {
        this.failAmount = failAmount;
    }

    public BigDecimal getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(BigDecimal serviceFee) {
        this.serviceFee = serviceFee;
    }

    public Integer getTxDate() {
        return txDate;
    }

    public void setTxDate(Integer txDate) {
        this.txDate = txDate;
    }

    public Integer getTxTime() {
        return txTime;
    }

    public void setTxTime(Integer txTime) {
        this.txTime = txTime;
    }

    public Integer getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(Integer seqNo) {
        this.seqNo = seqNo;
    }

    public String getBankSeqNo() {
        return bankSeqNo;
    }

    public void setBankSeqNo(String bankSeqNo) {
        this.bankSeqNo = bankSeqNo == null ? null : bankSeqNo.trim();
    }

    public String getPlanNid() {
        return planNid;
    }

    public void setPlanNid(String planNid) {
        this.planNid = planNid == null ? null : planNid.trim();
    }

    public String getOrdid() {
        return ordid;
    }

    public void setOrdid(String ordid) {
        this.ordid = ordid == null ? null : ordid.trim();
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data == null ? null : data.trim();
    }

}
