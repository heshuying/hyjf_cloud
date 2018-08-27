/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.userinfo;

import com.hyjf.cs.user.bean.SyncUserInfoRequestBean;
import com.hyjf.cs.user.bean.SyncUserInfoResultBean;
import com.hyjf.cs.user.service.BaseUserService;

/**
 * @author: sunpeikai
 * @version: ApiUserInfoService, v0.1 2018/8/27 9:58
 */
public interface ApiUserInfoService extends BaseUserService {

    /**
     * 第三方api用户信息查询
     * @auth sunpeikai
     * @param
     * @return
     */
    SyncUserInfoResultBean syncUserInfo(SyncUserInfoRequestBean requestBean);
}
