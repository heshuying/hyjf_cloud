/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.promotion;

import com.hyjf.am.resquest.admin.PlatformCountRequest;
import com.hyjf.am.user.dao.model.customize.PlatformUserCountCustomize;

import java.util.List;

/**
 * @author fq
 * @version PlatformCountService, v0.1 2018/8/13 9:54
 */
public interface PlatformCountService {
    /**
     * 获取用户相关信息
     * @return
     */
    List<PlatformUserCountCustomize> getUserInfo(PlatformCountRequest request);
}
