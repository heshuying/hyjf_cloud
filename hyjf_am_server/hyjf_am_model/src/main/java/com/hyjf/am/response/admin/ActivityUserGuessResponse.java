/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.AdminActivityUserGuessVO;

/**
 * @author yaoyong
 * @version ActivityUserGuessResponse, v0.1 2019/4/18 14:53
 */
public class ActivityUserGuessResponse extends Response<AdminActivityUserGuessVO> {

    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
