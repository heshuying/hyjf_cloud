package com.hyjf.am.vo.user;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.util.Date;

public class HjhUserAuthVO extends BaseVO implements Serializable {
    private Integer id;

    private Integer userId;

    private String userName;

    private Integer autoInvesStatus;

    private Integer autoCreditStatus;

    private Integer autoWithdrawStatus;

    private Integer autoConsumeStatus;

    private Integer autoCreateTime;

    private String autoOrderId;

    private String autoCreditOrderId;

    private Integer autoCreditTime;

    private String autoCreditEndTime;

    private Integer autoBidTime;

    private String autoBidEndTime;

    private Integer autoPaymentStatus;

    private Integer autoPaymentTime;

    private String autoPaymentEndTime;

    private Integer autoRepayStatus;

    private Integer autoRepayTime;

    private String autoRepayEndTime;

    private String invesCancelTime;

    private String creditCancelTime;

    private String paymentCancelTime;

    private String repayCancelTime;

    private String invesMaxAmt;

    private String creditMaxAmt;

    private String paymentMaxAmt;

    private String repayMaxAmt;

    private Integer delFlag;

    private Integer txAmount;

    private Integer totAmount;

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

    public Integer getAutoInvesStatus() {
        return autoInvesStatus;
    }

    public void setAutoInvesStatus(Integer autoInvesStatus) {
        this.autoInvesStatus = autoInvesStatus;
    }

    public Integer getAutoCreditStatus() {
        return autoCreditStatus;
    }

    public void setAutoCreditStatus(Integer autoCreditStatus) {
        this.autoCreditStatus = autoCreditStatus;
    }

    public Integer getAutoWithdrawStatus() {
        return autoWithdrawStatus;
    }

    public void setAutoWithdrawStatus(Integer autoWithdrawStatus) {
        this.autoWithdrawStatus = autoWithdrawStatus;
    }

    public Integer getAutoConsumeStatus() {
        return autoConsumeStatus;
    }

    public void setAutoConsumeStatus(Integer autoConsumeStatus) {
        this.autoConsumeStatus = autoConsumeStatus;
    }

    public Integer getAutoCreateTime() {
        return autoCreateTime;
    }

    public void setAutoCreateTime(Integer autoCreateTime) {
        this.autoCreateTime = autoCreateTime;
    }

    public String getAutoOrderId() {
        return autoOrderId;
    }

    public void setAutoOrderId(String autoOrderId) {
        this.autoOrderId = autoOrderId == null ? null : autoOrderId.trim();
    }

    public String getAutoCreditOrderId() {
        return autoCreditOrderId;
    }

    public void setAutoCreditOrderId(String autoCreditOrderId) {
        this.autoCreditOrderId = autoCreditOrderId == null ? null : autoCreditOrderId.trim();
    }

    public Integer getAutoCreditTime() {
        return autoCreditTime;
    }

    public void setAutoCreditTime(Integer autoCreditTime) {
        this.autoCreditTime = autoCreditTime;
    }

    public String getAutoCreditEndTime() {
        return autoCreditEndTime;
    }

    public void setAutoCreditEndTime(String autoCreditEndTime) {
        this.autoCreditEndTime = autoCreditEndTime == null ? null : autoCreditEndTime.trim();
    }

    public Integer getAutoBidTime() {
        return autoBidTime;
    }

    public void setAutoBidTime(Integer autoBidTime) {
        this.autoBidTime = autoBidTime;
    }

    public String getAutoBidEndTime() {
        return autoBidEndTime;
    }

    public void setAutoBidEndTime(String autoBidEndTime) {
        this.autoBidEndTime = autoBidEndTime == null ? null : autoBidEndTime.trim();
    }

    public Integer getAutoPaymentStatus() {
        return autoPaymentStatus;
    }

    public void setAutoPaymentStatus(Integer autoPaymentStatus) {
        this.autoPaymentStatus = autoPaymentStatus;
    }

    public Integer getAutoPaymentTime() {
        return autoPaymentTime;
    }

    public void setAutoPaymentTime(Integer autoPaymentTime) {
        this.autoPaymentTime = autoPaymentTime;
    }

    public String getAutoPaymentEndTime() {
        return autoPaymentEndTime;
    }

    public void setAutoPaymentEndTime(String autoPaymentEndTime) {
        this.autoPaymentEndTime = autoPaymentEndTime == null ? null : autoPaymentEndTime.trim();
    }

    public Integer getAutoRepayStatus() {
        return autoRepayStatus;
    }

    public void setAutoRepayStatus(Integer autoRepayStatus) {
        this.autoRepayStatus = autoRepayStatus;
    }

    public Integer getAutoRepayTime() {
        return autoRepayTime;
    }

    public void setAutoRepayTime(Integer autoRepayTime) {
        this.autoRepayTime = autoRepayTime;
    }

    public String getAutoRepayEndTime() {
        return autoRepayEndTime;
    }

    public void setAutoRepayEndTime(String autoRepayEndTime) {
        this.autoRepayEndTime = autoRepayEndTime == null ? null : autoRepayEndTime.trim();
    }

    public String getInvesCancelTime() {
        return invesCancelTime;
    }

    public void setInvesCancelTime(String invesCancelTime) {
        this.invesCancelTime = invesCancelTime == null ? null : invesCancelTime.trim();
    }

    public String getCreditCancelTime() {
        return creditCancelTime;
    }

    public void setCreditCancelTime(String creditCancelTime) {
        this.creditCancelTime = creditCancelTime == null ? null : creditCancelTime.trim();
    }

    public String getPaymentCancelTime() {
        return paymentCancelTime;
    }

    public void setPaymentCancelTime(String paymentCancelTime) {
        this.paymentCancelTime = paymentCancelTime == null ? null : paymentCancelTime.trim();
    }

    public String getRepayCancelTime() {
        return repayCancelTime;
    }

    public void setRepayCancelTime(String repayCancelTime) {
        this.repayCancelTime = repayCancelTime == null ? null : repayCancelTime.trim();
    }

    public String getInvesMaxAmt() {
        return invesMaxAmt;
    }

    public void setInvesMaxAmt(String invesMaxAmt) {
        this.invesMaxAmt = invesMaxAmt == null ? null : invesMaxAmt.trim();
    }

    public String getCreditMaxAmt() {
        return creditMaxAmt;
    }

    public void setCreditMaxAmt(String creditMaxAmt) {
        this.creditMaxAmt = creditMaxAmt == null ? null : creditMaxAmt.trim();
    }

    public String getPaymentMaxAmt() {
        return paymentMaxAmt;
    }

    public void setPaymentMaxAmt(String paymentMaxAmt) {
        this.paymentMaxAmt = paymentMaxAmt == null ? null : paymentMaxAmt.trim();
    }

    public String getRepayMaxAmt() {
        return repayMaxAmt;
    }

    public void setRepayMaxAmt(String repayMaxAmt) {
        this.repayMaxAmt = repayMaxAmt == null ? null : repayMaxAmt.trim();
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getTxAmount() {
        return txAmount;
    }

    public void setTxAmount(Integer txAmount) {
        this.txAmount = txAmount;
    }

    public Integer getTotAmount() {
        return totAmount;
    }

    public void setTotAmount(Integer totAmount) {
        this.totAmount = totAmount;
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