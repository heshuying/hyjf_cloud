/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.borrow;

import com.hyjf.am.resquest.admin.BorrowInvestRequest;
import com.hyjf.am.trade.dao.model.auto.BorrowRecover;
import com.hyjf.am.trade.dao.model.auto.TenderAgreement;
import com.hyjf.am.trade.dao.model.customize.BorrowInvestCustomize;
import com.hyjf.am.trade.dao.model.customize.BorrowListCustomize;
import com.hyjf.am.trade.dao.model.customize.WebProjectRepayListCustomize;
import com.hyjf.am.trade.dao.model.customize.WebUserInvestListCustomize;
import com.hyjf.am.trade.service.BaseService;
import com.hyjf.am.vo.admin.BorrowInvestCustomizeExtVO;

import java.util.List;

/**
 * @author wangjun
 * @version BorrowInvestService, v0.1 2018/7/10 9:35
 */
public interface BorrowInvestService extends BaseService {
    /**
     * 出借明细记录 总数COUNT
     *
     * @param borrowInvestRequest
     * @return
     */
    int countBorrowFirst(BorrowInvestRequest borrowInvestRequest);

    /**
     * 出借明细列表
     *
     * @param borrowInvestRequest
     * @return
     */
    List<BorrowInvestCustomize> selectBorrowInvestList(BorrowInvestRequest borrowInvestRequest);

    /**
     * 出借明细列表合计
     *
     * @param borrowInvestRequest
     * @return
     */
    String selectBorrowInvestAccount(BorrowInvestRequest borrowInvestRequest);

    /**
     * 导出出借明细列表
     *
     * @param borrowInvestRequest
     * @return
     */
    List<BorrowInvestCustomize> getExportBorrowInvestList(BorrowInvestRequest borrowInvestRequest);

    /**
     * 获取用户出借协议
     *
     * @param nid
     * @return
     */
    TenderAgreement selectTenderAgreementByNid(String nid);

    /**
     * 获取用户出借协议
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
     * 标的出借信息
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

    /**
     * 根据订单id获取订单详情
     * @param nid
     * @return
     */
    BorrowInvestCustomizeExtVO selectBorrowInvestByNid(String nid);

}
