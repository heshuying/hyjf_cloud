/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.config;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.ChannelStatisticsDetailVO;
import com.hyjf.am.vo.config.AdminUtmReadPermissionsVO;

/**
 * @author tanyy
 * @version AdminUtmReadPermissionsResponse.java, v0.1 2018年7月17日 下午3:22:17
 */
public class AdminUtmReadPermissionsResponse extends Response<AdminUtmReadPermissionsVO>{
    private  Integer  count;
    public Integer getCount() {
        return count;
    }
    public void setCount(Integer count) {
        this.count = count;
    }

}
