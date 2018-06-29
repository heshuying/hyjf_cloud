/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.batch;

import com.hyjf.am.user.dao.model.auto.UserInfo;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: UserPortraitBatchService, v0.1 2018/6/27 16:57
 */
public interface UserPortraitBatchService {
    List<UserInfo> searchUserInfoList();
    void updateUserPortraitInfo();
}
