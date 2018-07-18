package com.hyjf.am.trade.dao.mapper.customize.admin;

import com.hyjf.am.resquest.user.ChannelStatisticsDetailRequest;
import com.hyjf.am.vo.admin.ChannelStatisticsDetailVO;

import java.util.List;

/**
 * @author tanyy
 * @version ChannelStatisticsDetailCustomizeMapper, v0.1 2018/7/16 17:25
 */

public interface ChannelStatisticsDetailCustomizeMapper {

    List<ChannelStatisticsDetailVO> queryRecordList(ChannelStatisticsDetailRequest request);

}
