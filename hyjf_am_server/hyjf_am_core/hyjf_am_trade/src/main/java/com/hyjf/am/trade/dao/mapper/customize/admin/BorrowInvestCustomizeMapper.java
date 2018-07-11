/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize.admin;

import com.hyjf.am.resquest.admin.BorrowInvestRequest;
import com.hyjf.am.trade.dao.model.customize.admin.BorrowInvestCustomize;

import java.util.List;

/**
 * @author wangjun
 * @version BorrowInvestCustomizeMapper, v0.1 2018/7/10 9:24
 */
public interface BorrowInvestCustomizeMapper {
    /**
     * 投资明细列表
     *
     * @param borrowInvestRequest
     * @return
     */
    List<BorrowInvestCustomize> selectBorrowInvestList(BorrowInvestRequest borrowInvestRequest);

    /**
     * 投资明细记录 总数COUNT
     *
     * @param borrowInvestRequest
     * @return
     */
    int countBorrowInvest(BorrowInvestRequest borrowInvestRequest);

    /**
     * 导出投资明细列表
     *
     * @param borrowInvestRequest
     * @return
     */
    List<BorrowInvestCustomize> exportBorrowInvestList(BorrowInvestRequest borrowInvestRequest);

    /**
     * 投资金额合计
     *
     * @param borrowInvestRequest
     * @return
     */
    String selectBorrowInvestAccount(BorrowInvestRequest borrowInvestRequest);

    /**
     * 投资金额合计值取得
     *
     * @param borrowInvestRequest
     * @return
     */
    String sumBorrowInvest(BorrowInvestRequest borrowInvestRequest);
}
