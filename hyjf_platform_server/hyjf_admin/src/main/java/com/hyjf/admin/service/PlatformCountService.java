/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.admin.beans.request.PlatformCountRequestBean;
import com.hyjf.am.response.admin.PlatformCountCustomizeResponse;

/**
 * @author fuqiang
 * @version PlatformCountService, v0.1 2018/7/18 17:58
 */
public interface PlatformCountService {
    /**
     * 
     * @param requestBean
     * @return
     */
    PlatformCountCustomizeResponse searchAction(PlatformCountRequestBean requestBean);
}
