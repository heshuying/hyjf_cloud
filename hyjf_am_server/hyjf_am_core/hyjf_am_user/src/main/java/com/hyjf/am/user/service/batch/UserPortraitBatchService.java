/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.batch;

import com.hyjf.am.resquest.trade.BatchUserPortraitQueryRequest;
import com.hyjf.am.user.dao.model.auto.UserInfo;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: UserPortraitBatchService, v0.1 2018/6/27 16:57
 */
public interface UserPortraitBatchService {
    /**
     * 查询需要更新用户画像的userInfo的list
     * */
    List<UserInfo> searchUserInfoList();
    /**
     * 保存用户画像
     * */
    void saveUserPortrait(BatchUserPortraitQueryRequest request);
}
