/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.front.user;

import com.hyjf.am.user.service.BaseService;
import com.hyjf.am.vo.admin.UtmVO;
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
     * add by cwyang 20190322
     * 根据sourceID 获取渠道信息
     * @param soutceID
     * @return
     */
    UtmVO selectUtmBySourceId(Integer soutceID);
}
