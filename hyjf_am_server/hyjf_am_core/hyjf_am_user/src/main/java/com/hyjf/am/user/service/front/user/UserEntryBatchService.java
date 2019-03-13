package com.hyjf.am.user.service.front.user;

import com.hyjf.am.user.dao.model.auto.UserInfo;
import com.hyjf.am.user.service.BaseService;

/**
 * @author wangjun
 * @version UserEntryBatchService, v0.1 2018/6/12 14:58
 */
public interface UserEntryBatchService extends BaseService {
    /**
     * 更新入职用户信息(controller调用，不开事务)
     */
    void updUserEntryInfo();

    /**
     * 更新入职用户信息(service自己调用，每一个用户是单独事务)
     * @param record
     */
    void updateUserEntryInfo(UserInfo record);
}
