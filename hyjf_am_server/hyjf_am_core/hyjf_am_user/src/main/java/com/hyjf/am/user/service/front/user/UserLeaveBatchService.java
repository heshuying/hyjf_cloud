package com.hyjf.am.user.service.front.user;

import com.hyjf.am.user.dao.model.auto.User;
import com.hyjf.am.user.service.BaseService;

/**
 * @author wangjun
 * @version UserLeaveBatchService, v0.1 2018/6/12 14:58
 */
public interface UserLeaveBatchService extends BaseService {
    /**
     * 更新离职用户信息(controller调用，不开事务)
     */
    void updUserLeaveInfo();

    /**
     * 更新离职用户信息(service自己调用，每一个用户是单独事务)
     * @param user
     */
    void updateUserLeaveInfo(User user);
}
