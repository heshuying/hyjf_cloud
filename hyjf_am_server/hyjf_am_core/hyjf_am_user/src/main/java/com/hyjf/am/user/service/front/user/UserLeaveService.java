/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.front.user;

import com.hyjf.am.user.service.BaseService;

/**
 * @author wangjun
 * @version UserLeaveService, v0.1 2018/7/26 16:20
 */
public interface UserLeaveService extends BaseService {
    /**
     * 员工离职信息更新
     *
     * @param userId
     */
    boolean updateUserLeaveInfoFromCrm(String userId) throws Exception;
}
