/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.front.user;

import com.hyjf.am.user.dao.model.auto.VipInfo;
import com.hyjf.am.user.service.BaseService;

/**
 * @author zhangqingqing
 * @version VipInfoService, v0.1 2018/7/23 10:57
 */
public interface VipInfoService extends BaseService {
    /**
     * 获取用户vip信息
     * @param vipId
     * @return
     */
    VipInfo findVipInfoById(Integer vipId);
}
