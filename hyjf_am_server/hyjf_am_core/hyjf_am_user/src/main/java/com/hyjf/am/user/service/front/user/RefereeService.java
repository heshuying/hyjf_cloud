/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.front.user;

import com.hyjf.am.user.service.BaseService;

/**
 * @author wangjun
 * @version RefereeService, v0.1 2018/7/27 13:47
 */
public interface RefereeService extends BaseService {
    /**
     * 根据用户id检索用户是否存在
     *
     * @param userId
     * @return
     */
    int countUserById(String userId);

    /**
     * 修改推荐人
     *
     * @param userId
     * @param spreadsUserId
     * @param operationName
     * @param ip
     * @return
     */
    void updateSpreadsUsers(String userId, String spreadsUserId, String operationName, String ip);

}
