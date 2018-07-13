package com.hyjf.am.resquest.trade;

import com.hyjf.am.vo.BasePage;

/**
 * @author hesy
 * @version BankCreditEndListRequest, v0.1 2018/7/12 14:23
 */
public class BankCreditEndListRequest extends BasePage {
    /**
     * 融资用户ID(检索用)
     */
    private String userIdSrch;
    /**
     * 投资资用户ID(检索用)
     */
    private String tenderUserIdSrch;
    /**
     * 批次号(检索用)
     */
    private String batchNoSrch;
    /**
     * 订单号(检索用)
     */
    private String orderIdSrch;
    /**
     * 状态(检索用)
     */
    private String statusSrch;
    /**
     * 检索条件 limitStart
     */
    private Integer limitStart;
    /**
     * 检索条件 limitEnd
     */
    private Integer limitEnd;

    public String getUserIdSrch() {
        return userIdSrch;
    }

    public void setUserIdSrch(String userIdSrch) {
        this.userIdSrch = userIdSrch;
    }

    public String getTenderUserIdSrch() {
        return tenderUserIdSrch;
    }

    public void setTenderUserIdSrch(String tenderUserIdSrch) {
        this.tenderUserIdSrch = tenderUserIdSrch;
    }

    public String getBatchNoSrch() {
        return batchNoSrch;
    }

    public void setBatchNoSrch(String batchNoSrch) {
        this.batchNoSrch = batchNoSrch;
    }

    public String getOrderIdSrch() {
        return orderIdSrch;
    }

    public void setOrderIdSrch(String orderIdSrch) {
        this.orderIdSrch = orderIdSrch;
    }

    public String getStatusSrch() {
        return statusSrch;
    }

    public void setStatusSrch(String statusSrch) {
        this.statusSrch = statusSrch;
    }

    public Integer getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(Integer limitStart) {
        this.limitStart = limitStart;
    }

    public Integer getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(Integer limitEnd) {
        this.limitEnd = limitEnd;
    }
}
