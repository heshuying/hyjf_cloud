/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service;

import com.hyjf.am.trade.dao.model.auto.Account;
import com.hyjf.am.trade.dao.model.auto.HjhInstConfig;
import com.hyjf.am.trade.dao.model.auto.HjhLabel;
import com.hyjf.am.trade.dao.model.auto.HjhPlan;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;

import java.util.List;

/**
 * @author fuqiang
 * @version HjhPlanService, v0.1 2018/6/13 17:23
 */
public interface HjhPlanService {
    /**
     * 获取现金贷资产方信息配置
     *
     * @param instCode
     * @return
     */
    List<HjhInstConfig> selectHjhInstConfigByInstCode(String instCode);

    /**
     * 获取标签
     *
     * @param borrowStyle
     * @return
     */
    List<HjhLabel> seleHjhLabelByBorrowStyle(String borrowStyle);

    /**
     * @Description 根据计划编号查询计划
     * @Author sunss
     * @Version v0.1
     * @Date 2018/6/19 14:08
     */
    HjhPlan getHjhPlanByNid(String planNid);

    /**
     * 插入计划明细表
     * @param planAccede
     * @param userAccount
     * @return
     */
    int insertHJHPlanAccede(HjhAccedeVO planAccede, Account userAccount);
}
