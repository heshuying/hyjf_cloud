/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;

/**
 * @author PC-LIUSHOUYI
 * @version HjhDebtCreditResponse, v0.1 2018/6/27 14:40
 */
public class HjhDebtCreditResponse extends Response<HjhDebtCreditVO> {

    private Integer tenderCount;


    public Integer getTenderCount() {
        return tenderCount;
    }

    public void setTenderCount(Integer tenderCount) {
        this.tenderCount = tenderCount;
    }
}
