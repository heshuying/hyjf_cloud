/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.nifa;

import com.hyjf.am.trade.service.BaseService;

/**
 * @author PC-LIUSHOUYI
 * @version NifaRepayInfoService, v0.1 2018/9/11 17:20
 */
public interface NifaRepayInfoService extends BaseService {

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
