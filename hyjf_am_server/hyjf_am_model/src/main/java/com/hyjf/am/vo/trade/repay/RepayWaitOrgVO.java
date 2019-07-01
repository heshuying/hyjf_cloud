package com.hyjf.am.vo.trade.repay;

import com.hyjf.am.vo.BaseVO;

import java.math.BigDecimal;

/**
 * 垫付机构待还金额统计VO
 * @author hesy
 * @version RepayListCustomizeVO, v0.1 2018/6/27 15:46
 */
public class RepayWaitOrgVO extends BaseVO {
    /**
     * 待还本金总额
     */
    private BigDecimal capitalWaitTotal;

    /**
     * 待还利息总额
     */
    private BigDecimal interestWaitTotal;

    /**
     * 待还本息总额
     */
    private BigDecimal waitTotal;

    public BigDecimal getCapitalWaitTotal() {
        return capitalWaitTotal;
    }

    public void setCapitalWaitTotal(BigDecimal capitalWaitTotal) {
        this.capitalWaitTotal = capitalWaitTotal;
    }

    public BigDecimal getInterestWaitTotal() {
        return interestWaitTotal;
    }

    public void setInterestWaitTotal(BigDecimal interestWaitTotal) {
        this.interestWaitTotal = interestWaitTotal;
    }

    public BigDecimal getWaitTotal() {
        return waitTotal;
    }

    public void setWaitTotal(BigDecimal waitTotal) {
        this.waitTotal = waitTotal;
    }
}
