package com.hyjf.am.vo.admin.coupon;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CouponRecoverVO extends BaseVO implements Serializable {
    private Integer id;

    private String tenderId;

    private String transferId;

    private Integer recoverStatus;

    private Integer receivedFlg;

    private Integer recoverPeriod;

    private Integer transferTime;

    private Integer recoverTime;

    private Integer recoverYestime;

    private Integer mainRecoverYestime;

    private BigDecimal recoverInterest;

    private BigDecimal recoverInterestYes;

    private BigDecimal recoverAccount;

    private BigDecimal recoverAccountYes;

    private BigDecimal recoverCapital;

    private BigDecimal recoverCapitalYes;

    private Integer currentRecoverFlg;

    private Integer recoverType;

    private Integer noticeFlg;

    private Integer expTime;

    private Integer delFlag;

    private Integer createUserId;

    private Integer updateUserId;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTenderId() {
        return tenderId;
    }

    public void setTenderId(String tenderId) {
        this.tenderId = tenderId == null ? null : tenderId.trim();
    }

    public String getTransferId() {
        return transferId;
    }

    public void setTransferId(String transferId) {
        this.transferId = transferId == null ? null : transferId.trim();
    }

    public Integer getRecoverStatus() {
        return recoverStatus;
    }

    public void setRecoverStatus(Integer recoverStatus) {
        this.recoverStatus = recoverStatus;
    }

    public Integer getReceivedFlg() {
        return receivedFlg;
    }

    public void setReceivedFlg(Integer receivedFlg) {
        this.receivedFlg = receivedFlg;
    }

    public Integer getRecoverPeriod() {
        return recoverPeriod;
    }

    public void setRecoverPeriod(Integer recoverPeriod) {
        this.recoverPeriod = recoverPeriod;
    }

    public Integer getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(Integer transferTime) {
        this.transferTime = transferTime;
    }

    public Integer getRecoverTime() {
        return recoverTime;
    }

    public void setRecoverTime(Integer recoverTime) {
        this.recoverTime = recoverTime;
    }

    public Integer getRecoverYestime() {
        return recoverYestime;
    }

    public void setRecoverYestime(Integer recoverYestime) {
        this.recoverYestime = recoverYestime;
    }

    public Integer getMainRecoverYestime() {
        return mainRecoverYestime;
    }

    public void setMainRecoverYestime(Integer mainRecoverYestime) {
        this.mainRecoverYestime = mainRecoverYestime;
    }

    public BigDecimal getRecoverInterest() {
        return recoverInterest;
    }

    public void setRecoverInterest(BigDecimal recoverInterest) {
        this.recoverInterest = recoverInterest;
    }

    public BigDecimal getRecoverInterestYes() {
        return recoverInterestYes;
    }

    public void setRecoverInterestYes(BigDecimal recoverInterestYes) {
        this.recoverInterestYes = recoverInterestYes;
    }

    public BigDecimal getRecoverAccount() {
        return recoverAccount;
    }

    public void setRecoverAccount(BigDecimal recoverAccount) {
        this.recoverAccount = recoverAccount;
    }

    public BigDecimal getRecoverAccountYes() {
        return recoverAccountYes;
    }

    public void setRecoverAccountYes(BigDecimal recoverAccountYes) {
        this.recoverAccountYes = recoverAccountYes;
    }

    public BigDecimal getRecoverCapital() {
        return recoverCapital;
    }

    public void setRecoverCapital(BigDecimal recoverCapital) {
        this.recoverCapital = recoverCapital;
    }

    public BigDecimal getRecoverCapitalYes() {
        return recoverCapitalYes;
    }

    public void setRecoverCapitalYes(BigDecimal recoverCapitalYes) {
        this.recoverCapitalYes = recoverCapitalYes;
    }

    public Integer getCurrentRecoverFlg() {
        return currentRecoverFlg;
    }

    public void setCurrentRecoverFlg(Integer currentRecoverFlg) {
        this.currentRecoverFlg = currentRecoverFlg;
    }

    public Integer getRecoverType() {
        return recoverType;
    }

    public void setRecoverType(Integer recoverType) {
        this.recoverType = recoverType;
    }

    public Integer getNoticeFlg() {
        return noticeFlg;
    }

    public void setNoticeFlg(Integer noticeFlg) {
        this.noticeFlg = noticeFlg;
    }

    public Integer getExpTime() {
        return expTime;
    }

    public void setExpTime(Integer expTime) {
        this.expTime = expTime;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
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
}
