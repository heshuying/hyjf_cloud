package com.hyjf.am.resquest.trade;

/**
 * 还款申请更新Bean
 * @author hesy
 * @version RepayRequestUpdateRequest, v0.1 2018/7/10 10:35
 */
public class RepayRequestUpdateRequest {
    private String repayBeanData;

    private String bankCallBeanData;

    /**
     * 对分期标的，是否一次全部还款
     */
    private boolean isAllRepay;

    private int latePeriod;// 提交的逾期期数

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

    public boolean isAllRepay() {
        return isAllRepay;
    }

    public void setAllRepay(boolean allRepay) {
        isAllRepay = allRepay;
    }

    public int getLatePeriod() {
        return latePeriod;
    }

    public void setLatePeriod(int latePeriod) {
        this.latePeriod = latePeriod;
    }
}
