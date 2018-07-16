package com.hyjf.am.resquest.trade;

import com.hyjf.am.resquest.Request;

/**
 * @author hesy
 * @version BankCreditEndUpdateRequest, v0.1 2018/7/12 17:18
 */
public class BankCreditEndUpdateRequest extends Request {
    /**
     * 用户Id
     */
    private Integer userId;
    /**
     * 总笔数
     */
    private String txCounts;
    /**
     * 期数
     */
    private String txDate;
    /**
     * 批次
     */
    private String batchNo;
    /**
     * 批次状态
     */
    private String status;
    /**
     * 订单号
     */
    private String orderId;

    public String getTxCounts() {
        return txCounts;
    }

    public void setTxCounts(String txCounts) {
        this.txCounts = txCounts;
    }

    public String getTxDate() {
        return txDate;
    }

    public void setTxDate(String txDate) {
        this.txDate = txDate;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
