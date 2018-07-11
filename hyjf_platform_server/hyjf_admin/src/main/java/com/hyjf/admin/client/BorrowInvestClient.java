/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import com.hyjf.am.resquest.admin.BorrowInvestRequest;
import com.hyjf.am.vo.admin.BorrowInvestCustomizeVO;

import java.util.List;

/**
 * @author wangjun
 * @version BorrowInvestClient, v0.1 2018/7/10 9:18
 */
public interface BorrowInvestClient {
    /**
     * 投资明细记录 总数COUNT
     *
     * @param borrowInvestRequest
     * @return
     */
    Integer countBorrowInvest(BorrowInvestRequest borrowInvestRequest);

    /**
     * 投资明细列表
     *
     * @param borrowInvestRequest
     * @return
     */
    List<BorrowInvestCustomizeVO> selectBorrowInvestList(BorrowInvestRequest borrowInvestRequest);

    /**
     * 投资明细列表合计
     *
     * @param borrowInvestRequest
     * @return
     */
    String selectBorrowInvestAccount(BorrowInvestRequest borrowInvestRequest);
}
