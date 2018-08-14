/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.PlatformCountCustomizeVO;

/**
 * @author fuqiang
 * @version PlatformUserCountCustomizeResponse, v0.1 2018/7/18 19:06
 */
public class PlatformCountCustomizeResponse extends Response<PlatformCountCustomizeVO> {
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
