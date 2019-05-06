package com.hyjf.am.trade.service;

import com.hyjf.am.market.dao.model.auto.ScreenTwoParam;

import java.util.List;

public interface UserLargeScreenTwoCustomizeTService {

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
     * 资金明细表-数据批量删除
     * @param param
     */
    void deleteUserOperate(List<String> param);

    /**
     * 坐席每日待回款金额表-数据批量删除
     * @param param
     */
    void deleteRepaymentPlan(List<String> param);
}
