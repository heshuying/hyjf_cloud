/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.trade;

import com.hyjf.am.vo.BaseVO;

import java.math.BigDecimal;

/**
 * @author wangjun
 * @version WeChatLandingPageVO, v0.1 2018/7/30 18:13
 */
public class WeChatLandingPageVO extends BaseVO {
    private BigDecimal profitSum;

    public BigDecimal getProfitSum() {
        return profitSum;
    }

    public void setProfitSum(BigDecimal profitSum) {
        this.profitSum = profitSum;
    }
}
