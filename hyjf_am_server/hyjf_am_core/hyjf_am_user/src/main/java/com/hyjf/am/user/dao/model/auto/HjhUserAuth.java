package com.hyjf.am.user.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class HjhUserAuth implements Serializable {
    /**
     * id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 用户id
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 用户名
     *
     * @mbggenerated
     */
    private String userName;

    /**
     * 自动投标授权状态 0: 未授权    1:已授权
     *
     * @mbggenerated
     */
    private Integer autoInvesStatus;

    /**
     * 自动债转授权状态 0: 未授权    1:已授权
     *
     * @mbggenerated
     */
    private Integer autoCreditStatus;

    /**
     * 自动预约取现状态 0: 未授权    1:已授权
     *
     * @mbggenerated
     */
    private Integer autoWithdrawStatus;

    /**
     * 自动无密消费状态 0: 未授权    1:已授权
     *
     * @mbggenerated
     */
    private Integer autoConsumeStatus;

    /**
     * 授权时间
     *
     * @mbggenerated
     */
    private Integer autoCreateTime;

    /**
     * 订单号
     *
     * @mbggenerated
     */
    private String autoOrderId;

    /**
     * 自动债转订单号
     *
     * @mbggenerated
     */
    private String autoCreditOrderId;

    /**
     * 自动债转授权时间
     *
     * @mbggenerated
     */
    private Integer autoCreditTime;

    /**
     * 自动债转授权过期时间
     *
     * @mbggenerated
     */
    private String autoCreditEndTime;

    /**
     * 自动投资授权时间
     *
     * @mbggenerated
     */
    private Integer autoBidTime;

    /**
     * 自动投资授权过期时间
     *
     * @mbggenerated
     */
    private String autoBidEndTime;

    /**
     * 缴费授权状态 0: 未授权    1:已授权
     *
     * @mbggenerated
     */
    private Integer autoPaymentStatus;

    /**
     * 缴费授权时间
     *
     * @mbggenerated
     */
    private Integer autoPaymentTime;

    /**
     * 缴费授权结束时间
     *
     * @mbggenerated
     */
    private String autoPaymentEndTime;

    /**
     * 还款授权状态 0: 未授权    1:已授权
     *
     * @mbggenerated
     */
    private Integer autoRepayStatus;

    /**
     * 还款授权时间
     *
     * @mbggenerated
     */
    private Integer autoRepayTime;

    /**
     * 还款授权结束时间
     *
     * @mbggenerated
     */
    private String autoRepayEndTime;

    /**
     * 投资授权解约时间
     *
     * @mbggenerated
     */
    private String invesCancelTime;

    /**
     * 债转授权解约时间
     *
     * @mbggenerated
     */
    private String creditCancelTime;

    /**
     * 缴费授权解约时间
     *
     * @mbggenerated
     */
    private String paymentCancelTime;

    /**
     * 还款授权解约时间
     *
     * @mbggenerated
     */
    private String repayCancelTime;

    /**
     * 自动投资单笔最大金额
     *
     * @mbggenerated
     */
    private String invesMaxAmt;

    /**
     * 自动债转单笔最大金额
     *
     * @mbggenerated
     */
    private String creditMaxAmt;

    /**
     * 缴费授权单笔最大金额
     *
     * @mbggenerated
     */
    private String paymentMaxAmt;

    /**
     * 还款授权单笔最大金额
     *
     * @mbggenerated
     */
    private String repayMaxAmt;

    /**
     * 删除标识 0: 未删除    1:已删除
     *
     * @mbggenerated
     */
    private Integer delFlag;

    /**
     * 投资交易上限
     *
     * @mbggenerated
     */
    private Integer txAmount;

    /**
     * 投资交易总额上限
     *
     * @mbggenerated
     */
    private Integer totAmount;

    /**
     * 创建者
     *
     * @mbggenerated
     */
    private Integer createUserId;

    /**
     * 更新者
     *
     * @mbggenerated
     */
    private Integer updateUserId;

    /**
     * 添加时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 更新时间
     *
     * @mbggenerated
     */
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