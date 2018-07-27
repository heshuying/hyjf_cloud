/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service;

/**
 * @author wangjun
 * @version UserEntryService, v0.1 2018/7/26 14:04
 */
public interface UserEntryService extends BaseService {
    /**
     * 员工入职信息更新
     *
     * @param userId
     * @return
     */
    void updateUserEntryInfoFromCrm(String userId);
}
