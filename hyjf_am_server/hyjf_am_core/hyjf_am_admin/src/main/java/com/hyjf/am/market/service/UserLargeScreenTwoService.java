package com.hyjf.am.market.service;

import com.hyjf.am.vo.api.OperMonthPerformanceDataVO;

import java.util.List;

public interface UserLargeScreenTwoService {

    /**
     * 得到运营部用户月初站岗资金
     * @return
     */
    List<OperMonthPerformanceDataVO> getOperMonthStartBalance();

    /**
     * 得到运营部用户当前站岗资金
     * @return
     */
    List<OperMonthPerformanceDataVO> getOperMonthEndBalance();
}
