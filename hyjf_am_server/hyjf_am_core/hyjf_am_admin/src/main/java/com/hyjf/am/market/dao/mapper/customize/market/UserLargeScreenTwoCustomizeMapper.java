/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.market.dao.mapper.customize.market;

import com.hyjf.am.market.dao.model.auto.ScreenTwoParam;
import com.hyjf.am.user.dao.model.auto.CustomerTaskConfig;

import java.math.BigDecimal;
import java.util.List;

public interface UserLargeScreenTwoCustomizeMapper {

    /**
     * 查询所有有效坐席姓名
     * @return
     */
    List<CustomerTaskConfig> getCustomer();

    /**
     * 通过坐席姓名查询增资和提现率
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
     * 查询坐席每日待回款金额表下的所有userId
     * @return
     */
    List<String> getRepaymentPlan();

    /**
     * 资金明细表-数据批量删除
     * @param delUolUserId
     */
    void delUserOperate(List<String> delUolUserId);

    /**
     * 坐席每日待回款金额表-数据批量删除
     * @param delRpUserId
     */
    void delRepaymentPlan(List<String> delRpUserId);
}
