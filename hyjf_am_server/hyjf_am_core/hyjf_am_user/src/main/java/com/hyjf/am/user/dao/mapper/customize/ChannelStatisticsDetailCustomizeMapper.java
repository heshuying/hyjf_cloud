package com.hyjf.am.user.dao.mapper.customize;

import com.hyjf.am.resquest.user.ChannelStatisticsDetailRequest;
import com.hyjf.am.vo.admin.ChannelStatisticsDetailVO;

import java.util.List;
import java.util.Map;

/**
 * @author lisheng
 * @version SubmissionsCustomizeMapper, v0.1 2018/7/13 17:25
 */

public interface ChannelStatisticsDetailCustomizeMapper {
    /**
     * 取得数据总数量
     *
     * @return
     */
    int countRecordTotal(ChannelStatisticsDetailRequest request);

    /**
     * 根据查询条件 取得数据
     * @param request
     * @return
     */
    List<ChannelStatisticsDetailVO> queryRecordList(ChannelStatisticsDetailRequest request);

}
