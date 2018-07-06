/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;

import java.io.Serializable;

/**
 * @author: sunpeikai
 * @version: CustomerTransferListRequest, v0.1 2018/7/6 14:39
 */
public class CustomerTransferListRequest extends BasePage implements Serializable {
    private static final long serialVersionUID = 1L;
    // 订单号
    private String orderIdSrch;
    // 交易类型
    private String transferTypeSrch;
    // 转出账户
    private String outUserNameSrch;
    // 对账标识
    private String reconciliationIdSrch;
    // 转账状态
    private String statusSrch;
    /** 转账时间开始值 */
    private String transferTimeStart;
    /** 转账时间结束值 */
    private String transferTimeEnd;
    /** 操作时间开始值 */
    private String opreateTimeStart;
    /** 操作时间结束值 */
    private String opreateTimeEnd;

    /**
     * 检索条件 limitStart
     */
    private int limitStart = -1;
    /**
     * 检索条件 limitEnd
     */
    private int limitEnd = -1;

    public String getOrderIdSrch() {
        return orderIdSrch;
    }

    public void setOrderIdSrch(String orderIdSrch) {
        this.orderIdSrch = orderIdSrch;
    }

    public String getTransferTypeSrch() {
        return transferTypeSrch;
    }

    public void setTransferTypeSrch(String transferTypeSrch) {
        this.transferTypeSrch = transferTypeSrch;
    }

    public String getOutUserNameSrch() {
        return outUserNameSrch;
    }

    public void setOutUserNameSrch(String outUserNameSrch) {
        this.outUserNameSrch = outUserNameSrch;
    }

    public String getReconciliationIdSrch() {
        return reconciliationIdSrch;
    }

    public void setReconciliationIdSrch(String reconciliationIdSrch) {
        this.reconciliationIdSrch = reconciliationIdSrch;
    }

    public String getStatusSrch() {
        return statusSrch;
    }

    public void setStatusSrch(String statusSrch) {
        this.statusSrch = statusSrch;
    }

    public String getTransferTimeStart() {
        return transferTimeStart;
    }

    public void setTransferTimeStart(String transferTimeStart) {
        this.transferTimeStart = transferTimeStart;
    }

    public String getTransferTimeEnd() {
        return transferTimeEnd;
    }

    public void setTransferTimeEnd(String transferTimeEnd) {
        this.transferTimeEnd = transferTimeEnd;
    }

    public String getOpreateTimeStart() {
        return opreateTimeStart;
    }

    public void setOpreateTimeStart(String opreateTimeStart) {
        this.opreateTimeStart = opreateTimeStart;
    }

    public String getOpreateTimeEnd() {
        return opreateTimeEnd;
    }

    public void setOpreateTimeEnd(String opreateTimeEnd) {
        this.opreateTimeEnd = opreateTimeEnd;
    }

    public int getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    public int getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(int limitEnd) {
        this.limitEnd = limitEnd;
    }
}
