package com.hyjf.am.market.service;

import com.hyjf.am.vo.api.OperMonthPerformanceDataVO;

public interface UserLargeScreenTwoService {

    /**
     * 得到运营部用户月初站岗资金
     * @return
     */
    OperMonthPerformanceDataVO getOperMonthStartBalance();

    /**
     * 得到运营部用户当前站岗资金
     * @return
     */
    OperMonthPerformanceDataVO getOperMonthNowBalance();
}
