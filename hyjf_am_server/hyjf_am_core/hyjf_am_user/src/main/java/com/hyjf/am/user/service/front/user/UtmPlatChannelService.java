/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.front.user;

import com.hyjf.am.user.service.BaseService;
import com.hyjf.am.vo.admin.UtmVO;
import com.hyjf.am.vo.datacollect.AppChannelStatisticsDetailVO;
import com.hyjf.am.vo.user.UtmPlatVO;

/**
 * 推广渠道相关
 *
 * @author liuyang
 * @version UtmPlatChannelService, v0.1 2018/10/18 16:39
 */
public interface UtmPlatChannelService extends BaseService {

    /**
     * 根据utmId查询渠道信息
     *
     * @param utmId
     * @return
     */
    UtmVO selectUtmByUtmId(Integer utmId);

    /**
     * 根据source_id查询UtmPlat
     *
     * @param sourceId
     * @return
     */
    UtmPlatVO selectUtmPlatBySourceId(Integer sourceId);

    /**
     * 根据用户ID查询是否App渠道过来的用户
     *
     * @param userId
     * @return
     */
    AppChannelStatisticsDetailVO selectAppChannelStatisticsDetailByUserId(Integer userId);
}
