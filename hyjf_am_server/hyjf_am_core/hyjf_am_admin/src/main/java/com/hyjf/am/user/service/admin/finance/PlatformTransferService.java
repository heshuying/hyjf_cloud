/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.finance;

import com.hyjf.am.user.dao.model.auto.User;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: PlatformTransferService, v0.1 2018/7/9 13:55
 */
public interface PlatformTransferService {
    /**
     * 根据userId列表查询user列表
     * @auth sunpeikai
     * @param userIds 用户id列表
     * @return
     */
    List<User> findUserListByUserIds(String userIds);
}
