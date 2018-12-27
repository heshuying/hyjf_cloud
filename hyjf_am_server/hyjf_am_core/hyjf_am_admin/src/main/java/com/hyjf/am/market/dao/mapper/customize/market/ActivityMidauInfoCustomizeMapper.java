/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.market.dao.mapper.customize.market;

import com.hyjf.am.market.dao.model.customize.ActivityMidauInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author yinhui
 * @version ActivityMidauRecodCustomizeMapper, v0.1 2018/9/8 15:13
 */
public interface ActivityMidauInfoCustomizeMapper {

    //查询加出借散标的出借信息
    List<ActivityMidauInfo> queryTenderList(@Param("orderid") String orderid, @Param("userid") Integer userid);

    List<ActivityMidauInfo> queryTenderRecoverList(@Param("orderid") String orderid, @Param("userid") Integer userid);

    //查询加入汇计划的出借信息
    List<ActivityMidauInfo> queryPlanList(@Param("orderid") String orderid, @Param("userid") Integer userid);
}
