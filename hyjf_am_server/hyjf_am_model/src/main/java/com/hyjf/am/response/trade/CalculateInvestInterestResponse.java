/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.CalculateInvestInterestVO;

import java.math.BigDecimal;

/**
 * @author PC-LIUSHOUYI
 * @version CalculateInvestInterestResponse, v0.1 2018/6/26 14:16
 */
public class CalculateInvestInterestResponse extends Response<CalculateInvestInterestVO> {
    private BigDecimal interestSum;

    public BigDecimal getInterestSum() {
        return interestSum;
    }

    public void setInterestSum(BigDecimal interestSum) {
        this.interestSum = interestSum;
    }
}
