/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.am.resquest.admin.BorrowFullRequest;
import com.hyjf.am.vo.admin.BorrowFullCustomizeVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;

import java.util.List;

/**
 * @author wangjun
 * @version BorrowFullClient, v0.1 2018/7/6 10:32
 */
public interface BorrowFullClient {
    /**
     * 借款复审总条数
     *
     * @param borrowFullRequest
     * @return
     */
    Integer countBorrowFull(BorrowFullRequest borrowFullRequest);

    /**
     * 借款复审列表
     *
     * @param borrowFullRequest
     * @return
     */
    List<BorrowFullCustomizeVO> selectBorrowFullList(BorrowFullRequest borrowFullRequest);

    /**
     * 借款复审合计
     *
     * @param borrowFullRequest
     * @return
     */
    BorrowFullCustomizeVO sumAccount(BorrowFullRequest borrowFullRequest);

    /**
     * 复审详细信息
     *
     * @param borrowNid
     * @return
     */
    BorrowFullCustomizeVO getFullInfo(String borrowNid);

    /**
     * 复审详细列表条数
     *
     * @param borrowNid
     * @return
     */
    Integer countFullList(String borrowNid);

    /**
     * 复审详细列表
     *
     * @param borrowFullRequest
     * @return
     */
    List<BorrowFullCustomizeVO> getFullList(BorrowFullRequest borrowFullRequest);

    /**
     * 复审详细列表合计
     *
     * @param borrowNid
     * @return
     */
    BorrowFullCustomizeVO sumAmount(String borrowNid);

    /**
     * 根据UserID查询开户信息
     *
     * @param userId
     * @return
     */
    BankOpenAccountVO getBankOpenAccountByUserId(Integer userId);

    /**
     * 根据UserID查询账户信息
     *
     * @param userId
     * @return
     */
    AccountVO getAccountByUserId(int userId);

    /**
     * 标的复审数据更新
     *
     * @param borrowFullRequest
     * @return
     */
    AdminResult updateBorrowFull(BorrowFullRequest borrowFullRequest);

    /**
     * 流标
     *
     * @param borrowFullRequest
     * @return
     */
    AdminResult updateBorrowOver(BorrowFullRequest borrowFullRequest);
}
