/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin.promotion;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.datacollect.PcChannelStatisticsVO;

/**
 * @author fq
 * @version PcChannelStatisticsResponse, v0.1 2018/9/26 14:33
 */
public class PcChannelStatisticsResponse extends Response<PcChannelStatisticsVO> {
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
