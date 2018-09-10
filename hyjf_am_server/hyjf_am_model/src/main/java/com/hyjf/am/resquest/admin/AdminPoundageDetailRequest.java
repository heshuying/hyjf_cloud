/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
/**
 * @author: sunpeikai
 * @version: AdminPoundageDetailRequest, v0.1 2018/9/7 10:02
 */
public class AdminPoundageDetailRequest extends BasePage {
    /**
     * 实际分账时间查询条件
     */
    private Integer ledgerTimeSer;
    // 手续费分账配置id查询条件
    private Integer ledgerIdSer;
    private Integer poundageId;
    private String borrowNidSer;
    private String borrowTypeSer;
    private String addTimeStart;
    private String addTimeEnd;
    /**
     * 分页变量
     */
    private int limitStart = -1;
    private int limitEnd = -1;

    public Integer getLedgerTimeSer() {
        return ledgerTimeSer;
    }

    public void setLedgerTimeSer(Integer ledgerTimeSer) {
        this.ledgerTimeSer = ledgerTimeSer;
    }

    public Integer getLedgerIdSer() {
        return ledgerIdSer;
    }

    public void setLedgerIdSer(Integer ledgerIdSer) {
        this.ledgerIdSer = ledgerIdSer;
    }

    public Integer getPoundageId() {
        return poundageId;
    }

    public void setPoundageId(Integer poundageId) {
        this.poundageId = poundageId;
    }

    public String getBorrowNidSer() {
        return borrowNidSer;
    }

    public void setBorrowNidSer(String borrowNidSer) {
        this.borrowNidSer = borrowNidSer;
    }

    public String getBorrowTypeSer() {
        return borrowTypeSer;
    }

    public void setBorrowTypeSer(String borrowTypeSer) {
        this.borrowTypeSer = borrowTypeSer;
    }

    public String getAddTimeStart() {
        return addTimeStart;
    }

    public void setAddTimeStart(String addTimeStart) {
        this.addTimeStart = addTimeStart;
    }

    public String getAddTimeEnd() {
        return addTimeEnd;
    }

    public void setAddTimeEnd(String addTimeEnd) {
        this.addTimeEnd = addTimeEnd;
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
