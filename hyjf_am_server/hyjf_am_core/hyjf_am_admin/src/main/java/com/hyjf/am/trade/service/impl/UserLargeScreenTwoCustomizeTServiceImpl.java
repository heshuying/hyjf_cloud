package com.hyjf.am.trade.service.impl;

import com.hyjf.am.market.dao.mapper.customize.market.UserLargeScreenTwoCustomizeMapper;
import com.hyjf.am.market.dao.model.auto.ScreenTwoParam;
import com.hyjf.am.trade.service.UserLargeScreenTwoCustomizeTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther:dangzw
 * @Date:2019/5/6
 * @Description:
 */
@Service
public class UserLargeScreenTwoCustomizeTServiceImpl implements UserLargeScreenTwoCustomizeTService {

    @Autowired
    private UserLargeScreenTwoCustomizeMapper userLargeScreenTwoCustomizeMapper;

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
     * 资金明细表-数据批量删除
     * @param param
     */
    @Override
    public void deleteUserOperate(List<String> param) {
        userLargeScreenTwoCustomizeMapper.delUserOperate(param);
    }

    /**
     * 坐席每日待回款金额表-数据批量删除
     * @param param
     */
    @Override
    public void deleteRepaymentPlan(List<String> param) {
        userLargeScreenTwoCustomizeMapper.delRepaymentPlan(param);
    }
}
