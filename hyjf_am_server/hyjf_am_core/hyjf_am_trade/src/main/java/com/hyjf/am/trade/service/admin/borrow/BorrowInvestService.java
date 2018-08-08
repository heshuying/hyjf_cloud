/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.borrow;

import com.hyjf.am.resquest.admin.BorrowInvestRequest;
import com.hyjf.am.trade.dao.model.auto.BorrowRecover;
import com.hyjf.am.trade.dao.model.auto.TenderAgreement;
import com.hyjf.am.trade.dao.model.customize.admin.BorrowInvestCustomize;
import com.hyjf.am.trade.dao.model.customize.admin.BorrowListCustomize;
import com.hyjf.am.trade.dao.model.customize.trade.WebProjectRepayListCustomize;
import com.hyjf.am.trade.dao.model.customize.trade.WebUserInvestListCustomize;
import com.hyjf.am.trade.service.BaseService;

import java.util.List;

/**
 * @author wangjun
 * @version BorrowInvestService, v0.1 2018/7/10 9:35
 */
public interface BorrowInvestService extends BaseService {
    /**
     * 投资明细记录 总数COUNT
     *
     * @param borrowInvestRequest
     * @return
     */
    int countBorrowFirst(BorrowInvestRequest borrowInvestRequest);

    /**
     * 投资明细列表
     *
     * @param borrowInvestRequest
     * @return
     */
    List<BorrowInvestCustomize> selectBorrowInvestList(BorrowInvestRequest borrowInvestRequest);

    /**
     * 投资明细列表合计
     *
     * @param borrowInvestRequest
     * @return
     */
    String selectBorrowInvestAccount(BorrowInvestRequest borrowInvestRequest);

    /**
     * 导出投资明细列表
     *
     * @param borrowInvestRequest
     * @return
     */
    List<BorrowInvestCustomize> exportBorrowInvestList(BorrowInvestRequest borrowInvestRequest);

    /**
     * 投资金额合计值取得
     *
     * @param borrowInvestRequest
     * @return
     */
    String sumBorrowInvest(BorrowInvestRequest borrowInvestRequest);

    /**
     * 获取用户投资协议
     *
     * @param nid
     * @return
     */
    TenderAgreement selectTenderAgreementByNid(String nid);

    /**
     * 获取用户投资协议
     *
     * @param userId
     * @param borrowNid
     * @param nid
     * @return
     */
    BorrowRecover selectBorrowRecover(Integer userId, String borrowNid, String nid);

    /**
     * 获取借款列表
     *
     * @param borrowNid
     * @return
     */
    List<BorrowListCustomize> selectBorrowList(String borrowNid);

    /**
     * 标的投资信息
     *
     * @param borrowInvestRequest
     * @return
     */
    List<WebUserInvestListCustomize> selectUserInvestList(BorrowInvestRequest borrowInvestRequest);

    /**
     * 标的放款记录-分期 count
     *
     * @param borrowInvestRequest
     * @return
     */
    int countProjectRepayPlanRecordTotal(BorrowInvestRequest borrowInvestRequest);

    /**
     * 标的放款记录-分期
     *
     * @param borrowInvestRequest
     * @return
     */
    List<WebProjectRepayListCustomize> selectProjectRepayPlanList(BorrowInvestRequest borrowInvestRequest);

    /**
     * 更新标的放款记录
     *
     * @param borrowInvestRequest
     * @return
     */
    int updateBorrowRecover(BorrowInvestRequest borrowInvestRequest);
}
