package com.hyjf.am.user.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class HjhUserAuth implements Serializable {
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

    private Integer delFlg;

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

    public Integer getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(Integer delFlg) {
        this.delFlg = delFlg;
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