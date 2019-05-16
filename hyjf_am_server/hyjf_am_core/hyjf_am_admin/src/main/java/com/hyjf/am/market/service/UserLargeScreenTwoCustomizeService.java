package com.hyjf.am.market.service;


import com.hyjf.am.market.dao.model.auto.ScreenTwoParam;
import com.hyjf.am.user.dao.model.auto.CustomerTaskConfig;

import java.math.BigDecimal;
import java.util.List;

public interface UserLargeScreenTwoCustomizeService {

    /**
     * 查询有效坐席
     * @return
     */
    List<CustomerTaskConfig> getCustomer();

    /**
     * 查询坐席下的增资、提现率
     * @param customerList
     * @return
     */
    List<ScreenTwoParam> getCapitalIncreaseAndCashWithdrawalRateByCustomer(List<CustomerTaskConfig> customerList);

    /**
     * 查询运营部当前总站岗资金
     * @return
     */
    BigDecimal getOperNowBalance();

    /**
     * 查询所有运营部用户的userId
     * @return
     */
    List<String> getOperationUserId();

    /**
     * 查询运营部用户资金明细表下的所有userId
     * @return
     */
    List<String> getUserOperateListUserId();

    /**
     * 查询坐席每日待回款金额表下的所有userId
     * @return
     */
    List<String> getRepaymentPlan();
}
