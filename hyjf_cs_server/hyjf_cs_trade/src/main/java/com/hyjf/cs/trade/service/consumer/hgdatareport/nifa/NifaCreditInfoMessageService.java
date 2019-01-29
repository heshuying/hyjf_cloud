/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.consumer.hgdatareport.nifa;

import com.hyjf.am.vo.hgreportdata.nifa.NifaCreditInfoVO;
import com.hyjf.am.vo.hgreportdata.nifa.NifaCreditTransferVO;
import com.hyjf.am.vo.trade.BorrowCreditVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.cs.trade.service.BaseTradeService;

import java.math.BigDecimal;

/**
 * @author PC-LIUSHOUYI
 * @version NifaCreditInfoMessageService, v0.1 2019/1/24 16:39
 */
public interface NifaCreditInfoMessageService extends BaseTradeService {

    /**
     * 散标债转出让人数据处理
     *
     * @param assignPay
     * @param creditNid
     * @param usersInfo
     * @param nifaCreditTransferEntity
     * @return
     */
    boolean selectDualNifaCreditTransfer(BigDecimal assignPay, String creditNid, UserInfoVO usersInfo, NifaCreditTransferVO nifaCreditTransferEntity);

    /**
     * 处理散标债转数据
     *
     * @param assignRepayAccount
     * @param assignPay
     * @param creditFee
     * @param size
     * @param historyData
     * @param borrowCredit
     * @param borrow
     * @param nifaCreditInfoEntity
     * @return
     */
    boolean selectDualNifaCreditInfo(BigDecimal assignRepayAccount, BigDecimal assignPay, BigDecimal creditFee, Integer size, String historyData, BorrowCreditVO borrowCredit, BorrowAndInfoVO borrow, NifaCreditInfoVO nifaCreditInfoEntity);

    /**
     * 处理计划债转上送信息
     *
     * @param assignRepayAccount
     * @param creditFee
     * @param assignPay
     * @param tenderCapital
     * @param tenderInterest
     * @param size
     * @param borrowNid
     * @param historyData
     * @param hjhDebtCredit
     * @param borrow
     * @param nifaCreditInfoEntity
     * @return
     */
    boolean selectDualNifaHjhFirstCreditInfo(BigDecimal assignRepayAccount, BigDecimal creditFee, BigDecimal assignPay, BigDecimal tenderCapital, BigDecimal tenderInterest, Integer size, String borrowNid, String historyData, HjhDebtCreditVO hjhDebtCredit, BorrowAndInfoVO borrow, NifaCreditInfoVO nifaCreditInfoEntity);

    /**
     * 散标债转数据拉取
     *
     * @param creditNid
     * @return
     */
    NifaCreditInfoVO selectNifaCreditInfoByCreditNid(String creditNid);

    /**
     * 保存散标债转承接人信息
     *
     * @param nifaCreditTransferEntity
     * @return
     */
    boolean insertNifaCreditTransfer(NifaCreditTransferVO nifaCreditTransferEntity);

    /**
     * 保存散标债转信息
     *
     * @param nifaCreditInfoEntity
     * @return
     */
    boolean insertNifaCreditInfo(NifaCreditInfoVO nifaCreditInfoEntity);
}
