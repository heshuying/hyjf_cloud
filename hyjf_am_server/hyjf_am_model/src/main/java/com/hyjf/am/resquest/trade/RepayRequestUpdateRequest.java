package com.hyjf.am.resquest.trade;

/**
 * 还款申请更新Bean
 * @author hesy
 * @version RepayRequestUpdateRequest, v0.1 2018/7/10 10:35
 */
public class RepayRequestUpdateRequest {
    private String repayBeanData;

    private String bankCallBeanData;

    public String getRepayBeanData() {
        return repayBeanData;
    }

    public void setRepayBeanData(String repayBeanData) {
        this.repayBeanData = repayBeanData;
    }

    public String getBankCallBeanData() {
        return bankCallBeanData;
    }

    public void setBankCallBeanData(String bankCallBeanData) {
        this.bankCallBeanData = bankCallBeanData;
    }
}
