/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.app;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.datacollect.AppChannelStatisticsVO;

/**
 * @author yaoyong
 * @version AppChannelStatisticsResponse, v0.1 2018/9/21 11:36
 */
public class AppChannelStatisticsResponse extends Response<AppChannelStatisticsVO> {
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
