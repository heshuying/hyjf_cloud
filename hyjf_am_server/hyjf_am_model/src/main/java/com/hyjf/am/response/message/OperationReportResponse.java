/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.message;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.datacollect.OperationReportVO;

/**
 * @author tanyy
 * @version OperationReportResponse, v0.1 2018/7/23 14:00
 */
public class OperationReportResponse extends Response<OperationReportVO> {
    private  Integer  count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
