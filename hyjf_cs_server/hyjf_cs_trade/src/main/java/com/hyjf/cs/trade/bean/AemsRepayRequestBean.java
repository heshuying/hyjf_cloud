package com.hyjf.cs.trade.bean;

/**
 * aems用户还款请求request
 */
public class AemsRepayRequestBean extends BaseBean {

    /**
     * 电子账号
     */
    private String accountId;
    /**
     * 还款来源 1-借款人 2-担保账户
     */
    private String repaySouce;
    /**
     * 项目编号
     */
    private String productId;
    /**
     * 还款期数
     */
    private String repayPeriods;
    /**
     * 异步路径
     */
    private String bgUrl;

    @Override
    public String getAccountId() {
        return accountId;
    }

    @Override
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getRepaySouce() {
        return repaySouce;
    }

    public void setRepaySouce(String repaySouce) {
        this.repaySouce = repaySouce;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getRepayPeriods() {
        return repayPeriods;
    }

    public void setRepayPeriods(String repayPeriods) {
        this.repayPeriods = repayPeriods;
    }

    public String getBgUrl() {
        return bgUrl;
    }

    public void setBgUrl(String bgUrl) {
        this.bgUrl = bgUrl;
    }
}
