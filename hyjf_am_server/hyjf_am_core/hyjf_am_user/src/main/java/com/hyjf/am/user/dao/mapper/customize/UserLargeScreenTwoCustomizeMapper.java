package com.hyjf.am.user.dao.mapper.customize;

import com.hyjf.am.vo.api.MonthDataStatisticsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserLargeScreenTwoCustomizeMapper {

    List<MonthDataStatisticsVO> getCurrentOwners();

    List<Integer> getCurrentOwnerUserIds(@Param("currentOwner") String currentOwner);
}
