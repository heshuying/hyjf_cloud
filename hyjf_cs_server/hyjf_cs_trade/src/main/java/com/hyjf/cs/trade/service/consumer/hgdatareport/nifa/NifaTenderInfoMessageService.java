/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.consumer.hgdatareport.nifa;

import com.hyjf.am.vo.hgreportdata.nifa.NifaBorrowInfoVO;
import com.hyjf.am.vo.hgreportdata.nifa.NifaBorrowerInfoVO;
import com.hyjf.am.vo.hgreportdata.nifa.NifaTenderInfoVO;
import com.hyjf.am.vo.trade.borrow.*;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.cs.trade.service.BaseTradeService;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version NifaTenderInfoMessageService, v0.1 2019/1/16 13:38
 */
public interface NifaTenderInfoMessageService extends BaseTradeService {

    /**
     * 查询该借款数据是否上报完成
     *
     * @param borrowNid
     * @param msgBody
     * @return
     */
    NifaBorrowInfoVO selectNifaBorrowInfoByBorrowNid(String borrowNid, String msgBody);

    /**
     * 处理借款企业信息
     *
     * @param borrowUsers
     * @param borrower
     * @param borrowLevelStr
     * @param bank
     * @param nifaBorrowerInfoVO
     * @return
     */
    boolean selectDualNifaBorrowerUserInfo(BorrowUserVO borrowUsers, String borrower, String borrowLevelStr, String bank, NifaBorrowerInfoVO nifaBorrowerInfoVO);

    /**
     * 借款企业信息插入
     *
     * @param nifaBorrowerInfoVO
     */
    boolean insertNifaBorrowerUserInfo(NifaBorrowerInfoVO nifaBorrowerInfoVO);

    /**
     * 处理借款个人信息
     *
     * @param borrowManinfo
     * @param borrower
     * @param borrowLevelStr
     * @param bank
     * @param nifaBorrowerInfoVO
     * @return
     */
    boolean selectDualNifaBorrowerManInfo(BorrowManinfoVO borrowManinfo, String borrower, String borrowLevelStr, String bank, NifaBorrowerInfoVO nifaBorrowerInfoVO);

    /**
     * 处理投资人信息
     *
     * @param borrow
     * @param borrowTenderVO
     * @param userInfoVO
     * @param nifaTenderInfoVO
     * @return
     */
    boolean selectDualNifaTenderInfo(BorrowAndInfoVO borrow, BorrowTenderVO borrowTenderVO, UserInfoVO userInfoVO, NifaTenderInfoVO nifaTenderInfoVO);

    /**
     * 插入投资人信息
     *
     * @param nifaTenderInfoVO
     */
    boolean insertNifaTenderInfo(NifaTenderInfoVO nifaTenderInfoVO);

    /**
     * 处理借款信息
     *
     * @param historyData
     * @param borrow
     * @param borrowRepay
     * @param borrowRecoverList
     * @param recoverFee
     * @param lateCounts
     * @param nifaBorrowInfoVO
     * @return
     */
    boolean selectDualNifaBorrowInfo(String historyData, BorrowAndInfoVO borrow, BorrowRepayVO borrowRepay, List<BorrowRecoverVO> borrowRecoverList, BigDecimal recoverFee, String lateCounts, NifaBorrowInfoVO nifaBorrowInfoVO);

    /**
     * 插入借款信息
     *
     * @param nifaBorrowInfoVO
     */
    boolean insertNifaBorrowInfo(NifaBorrowInfoVO nifaBorrowInfoVO);

    /**
     * 更新借款人状态为已上报
     *
     * @param borrowNid
     * @param msgBody
     */
    boolean updateNifaBorrowerInfo(String borrowNid, String msgBody);

    /**
     * 更新投资人状态为已上报
     *
     * @param borrowNid
     * @param msgBody
     */
    boolean updateNifaTenderInfo(String borrowNid, String msgBody);
}
