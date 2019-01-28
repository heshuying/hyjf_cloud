/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.user;

import com.hyjf.am.vo.user.UserPortraitVO;

import java.util.List;

/**
 * @author yaoyong
 * @version UserPortraitCustomizeRequest, v0.1 2019/1/28 13:40
 */
public class UserPortraitCustomizeRequest {

    private List<String> userNames;

    private List<UserPortraitVO> userPortraitVOS;

    public List<String> getUserNames() {
        return userNames;
    }

    public void setUserNames(List<String> userNames) {
        this.userNames = userNames;
    }

    public List<UserPortraitVO> getUserPortraitVOS() {
        return userPortraitVOS;
    }

    public void setUserPortraitVOS(List<UserPortraitVO> userPortraitVOS) {
        this.userPortraitVOS = userPortraitVOS;
    }
}
