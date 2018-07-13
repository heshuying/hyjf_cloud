package com.hyjf.am.trade.dao.model.customize.admin;


/**
 * 
 * @author pcc
 */
public class UserWithdrawRecordCustomize {
    //汇盈金服用户名
    private String username;
    //用户姓名
    private String truename;
    //提现金额
    private String total;
    //实际到账
    private String credited;
    //手续费
    private String fee;
    //银行卡号
    private String cardNo;
    //所属银行
    private String bank;
    //提现状态
    private String status;
    //提现时间
    private String withdrawTime;
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getTruename() {
        return truename;
    }
    public void setTruename(String truename) {
        this.truename = truename;
    }
    public String getTotal() {
        return total;
    }
    public void setTotal(String total) {
        this.total = total;
    }
    public String getCredited() {
        return credited;
    }
    public void setCredited(String credited) {
        this.credited = credited;
    }
    public String getFee() {
        return fee;
    }
    public void setFee(String fee) {
        this.fee = fee;
    }
    public String getCardNo() {
        return cardNo;
    }
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
    public String getBank() {
        return bank;
    }
    public void setBank(String bank) {
        this.bank = bank;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getWithdrawTime() {
        return withdrawTime;
    }
    public void setWithdrawTime(String withdrawTime) {
        this.withdrawTime = withdrawTime;
    }
	
    
}
