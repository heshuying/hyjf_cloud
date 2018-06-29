package com.hyjf.am.trade.dao.mapper.customize.trade;

import com.hyjf.am.trade.dao.model.auto.Account;
import com.hyjf.am.trade.dao.model.auto.HjhPlan;

import java.util.Map;

/**
 * @Description 计划类
 * @Author sunss
 * @Date 2018/6/22 15:41
 */
public interface HjhPlanCustomizeMapper {

    /**
     * 更新相应的汇计划专属标的加入的用户的账户信息
     *
     * @param investAccount
     * @return
     */
    int updateOfPlanJoin(Account investAccount);

    /**
     * 加入计划后更新计划总信息
     * @param planUpdate
     * @return
     */
    int updateByDebtPlanId(Map<String,Object> planUpdate);

    /**
     * 更新还款后复投的开放额度
     * @param hjhPlan
     * @return
     */
    int updateRepayPlanAccount(HjhPlan hjhPlan);
}
