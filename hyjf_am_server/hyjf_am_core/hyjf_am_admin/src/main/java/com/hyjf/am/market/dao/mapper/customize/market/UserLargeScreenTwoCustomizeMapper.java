/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.market.dao.mapper.customize.market;

import com.hyjf.am.vo.api.OperMonthPerformanceDataVO;

public interface UserLargeScreenTwoCustomizeMapper {

    /**
     * 得到运营部用户月初站岗资金
     * @return
     */
    OperMonthPerformanceDataVO getOperMonthStartBalance();

    /**
     * 得到运营部用户月末站岗资金
     * @return
     */
    OperMonthPerformanceDataVO getOperMonthNowBalance();
}
