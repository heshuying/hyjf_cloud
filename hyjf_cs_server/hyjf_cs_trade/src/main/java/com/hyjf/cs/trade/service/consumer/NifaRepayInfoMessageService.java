/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.consumer;

import com.hyjf.cs.common.service.BaseService;

/**
 * @author PC-LIUSHOUYI
 * @version NifaRepayInfoMessageService, v0.1 2018/9/11 16:51
 */
public interface NifaRepayInfoMessageService extends BaseService {

    /**
     * 借款人还款表
     *
     * @param borrowNid
     * @param repayPeriod
     * @return
     */
    boolean insertNifaRepayInfo(String borrowNid, Integer repayPeriod);

    /**
     * 合同状态变更数据生成
     *
     * @param borrowNid
     * @param repayPeriod
     * @return
     */
    boolean insertNifaContractStatus(String borrowNid, Integer repayPeriod);

    /**
     * 出借人回款记录生成
     *
     * @param borrowNid
     * @param repayPeriod
     * @return
     */
    boolean insertNifaReceivedPayments(String borrowNid, Integer repayPeriod);
}
