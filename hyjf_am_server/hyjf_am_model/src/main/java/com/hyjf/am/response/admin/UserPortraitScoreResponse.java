/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.UserPortraitScoreCustomizeVO;

/**
 * @author yaoyong
 * @version UserPortraitScoreResponse, v0.1 2018/8/9 11:30
 */
public class UserPortraitScoreResponse extends Response<UserPortraitScoreCustomizeVO> {
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
