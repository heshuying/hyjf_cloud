/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.user;

import com.hyjf.am.vo.user.UserAndSpreadsUserVO;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: BatchUserPortraitRequest, v0.1 2018/10/11 16:07
 */
public class BatchUserPortraitRequest {
    private List<UserAndSpreadsUserVO> userAndSpreadsUserVOList;

    public List<UserAndSpreadsUserVO> getUserAndSpreadsUserVOList() {
        return userAndSpreadsUserVOList;
    }

    public void setUserAndSpreadsUserVOList(List<UserAndSpreadsUserVO> userAndSpreadsUserVOList) {
        this.userAndSpreadsUserVOList = userAndSpreadsUserVOList;
    }
}
