/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.api.aems.repayplan.impl;

import com.hyjf.am.trade.dao.model.customize.AemsBorrowRepayPlanCustomize;
import com.hyjf.am.trade.service.api.aems.repayplan.AemsBorrowRepayPlanService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * AEMS系统:还款计划查询
 *
 * @author liuyang
 * @version AemsBorrowRepayPlanServiceImpl, v0.1 2018/12/12 15:45
 */
@Service
public class AemsBorrowRepayPlanServiceImpl extends BaseServiceImpl implements AemsBorrowRepayPlanService {

    /**
     * 根据机构编号,查询还款计划数量
     *
     * @param params
     * @return
     */
    @Override
    public Integer selectBorrowRepayPlanCountsByInstCode(Map<String, Object> params) {
        return aemsBorrowRepayPlanCustomizeMapper.selectBorrowRepayPlanCountsByInstCode(params);
    }

    /**
     * 根据机构编号,查询还款计划
     *
     * @param params
     * @return
     */
    @Override
    public List<AemsBorrowRepayPlanCustomize> selectBorrowRepayPlanList(Map<String, Object> params) {
        return aemsBorrowRepayPlanCustomizeMapper.selectBorrowRepayPlanList(params);
    }
}
