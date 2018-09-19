/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.consumer.impl;

import com.hyjf.cs.common.service.BaseServiceImpl;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.service.consumer.NifaRepayInfoMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author PC-LIUSHOUYI
 * @version NifaRepayInfoMessageServiceImpl, v0.1 2018/9/11 16:50
 */
@Service
public class NifaRepayInfoMessageServiceImpl extends BaseServiceImpl implements NifaRepayInfoMessageService {

    @Autowired
    AmTradeClient amTradeClient;

    /**
     * 借款人还款表
     *
     * @param borrowNid
     * @param repayPeriod
     * @return
     */
    @Override
    public boolean insertNifaRepayInfo(String borrowNid, Integer repayPeriod) {
        return amTradeClient.insertNifaRepayInfo(borrowNid,repayPeriod);
    }

    /**
     * 合同状态变更数据生成
     *
     * @param borrowNid
     * @param repayPeriod
     * @return
     */
    @Override
    public boolean insertNifaContractStatus(String borrowNid, Integer repayPeriod) {
        return amTradeClient.insertNifaContractStatus(borrowNid,repayPeriod);
    }

    /**
     * 出借人回款记录生成
     *
     * @param borrowNid
     * @param repayPeriod
     * @return
     */
    @Override
    public boolean insertNifaReceivedPayments(String borrowNid, Integer repayPeriod) {
        return amTradeClient.insertNifaReceivedPayments(borrowNid,repayPeriod);
    }
}
