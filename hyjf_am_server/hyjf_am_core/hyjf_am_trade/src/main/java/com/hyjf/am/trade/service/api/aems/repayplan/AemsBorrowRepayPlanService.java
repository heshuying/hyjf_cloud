/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.api.aems.repayplan;

import com.hyjf.am.trade.dao.model.customize.AemsBorrowRepayPlanCustomize;
import com.hyjf.am.trade.service.BaseService;

import java.util.List;
import java.util.Map;

/**
 * AEMS系统:还款计划查询
 *
 * @author liuyang
 * @version AemsBorrowRepayPlanService, v0.1 2018/12/12 15:43
 */
public interface AemsBorrowRepayPlanService extends BaseService {

    /**
     * 根据机构编号,查询还款计划数量
     *
     * @param params
     * @return
     */
    Integer selectBorrowRepayPlanCountsByInstCode(Map<String, Object> params);

    /**
     * 根据机构编号,查询还款计划
     *
     * @param params
     * @return
     */
    List<AemsBorrowRepayPlanCustomize> selectBorrowRepayPlanList(Map<String, Object> params);
}
