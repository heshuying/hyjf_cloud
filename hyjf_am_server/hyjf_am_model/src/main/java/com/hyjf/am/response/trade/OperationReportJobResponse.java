package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.OperationReportJobVO;

import java.math.BigDecimal;

public class OperationReportJobResponse extends Response<OperationReportJobVO> {
    private  int count;

    private BigDecimal totalAccount;//月交易金额

    private double account;//人均投资金额

    private float fullBillAverage;//平均满标时间
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public BigDecimal getTotalAccount() {
        return totalAccount;
    }

    public void setTotalAccount(BigDecimal totalAccount) {
        this.totalAccount = totalAccount;
    }

    public double getAccount() {
        return account;
    }

    public void setAccount(double account) {
        this.account = account;
    }

    public float getFullBillAverage() {
        return fullBillAverage;
    }

    public void setFullBillAverage(float fullBillAverage) {
        this.fullBillAverage = fullBillAverage;
    }
}
