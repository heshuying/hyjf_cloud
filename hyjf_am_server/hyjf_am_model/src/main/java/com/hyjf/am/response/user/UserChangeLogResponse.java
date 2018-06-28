/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.user;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.user.UserChangeLogVO;

/**
 * @author nxl
 * @version UserChangeLogResponse, v0.1 2018/6/25 15:36
 */
public class UserChangeLogResponse extends Response<UserChangeLogVO> {
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
