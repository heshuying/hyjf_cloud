/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;
import com.hyjf.cs.trade.client.HjhDebtCreditClient;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version HjhDebtCreditClientImpl, v0.1 2018/6/26 11:59
 */
@Service
public class HjhDebtCreditClientImpl implements HjhDebtCreditClient {
    @Override
    public List<HjhDebtCreditVO> selectHjhDebtCreditListByAccedeOrderId(String accedeOrderId) {
        return null;
    }

    /**
     * 判断剩余待清算金额是否全部债转
     * 查询条件：PlanOrderIdEqualTo(accedeOrderId)
     *           BorrowNidEqualTo(borrowNid)
     *           CreditStatusNotEqualTo(3)
     *
     * @param accedeOrderId
     * @param borrowNid
     * @return
     */
    @Override
    public List<HjhDebtCreditVO> selectHjhDebtCreditListByOrderIdNid(String accedeOrderId, String borrowNid) {
        return null;
    }
}
