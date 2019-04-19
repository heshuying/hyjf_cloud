/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.activity.ActivityUserRewardVO;

/**
 * @author yaoyong
 * @version ActivityUserRewardResponse, v0.1 2019/4/19 9:34
 */
public class ActivityUserRewardResponse extends Response<ActivityUserRewardVO> {

    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
