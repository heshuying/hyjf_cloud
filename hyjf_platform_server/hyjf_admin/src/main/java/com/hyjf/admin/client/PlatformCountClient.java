/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import com.hyjf.admin.beans.request.PlatformCountRequestBean;
import com.hyjf.am.response.admin.PlatformCountCustomizeResponse;

/**
 * @author fq
 * @version PlatformCountClient, v0.1 2018/8/9 15:09
 */
public interface PlatformCountClient {
    /**
     * 获取列表
     * @param requestBean
     * @return
     */
    PlatformCountCustomizeResponse searchAction(PlatformCountRequestBean requestBean);
}
