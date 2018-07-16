package com.hyjf.am.resquest.admin;

/**
 * @author hesy
 * @version ManualReverseAddRequest, v0.1 2018/7/14 15:48
 */
public class ManualReverseAddRequest {
    /**
     * 操作金额
     */
    private String amount;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 原交易流水号
     */
    private String seqNo;

    /**
     * 交易流水号
     */
    private String bankSeqNo;
    /**
     * 电子账号
     */
    private String accountId;
    /**
     * 收支类型 1收入 2支出
     */
    private String type;

    /**
     * 登录用户id
     */
    private String loginUserId;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getBankSeqNo() {
        return bankSeqNo;
    }

    public void setBankSeqNo(String bankSeqNo) {
        this.bankSeqNo = bankSeqNo;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLoginUserId() {
        return loginUserId;
    }

    public void setLoginUserId(String loginUserId) {
        this.loginUserId = loginUserId;
    }
}
