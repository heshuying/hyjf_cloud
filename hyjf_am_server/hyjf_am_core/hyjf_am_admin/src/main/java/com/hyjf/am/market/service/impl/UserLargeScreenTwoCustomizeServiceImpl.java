package com.hyjf.am.market.service.impl;

import com.hyjf.am.market.dao.mapper.customize.market.UserLargeScreenTwoCustomizeMapper;
import com.hyjf.am.market.dao.model.auto.ScreenTwoParam;
import com.hyjf.am.market.service.UserLargeScreenTwoCustomizeService;
import com.hyjf.am.user.dao.model.auto.CustomerTaskConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Auther:dangzw
 * @Date:2019/5/6
 * @Description:用户画像-运营部投屏二数据batch获取、每日用户划转
 */
@Service
public class UserLargeScreenTwoCustomizeServiceImpl implements UserLargeScreenTwoCustomizeService {

    @Autowired
    private UserLargeScreenTwoCustomizeMapper userLargeScreenTwoCustomizeMapper;

    /**
     * 查询有效坐席
     * @return
     */
    @Override
    public List<CustomerTaskConfig> getCustomer() {
        return userLargeScreenTwoCustomizeMapper.getCustomer();
    }

    /**
     * 查询坐席下的增资、提现率
     * @param customerList
     * @return
     */
    @Override
    public List<ScreenTwoParam> getCapitalIncreaseAndCashWithdrawalRateByCustomer(List<CustomerTaskConfig> customerList) {
        return userLargeScreenTwoCustomizeMapper.getCapitalIncreaseAndCashWithdrawalRateByCustomer(customerList);
    }

    /**
     * 查询运营部当前总站岗资金
     * @return
     */
    @Override
    public BigDecimal getOperNowBalance() {
        return userLargeScreenTwoCustomizeMapper.getOperNowBalance();
    }

    /**
     * 添加数据之前先清空表历史数据,防止表数据增长太快
     */
    @Override
    public void deleteAllParam() {
        userLargeScreenTwoCustomizeMapper.deleteAllParam();
    }

    /**
     * 添加集合数据到 用户画像-屏幕二数据表
     * @param result
     */
    @Override
    public void insertResult(List<ScreenTwoParam> result) {
        userLargeScreenTwoCustomizeMapper.insertResult(result);
    }

    /**
     * 查询所有运营部用户的userId
     * @return
     */
    @Override
    public List<String> getOperationUserId() {
        return userLargeScreenTwoCustomizeMapper.getOperationUserId();
    }

    /**
     * 查询运营部用户资金明细表下的所有userId
     * @return
     */
    @Override
    public List<String> getUserOperateListUserId() {
        return userLargeScreenTwoCustomizeMapper.getUserOperateListUserId();
    }

    /**
     * 资金明细表-数据批量删除
     * @param param
     */
    @Override
    public void deleteUserOperate(List<String> param) {
        userLargeScreenTwoCustomizeMapper.delUserOperate(param);
    }

    /**
     * 查询坐席每日待回款金额表下的所有userId
     * @return
     */
    @Override
    public List<String> getRepaymentPlan() {
        return userLargeScreenTwoCustomizeMapper.getRepaymentPlan();
    }

    /**
     * 坐席每日待回款金额表-数据批量删除
     * @param param
     */
    @Override
    public void deleteUserOperateT(List<String> param) {
        userLargeScreenTwoCustomizeMapper.deleteUserOperateT(param);
    }
}
