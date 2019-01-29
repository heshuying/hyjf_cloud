/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.consumer.hgdatareport.nifa;

import com.hyjf.am.vo.hgreportdata.nifa.NifaBorrowInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowRecoverVO;
import com.hyjf.am.vo.trade.borrow.BorrowRepayVO;
import com.hyjf.cs.trade.service.BaseTradeService;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version NifaTwoRepayInfoMessageService, v0.1 2019/1/24 15:29
 */
public interface NifaTwoRepayInfoMessageService extends BaseTradeService {
    /**
     * 处理还款数据
     *
     * @param historyData
     * @param repayPeriod
     * @param borrow
     * @param borrowRepay
     * @param borrowRecoverList
     * @param recoverFee
     * @param lateCounts
     * @param nifaRepayInfoEntity
     * @return
     */
    boolean selectDualNifaRepayInfo(String historyData, Integer repayPeriod, BorrowAndInfoVO borrow, BorrowRepayVO borrowRepay, List<BorrowRecoverVO> borrowRecoverList, BigDecimal recoverFee, String lateCounts, NifaBorrowInfoVO nifaRepayInfoEntity);
}
