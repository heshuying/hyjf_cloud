/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.trade;

import com.hyjf.am.resquest.Request;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;

import java.math.BigDecimal;

/**
 * @author liubin
 * @version SaveCreditTenderLogRequest, v0.1 2018/7/4 14:22
 */
public class SaveCreditTenderLogRequest extends Request {
    HjhDebtCreditVO credit;
    HjhAccedeVO hjhAccede;
    String orderId;
    String orderDate;
    BigDecimal yujiAmoust;
    boolean isLast;

    public SaveCreditTenderLogRequest() {
    }

    public SaveCreditTenderLogRequest(HjhDebtCreditVO credit, HjhAccedeVO hjhAccede, String orderId, String orderDate, BigDecimal yujiAmoust, boolean isLast) {
        this.credit = credit;
        this.hjhAccede = hjhAccede;
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.yujiAmoust = yujiAmoust;
        this.isLast = isLast;
    }

    public HjhDebtCreditVO getCredit() {
        return credit;
    }

    public void setCredit(HjhDebtCreditVO credit) {
        this.credit = credit;
    }

    public HjhAccedeVO getHjhAccede() {
        return hjhAccede;
    }

    public void setHjhAccede(HjhAccedeVO hjhAccede) {
        this.hjhAccede = hjhAccede;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getYujiAmoust() {
        return yujiAmoust;
    }

    public void setYujiAmoust(BigDecimal yujiAmoust) {
        this.yujiAmoust = yujiAmoust;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }
}
