/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.message;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.UserOperationLogEntityVO;

/**
 * @author tanyy
 * @version OperationReportResponse, v0.1 2018/7/23 14:00
 */
public class UserOperationLogResponse extends Response<UserOperationLogEntityVO> {
    //总条数
    private int count;
    public int getCount() {
        return count;
    }

    public void setCount(int count) {

        this.count = count;
    }
}
