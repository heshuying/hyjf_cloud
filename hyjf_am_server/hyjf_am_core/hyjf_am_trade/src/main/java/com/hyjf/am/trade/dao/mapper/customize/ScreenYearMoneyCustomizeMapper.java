/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize;

import org.apache.ibatis.annotations.Param;

/**
 * @author yinhui
 * @version ActivityMidauRecodCustomizeMapper, v0.1 2018/9/8 15:13
 */
public interface ScreenYearMoneyCustomizeMapper {

    /**
     * 查询加出借散标的出借信息
     * @param orderId
     * @param userId
     * @return
     */
    String queryTenderList(@Param("orderId") String orderId, @Param("userId") Integer userId);


    /**
     * 查询加入汇计划的出借信息
     * @param orderId
     * @param userId
     * @return
     */
    String queryPlanList(@Param("orderId") String orderId, @Param("userId") Integer userId);


}
