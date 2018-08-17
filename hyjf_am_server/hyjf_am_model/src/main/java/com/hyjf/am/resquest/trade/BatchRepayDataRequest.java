package com.hyjf.am.resquest.trade;

/**
 * @author hesy
 * @version RepayDetailRequestVO, v0.1 2018/8/4 14:09
 */
public class BatchRepayDataRequest {
    String userId;
    String startDate;
    String endDate;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
