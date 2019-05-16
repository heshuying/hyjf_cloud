package com.hyjf.am.user.service.admin.screendata;

import com.hyjf.am.vo.trade.RepaymentPlanVO;

import java.util.List;

/**
 * @author lisheng
 * @version ScreenDataJobService, v0.1 2019/3/20 17:37
 */

public interface ScreenDataJobService {

    /**
     * 获取获取本月待回款用户id
     * @param startTime
     * @param endTime
     * @return
     */
    List<RepaymentPlanVO> findRepayUser( Integer startTime, Integer endTime,Integer limitStart,Integer limitEnd);

    /**
     * 获取本月待回款用户的数量
     * @param startTime
     * @param endTime
     * @return
     */
    Integer countRepayUser( Integer startTime, Integer endTime);

}
