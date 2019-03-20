/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.trade.dao.model.customize.AdminPlanAccedeListCustomize;
import com.hyjf.am.vo.api.EchartsResultVO;
import com.hyjf.am.vo.api.UserCapitalDetailsVO;
import com.hyjf.am.vo.api.UserLargeScreenVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author tanyy
 * @version UserLargeScreenCustomizeMapper, v0.1 2018/7/12 11:02
 */
public interface UserLargeScreenCustomizeMapper {

    UserLargeScreenVO getTotalAmount();

    UserLargeScreenVO getScalePerformance();

    List<EchartsResultVO> getAchievementDistributionList();

    List<EchartsResultVO> getMonthScalePerformanceList();

    List<EchartsResultVO> getMonthReceivedPayments();

    List<UserCapitalDetailsVO> getUserCapitalDetails(@Param("star")Date star, @Param("end")Date end);
}
