package com.hyjf.cs.user.beans;

public class AutoStateQueryResultBean extends BaseResultBean {

    private static final long serialVersionUID = -5505697665806980856L;

    private String accountId;

    // 自动投标功能开通标志
    private String autoBidStatus;

    // 自动债转功能开通标志
    private String autoTransferStatus;

    // 预约取现功能开通标志
    private String agreeWithdrawStatus;

    // 缴费授权
    private String paymentAuthStatus;

    // 还款授权
    private String repayAuthStatus;

    // 自动投标到期日
    private String autoBidDeadline;

    // 缴费授权到期日
    private String paymentDeadline;

    // 还款授权到期日
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
