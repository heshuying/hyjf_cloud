package com.hyjf.am.trade.service;

import java.util.List;

public interface UserLargeScreenTwoCustomizeTService {

    /**
     * 坐席每日待回款金额表-数据批量删除
     * @param param
     */
    void deleteRepaymentPlan(List<String> param);
}
