/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.user;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.user.UserAliasVO;

/**
 * @author fuqiang
 * @version UserAliasResponse, v0.1 2018/5/8 10:52
 */
public class UserAliasResponse extends Response<UserAliasVO> {
    // 设备类型统计用户人数
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
