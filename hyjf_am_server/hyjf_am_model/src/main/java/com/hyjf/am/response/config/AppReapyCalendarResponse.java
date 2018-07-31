/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.config;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.market.AppReapyCalendarResultVO;

/**
 * @author dangzw
 * @version AppReapyCalendarResponse, v0.1 2018/7/27 14:15
 */
public class AppReapyCalendarResponse extends Response<AppReapyCalendarResultVO> {

    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
