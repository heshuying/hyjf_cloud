package com.hyjf.cs.user.bean;

import io.swagger.annotations.ApiModelProperty;

/**
 * @version AemsAuthStatusQueryResultBean, v0.1 2018/12/6 10:29
 * @Author: Zha Daojian
 */
public class AemsAuthStatusQueryResultBean extends BaseResultBean {

    private static final long serialVersionUID = -5505697665806980856L;

    private String accountId;

    @ApiModelProperty(value = "自动投标功能开通标志")
    private String autoBidStatus;

    @ApiModelProperty(value = "自动债转功能开通标志")
    private String autoTransferStatus;

    @ApiModelProperty(value = "预约取现功能开通标志")
    private String agreeWithdrawStatus;

    @ApiModelProperty(value = "缴费授权")
    private String paymentAuthStatus;

    @ApiModelProperty(value = "还款授权")
    private String repayAuthStatus;

    @ApiModelProperty(value = "自动投标到期日")
    private String autoBidDeadline;

    @ApiModelProperty(value = "缴费授权到期日")
    private String paymentDeadline;

    @ApiModelProperty(value = "还款授权到期日")
    private String repayDeadline;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAutoBidStatus() {
        return autoBidStatus;
    }

    public void setAutoBidStatus(String autoBidStatus) {
        this.autoBidStatus = autoBidStatus;
    }

    public String getAutoTransferStatus() {
        return autoTransferStatus;
    }

    public void setAutoTransferStatus(String autoTransferStatus) {
        this.autoTransferStatus = autoTransferStatus;
    }

    public String getAgreeWithdrawStatus() {
        return agreeWithdrawStatus;
    }

    public void setAgreeWithdrawStatus(String agreeWithdrawStatus) {
        this.agreeWithdrawStatus = agreeWithdrawStatus;
    }

    public String getPaymentAuthStatus() {
        return paymentAuthStatus;
    }

    public void setPaymentAuthStatus(String paymentAuthStatus) {
        this.paymentAuthStatus = paymentAuthStatus;
    }

    public String getRepayAuthStatus() {
        return repayAuthStatus;
    }

    public void setRepayAuthStatus(String repayAuthStatus) {
        this.repayAuthStatus = repayAuthStatus;
    }

    public String getAutoBidDeadline() {
        return autoBidDeadline;
    }

    public void setAutoBidDeadline(String autoBidDeadline) {
        this.autoBidDeadline = autoBidDeadline;
    }

    public String getPaymentDeadline() {
        return paymentDeadline;
    }

    public void setPaymentDeadline(String paymentDeadline) {
        this.paymentDeadline = paymentDeadline;
    }

    public String getRepayDeadline() {
        return repayDeadline;
    }

    public void setRepayDeadline(String repayDeadline) {
        this.repayDeadline = repayDeadline;
    }
}