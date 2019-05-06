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
     * 添加数据之前先清空表历史数据,防止表数据增长太快
     */
    void deleteAllParam();

    /**
     * 添加集合数据到 用户画像-屏幕二数据表
     * @param result
     */
    void insertResult(List<ScreenTwoParam> result);

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
     * 资金明细表-数据批量删除
     * @param param
     */
    void deleteUserOperate(List<String> param);

    /**
     * 查询坐席每日待回款金额表下的所有userId
     * @return
     */
    List<String> getRepaymentPlan();
}
