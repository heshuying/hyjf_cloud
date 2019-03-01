/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.trade.borrow;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author PC-LIUSHOUYI
 * @version BorrowRepayPlanVO, v0.1 2018/6/22 15:27
 */
public class BorrowRepayPlanBeanVO extends BorrowRepayPlanVO implements Serializable {

    private static final long serialVersionUID = -1229013393923143695L;
    private String repayTimeStr;

    private String borrowStatus;

    private String ip;

    public String getRepayTimeStr() {
        return repayTimeStr;
    }

    public void setRepayTimeStr(String repayTimeStr) {
        this.repayTimeStr = repayTimeStr;
    }

    public String getBorrowStatus() {
        return borrowStatus;
    }

    public void setBorrowStatus(String borrowStatus) {
        this.borrowStatus = borrowStatus;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
