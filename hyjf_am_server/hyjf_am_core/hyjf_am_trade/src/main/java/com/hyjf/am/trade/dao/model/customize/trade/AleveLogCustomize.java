/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.model.customize.trade;

import com.hyjf.am.trade.dao.model.auto.AleveLog;

/**
 * @author wangjun
 * @version AleveLogCustomize, v0.1 2018/6/26 15:35
 */
public class AleveLogCustomize extends AleveLog {
    private String orderId;

    private Integer userId;

    private String startValdate;

    private String endValdate;

    private String startInpdate;

    private String endInpdate;

    private String startReldate;

    private String endReldate;

    private Integer limitStart;

    private Integer limitEnd;

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

    public String getStartValdate() {
        return startValdate;
    }

    public void setStartValdate(String startValdate) {
        this.startValdate = startValdate;
    }

    public String getEndValdate() {
        return endValdate;
    }

    public void setEndValdate(String endValdate) {
        this.endValdate = endValdate;
    }

    public String getStartInpdate() {
        return startInpdate;
    }

    public void setStartInpdate(String startInpdate) {
        this.startInpdate = startInpdate;
    }

    public String getEndInpdate() {
        return endInpdate;
    }

    public void setEndInpdate(String endInpdate) {
        this.endInpdate = endInpdate;
    }

    public String getStartReldate() {
        return startReldate;
    }

    public void setStartReldate(String startReldate) {
        this.startReldate = startReldate;
    }

    public String getEndReldate() {
        return endReldate;
    }

    public void setEndReldate(String endReldate) {
        this.endReldate = endReldate;
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
