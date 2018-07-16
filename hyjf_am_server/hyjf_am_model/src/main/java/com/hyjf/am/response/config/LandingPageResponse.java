/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.config;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.config.LandingPageVo;

/**
 * @author tanyy
 * @version JobResponse, v0.1 2018/7/16 14:19
 */
public class LandingPageResponse extends Response<LandingPageVo> {
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
