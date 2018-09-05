/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.batch.impl;

import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.cs.common.service.BaseServiceImpl;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.service.batch.BorrowRepayToHjhQuitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BorrowRepayToHjhQuitServiceImpl, v0.1 2018/6/25 9:38
 */
@Service
public class BorrowRepayToHjhQuitServiceImpl extends BaseServiceImpl implements BorrowRepayToHjhQuitService {

    @Autowired
    AmTradeClient amTradeClient;

    /**
     * 查询待退出计划的标的
     *
     * @return
     */
    @Override
    public List<HjhAccedeVO> selectWaitQuitHjhList() {
        return amTradeClient.selectWaitQuitHjhList();
    }
}
