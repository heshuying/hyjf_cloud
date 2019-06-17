package com.hyjf.am.user.dao.mapper.customize;

import com.hyjf.am.response.trade.ScreenTransferResponse;
import com.hyjf.am.vo.api.MonthDataStatisticsVO;
import com.hyjf.am.vo.screen.ScreenTransferVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserLargeScreenTwoCustomizeMapper {

    List<MonthDataStatisticsVO> getCurrentOwners();

    List<Integer> getCurrentOwnerUserIds(@Param("currentOwner") String currentOwner);

    /**
     * @Author walter.limeng
     * @Description //获取投屏采集的所有的用户信息
     * @Date 16:19 2019-05-29
     * @Param [userList]
     * @return com.hyjf.am.response.trade.ScreenTransferResponse
     **/
    List<ScreenTransferVO> getScreenTransferData(List<ScreenTransferVO> userList);
}
