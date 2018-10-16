/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.front.user;

import com.hyjf.am.resquest.trade.BatchUserPortraitQueryRequest;
import com.hyjf.am.user.dao.model.auto.UserLoginLog;
import com.hyjf.am.vo.user.UserAndSpreadsUserVO;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: UserPortraitBatchService, v0.1 2018/6/27 16:57
 */
public interface UserPortraitBatchService {
    /**
     * 查询需要更新用户画像的userInfo的list
     * */
    List<UserAndSpreadsUserVO> searchUserIdForUserPortrait(int flag);
    /**
     * 保存用户画像
     * */
    void saveUserPortrait(BatchUserPortraitQueryRequest request);
}
