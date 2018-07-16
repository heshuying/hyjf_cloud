/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.config;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.config.JobsVo;

/**
 * @author fuqiang
 * @version JobResponse, v0.1 2018/7/12 14:19
 */
public class JobResponse extends Response<JobsVo> {
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
