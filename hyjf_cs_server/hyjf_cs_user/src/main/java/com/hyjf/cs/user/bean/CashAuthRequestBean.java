package com.hyjf.cs.user.bean;


public class CashAuthRequestBean extends BaseBean{
    
    
    private String channel; //交易渠道
    private String accountId; //电子账户号
    private String bitMap; //维护标识
    private String autoBid; //开通自动投标功能标志
    private String autoTransfer; //开通自动债转功能标志
    private String agreeWithdraw; //开通预约取现功能标志
    private String directConsume; //开通无密消费功能标识
    private String retUrl;//同步地址
    private String notifyUrl; //异步地址
    private String transactionUrl;//返回交易页面链接
    private String forgotPwdUrl; // 忘记交易密码链接
    
    @Override
    public String getChannel() {
        return channel;
    }
    @Override
    public void setChannel(String channel) {
        this.channel = channel;
    }
    @Override
    public String getAccountId() {
        return accountId;
    }
    @Override
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
    public String getBitMap() {
        return bitMap;
    }
    public void setBitMap(String bitMap) {
        this.bitMap = bitMap;
    }
    public String getAutoBid() {
        return autoBid;
    }
    public void setAutoBid(String autoBid) {
        this.autoBid = autoBid;
    }
    public String getAutoTransfer() {
        return autoTransfer;
    }
    public void setAutoTransfer(String autoTransfer) {
        this.autoTransfer = autoTransfer;
    }
    public String getAgreeWithdraw() {
        return agreeWithdraw;
    }
    public void setAgreeWithdraw(String agreeWithdraw) {
        this.agreeWithdraw = agreeWithdraw;
    }
    public String getDirectConsume() {
        return directConsume;
    }
    public void setDirectConsume(String directConsume) {
        this.directConsume = directConsume;
    }
    @Override
    public String getRetUrl() {
        return retUrl;
    }
    @Override
    public void setRetUrl(String retUrl) {
        this.retUrl = retUrl;
    }
    public String getNotifyUrl() {
        return notifyUrl;
    }
    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }
    public String getTransactionUrl() {
        return transactionUrl;
    }
    public void setTransactionUrl(String transactionUrl) {
        this.transactionUrl = transactionUrl;
    }
	@Override
    public String getForgotPwdUrl() {
		return forgotPwdUrl;
	}
	@Override
    public void setForgotPwdUrl(String forgotPwdUrl) {
		this.forgotPwdUrl = forgotPwdUrl;
	}
    
    
}
