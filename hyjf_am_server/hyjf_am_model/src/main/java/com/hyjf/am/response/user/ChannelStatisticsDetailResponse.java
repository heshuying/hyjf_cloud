/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.user;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.ChannelStatisticsDetailVO;

/**
 * @author libin
 * @version AccedeListResponse.java, v0.1 2018年7月7日 下午3:22:17
 */
public class ChannelStatisticsDetailResponse extends Response<ChannelStatisticsDetailVO>{
    private  Integer  count;
    public Integer getCount() {
        return count;
    }
    public void setCount(Integer count) {
        this.count = count;
    }

}
