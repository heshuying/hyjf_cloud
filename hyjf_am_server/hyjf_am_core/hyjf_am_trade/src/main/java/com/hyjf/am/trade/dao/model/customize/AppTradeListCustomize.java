package com.hyjf.am.trade.dao.model.customize;

/**
 * @author pangchengchao
 * @version AppTradeListCustomize, v0.1 2018/8/6 15:11
 */
public class AppTradeListCustomize {
    /**
     *
     */
    private static final long serialVersionUID = -1457644265103686234L;
    // 类型：充值，提现，提现，投标成功，出借冻结等
    private String tradeType;
    // 时间
    private String tradeTime;
    // 出借时间年
    private String tradeYear;
    // 出借时间月
    private String tradeMonth;
    // 金额
    private String account;
    // 是否是银行交易明细(0:汇付,1:江西银行)
    private String isBank;
    //是否是月份 0（月份）1（交易明细数据）
    private String isMonth="1";
    //月份标题
    private String month;
    //可用余额
    private String bankBalance;
    //交易类型
    private String trade;
    //交易凭证号
    private String nid;
    //汇计划加入订单号
    private String accedeOrderId;
    /**
     * 构造方法
     */

    public AppTradeListCustomize() {
        super();
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getIsBank() {
        return isBank;
    }

    public void setIsBank(String isBank) {
        this.isBank = isBank;
    }

    public String getIsMonth() {
        return isMonth;
    }

    public void setIsMonth(String isMonth) {
        this.isMonth = isMonth;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getTradeYear() {
        return tradeYear;
    }

    public void setTradeYear(String tradeYear) {
        this.tradeYear = tradeYear;
    }

    public String getTradeMonth() {
        return tradeMonth;
    }

    public void setTradeMonth(String tradeMonth) {
        this.tradeMonth = tradeMonth;
    }

    public String getBankBalance() {
        return bankBalance;
    }

    public void setBankBalance(String bankBalance) {
        this.bankBalance = bankBalance;
    }

    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getAccedeOrderId() {
        return accedeOrderId;
    }

    public void setAccedeOrderId(String accedeOrderId) {
        this.accedeOrderId = accedeOrderId;
    }
}
