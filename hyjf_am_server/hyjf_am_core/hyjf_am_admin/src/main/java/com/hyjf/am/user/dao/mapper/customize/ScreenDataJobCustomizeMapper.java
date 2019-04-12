package com.hyjf.am.user.dao.mapper.customize;

import com.hyjf.am.vo.trade.RepaymentPlanVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lisheng
 * @version ScreenDataJobCustomizeMapper, v0.1 2019/3/20 17:39
 */

public interface ScreenDataJobCustomizeMapper {
    /**
     * 获取获取本月待回款用户id
     * @param startTime
     * @param endTime
     * @return
     */
    List<RepaymentPlanVO> findRepayUser(@Param("startTime") Integer startTime, @Param("endTime") Integer endTime,@Param("limitStart") Integer limitStart, @Param("limitEnd") Integer limitEnd);

    Integer countRepayUser(@Param("startTime") Integer startTime, @Param("endTime") Integer endTime);


}
