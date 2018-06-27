/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.trade.hjh.HjhDebtDetailVO;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version HjhDebtDetailClient, v0.1 2018/6/26 13:44
 */
public interface HjhDebtDetailClient {

    /**
     *
     * @param accedeOrderId
     * @return
     */
    List<HjhDebtDetailVO> selectHjhDebtDetailListByAccedeOrderId(String accedeOrderId);

}

