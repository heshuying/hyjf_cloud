package com.hyjf.am.trade.dao.mapper.customize.trade;

import java.util.List;
import java.util.Map;

import com.hyjf.am.resquest.admin.PlanListCustomizeRequest;
import com.hyjf.am.trade.dao.model.auto.Account;
import com.hyjf.am.trade.dao.model.auto.HjhAccede;
import com.hyjf.am.trade.dao.model.auto.HjhPlan;
import com.hyjf.am.trade.dao.model.auto.HjhRepay;
import com.hyjf.am.trade.dao.model.customize.trade.HjhPlanCustomize;
import com.hyjf.am.trade.dao.model.customize.trade.UserHjhInvistDetailCustomize;
import com.hyjf.am.vo.trade.hjh.HjhPlanSumVO;

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

    /**
     * 获取汇计划投资详情
     * @param params
     * @return
     */
    UserHjhInvistDetailCustomize selectUserHjhInvistDetail(Map<String,Object> params);

    /**
     * 更新汇计划还款信息
     * @param repayParam
     * @return
     */
    int updateHjhRepayForHjhRepay(HjhRepay repayParam);

    /**
     * 更新汇计划投资明细金额
     * @param hjhAccede
     * @return
     */
    int updateHjhAccedeForHjhProcess(HjhAccede hjhAccede);
    
    /**
     * 获取汇计划sum
     * @param hjhAccede
     * @return
     */
    HjhPlanSumVO getCalcSumByParam(PlanListCustomizeRequest request);

    /**
     * 获取app首页汇计划列表
     * @param params
     * @return
     */
    List<HjhPlanCustomize> getHjhPlanAppList(Map<String,Object> params);
}
