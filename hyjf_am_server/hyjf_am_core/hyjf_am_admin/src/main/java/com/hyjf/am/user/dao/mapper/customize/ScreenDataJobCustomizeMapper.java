package com.hyjf.am.user.dao.mapper.customize;

import com.hyjf.am.vo.trade.RepaymentPlanVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author lisheng
 * @version ScreenDataJobCustomizeMapper, v0.1 2019/3/20 17:39
 */

public interface ScreenDataJobCustomizeMapper {
    /**
     * 获取获取本月待回款用户id
     * @param map
     * @return
     */
    List<RepaymentPlanVO> findRepayUser(Map<String, Object> map);

    Integer countRepayUser(@Param("startTime") Integer startTime, @Param("endTime") Integer endTime);


}
