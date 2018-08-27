/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.SmsLogVO;

/**
 * @author fq
 * @version SmsLogResponse, v0.1 2018/8/15 14:14
 */
public class SmsLogResponse extends Response<SmsLogVO> {
    private int logCount;

    public int getLogCount() {
        return logCount;
    }

    public void setLogCount(int logCount) {
        this.logCount = logCount;
    }
}
