/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.market.dao.mapper.customize.market;

import com.hyjf.am.vo.api.OperMonthPerformanceDataVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface UserLargeScreenTwoCustomizeMapper {

    /**
     * 得到运营部用户月初站岗资金
     * @return
     */
    OperMonthPerformanceDataVO getOperMonthStartBalance(@Param("time") Date tiem);

    /**
     * 得到运营部用户月末站岗资金
     * @return
     */
    OperMonthPerformanceDataVO getOperMonthNowBalance(@Param("time") Date tiem);
}
