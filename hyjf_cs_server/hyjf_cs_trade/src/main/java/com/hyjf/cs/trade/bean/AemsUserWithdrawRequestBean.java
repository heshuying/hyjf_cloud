package com.hyjf.cs.trade.bean;

/**
 * Aems接口:用户提现请求Bean
 * @author Zha Daojian
 * @date 2018/12/12 11:02
 * @param
 * @return
 **/
public class AemsUserWithdrawRequestBean extends BaseBean {

    /**
     * 用户电子账户号
     */
    private String accountId;
    /**
     * 银行卡号
     */
    private String cardNo;

    /**
     * 提现金额
     */
    private String account;
    /**
     * 银联行号
     */
    private String payAllianceCode;
    
    /**
     * 状态
     */
    private String status;

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPayAllianceCode() {
        return payAllianceCode;
    }

    public void setPayAllianceCode(String payAllianceCode) {
        this.payAllianceCode = payAllianceCode;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    
}
