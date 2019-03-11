/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.hjh;

import com.hyjf.am.trade.dao.model.auto.HjhAccede;
import com.hyjf.am.trade.dao.model.customize.PlanDetailCustomize;
import com.hyjf.am.trade.service.BaseService;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version HjhAccedeService, v0.1 2018/6/25 10:24
 */
public interface HjhAccedeService extends BaseService {

    /**
     * 查询退出中和准备进入锁定期的计划
     * @return
     */
    List<HjhAccede> selectWaitQuitHjhList();
    /**
     * 判断用户是否有持有中的计划。如果有，则不能解除出借授权和债转授权
     * @param userId
     * @return
     */
	boolean canCancelAuth(int userId);

    /**
     * 
     * @param accedeOrderId
     * @return
     */
    HjhAccede getHjhAccedeByAccedeOrderId(String accedeOrderId);

    /**
     *
     * @param accedeOrderId
     * @return
     */
    HjhAccede doGetHjhAccedeByAccedeOrderId(String accedeOrderId);

    /**
     * 计算加入总数
     * @author zhangyk
     * @date 2018/6/27 19:10
     */
    int countAccede(String planNid);

    List<PlanDetailCustomize> getPlanDetail(String planNid);

    int updateHjhAccedeByPrimaryKey(HjhAccede hjhAccede);

    /**
     * 查询汇计划匹配期大于2天，短信预警
     * @author zhangyk
     * @date 2018/8/15 14:14
     */
    List<HjhAccede> getPlanMatchPeriodList();


    /**
     * 订单出借异常短信预警
     * @author zhangyk
     * @date 2018/8/15 16:25
     */
    List<HjhAccede> getPlanOrderInvestExceptionList();

    /**
     * 更新未进入锁定期的计划订单的匹配期hjhaccede
     * @return
     */
    boolean updateMatchDays();

    /**
     * 根据用户ID查询用户加入记录
     *
     * @param userId
     * @return
     */
    List<HjhAccede> selectHjhAccedeListByUserId(Integer userId);
}
