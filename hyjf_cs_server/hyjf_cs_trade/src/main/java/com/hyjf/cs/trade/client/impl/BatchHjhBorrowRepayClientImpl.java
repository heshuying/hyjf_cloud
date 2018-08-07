/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.trade.*;
import com.hyjf.am.response.user.HjhPlanResponse;
import com.hyjf.am.vo.trade.CalculateInvestInterestVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.borrow.BorrowRecoverVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.am.vo.trade.hjh.HjhRepayVO;
import com.hyjf.cs.trade.client.BatchHjhBorrowRepayClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BatchHjhBorrowRepayClientImpl, v0.1 2018/6/25 17:41
 */
@Service
public class BatchHjhBorrowRepayClientImpl implements BatchHjhBorrowRepayClient {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 计划锁定
     *
     * @param accedeOrderId
     */
    @Override
    public void updateForLock(String accedeOrderId) {
        String url = "http://AM-TRADE/am-trade/planLockQuit/updateLockRepayInfo/" + accedeOrderId;
        restTemplate.getForEntity(url, String.class);
    }

    /**
     * 计划退出
     *
     * @param accedeOrderId
     */
    @Override
    public void updateForQuit(String accedeOrderId) {
        String url = "http://AM-TRADE/am-trade/planLockQuit/updateQuitRepayInfo/" + accedeOrderId;
        restTemplate.getForEntity(url, String.class);
    }
}
