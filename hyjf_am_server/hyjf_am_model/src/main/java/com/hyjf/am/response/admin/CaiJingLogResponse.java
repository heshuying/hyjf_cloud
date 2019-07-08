/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.CaiJingPresentationLogVO;

/**
 * @author yaoyong
 * @version CaiJingLogResponse, v0.1 2019/6/10 9:29
 */
public class CaiJingLogResponse extends Response<CaiJingPresentationLogVO> {

    private Integer logCount;

    public Integer getLogCount() {
        return logCount;
    }

    public void setLogCount(Integer logCount) {
        this.logCount = logCount;
    }
}
