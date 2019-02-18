/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.aems.repayplan;

import com.hyjf.am.vo.trade.AemsBorrowRepayPlanCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowRepayPlanVO;
import com.hyjf.am.vo.trade.borrow.RightBorrowVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanAssetVO;
import com.hyjf.cs.trade.bean.AemsBorrowRepayPlanRequestBean;
import com.hyjf.cs.trade.service.BaseTradeService;

import java.util.List;

/**
 * AEMS系统:还款计划查询Service
 *
 * @author liuyang
 * @version AemsBorrowRepayPlanService, v0.1 2018/12/12 13:54
 */
public interface AemsBorrowRepayPlanService extends BaseTradeService {
    /**
     * 根据机构编号,查询还款计划数量
     *
     * @param requestBean
     * @return
     */
    Integer selectBorrowRepayPlanCountsByInstCode(AemsBorrowRepayPlanRequestBean requestBean);

    /**
     * 根据机构编号,查询还款计划
     *
     * @param requestBean
     * @return
     */
    List<AemsBorrowRepayPlanCustomizeVO> selectBorrowRepayPlanList(AemsBorrowRepayPlanRequestBean requestBean);

    /**
     * 根据标的编号查询资产推送表
     *
     * @param borrowNid
     * @return
     */
    HjhPlanAssetVO selectHjhPlanAssetByBorrowNid(String borrowNid);

    /**
     * 根据标的编号查询还款计划
     *
     * @param borrowNid
     * @return
     */
    List<BorrowRepayPlanVO> selectBorrowRepayPlanByBorrowNid(String borrowNid);
    /**
     * 判断是否逾期 逾期或延期时返回false 逾期或延期时不计算提前还款提前还款减息
     * @param userId,borrowNid
     * @return
     */
    Boolean getFlag(RightBorrowVO borrowNid);
}
