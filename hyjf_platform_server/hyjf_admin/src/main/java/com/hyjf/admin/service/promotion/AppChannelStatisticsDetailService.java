package com.hyjf.admin.service.promotion;

import com.hyjf.am.vo.user.UtmPlatVO;

import java.util.List;

/**
 * @author lisheng
 * @version AppChannelStatisticsDetailService, v0.1 2018/9/25 11:27
 */

public interface AppChannelStatisticsDetailService {

    /**
     * 获取app渠道列表
     * @return
     */
    List<UtmPlatVO> getAppUtm();

}
