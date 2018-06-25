/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl;

import com.hyjf.am.trade.dao.mapper.auto.BorrowRepayPlanMapper;
import com.hyjf.am.trade.dao.model.auto.BorrowRepayPlan;
import com.hyjf.am.trade.dao.model.auto.BorrowRepayPlanExample;
import com.hyjf.am.trade.service.BorrowRepayPlanService;
import com.hyjf.am.vo.trade.borrow.BorrowRepayPlanVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BorrowRepayPlanServiceImpl, v0.1 2018/6/23 11:42
 */
@Service
public class BorrowRepayPlanServiceImpl implements BorrowRepayPlanService {

    @Autowired
    BorrowRepayPlanMapper borrowRepayPlanMapper;

    @Override
    public List<BorrowRepayPlan> selectBorrowRepayPlanList(String borrowNid, Integer repaySmsReminder) {
        BorrowRepayPlanExample example = new BorrowRepayPlanExample();
        BorrowRepayPlanExample.Criteria cra = example.createCriteria();
        if (StringUtils.isNotBlank(borrowNid)) {
            cra.andBorrowNidEqualTo(borrowNid);
        }
        cra.andRepaySmsReminderEqualTo(repaySmsReminder);
        // 未还款
        cra.andRepayStatusEqualTo(0);
        // 还款期数升序
        example.setOrderByClause("repay_period ASC");
        return this.borrowRepayPlanMapper.selectByExample(example);
    }

    @Override
    public Integer updateBorrowRepayPlan(BorrowRepayPlanVO borrowRepayPlanVO) {
        BorrowRepayPlan borrowRepayPlan = new BorrowRepayPlan();
        BeanUtils.copyProperties(borrowRepayPlanVO,borrowRepayPlan);
        boolean result = this.borrowRepayPlanMapper.updateByPrimaryKeySelective(borrowRepayPlan) > 0 ? true : false;
        return result ?1:0;
    }
}
