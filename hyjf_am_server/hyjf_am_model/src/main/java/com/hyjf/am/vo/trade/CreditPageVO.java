package com.hyjf.am.vo.trade;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CreditPageVO extends BaseVO implements Serializable {
    private BigDecimal canCreditMoney;

    private BigDecimal inCreditMoney;

    private BigDecimal creditSuccessMoney;

    private  BigDecimal holdMoneyTotal;

    public BigDecimal getCanCreditMoney() {
        return canCreditMoney;
    }

    public void setCanCreditMoney(BigDecimal canCreditMoney) {
        this.canCreditMoney = canCreditMoney;
    }

    public BigDecimal getInCreditMoney() {
        return inCreditMoney;
    }

    public void setInCreditMoney(BigDecimal inCreditMoney) {
        this.inCreditMoney = inCreditMoney;
    }

    public BigDecimal getCreditSuccessMoney() {
        return creditSuccessMoney;
    }

    public void setCreditSuccessMoney(BigDecimal creditSuccessMoney) {
        this.creditSuccessMoney = creditSuccessMoney;
    }

    public BigDecimal getHoldMoneyTotal() {
        return holdMoneyTotal;
    }

    public void setHoldMoneyTotal(BigDecimal holdMoneyTotal) {
        this.holdMoneyTotal = holdMoneyTotal;
    }
}