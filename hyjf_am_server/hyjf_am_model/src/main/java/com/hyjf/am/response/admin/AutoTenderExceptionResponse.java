/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.AdminPlanAccedeListCustomizeVO;

/**
 * @author nxl
 * @version AutoTenderExceptionResponse, v0.1 2018/7/12 11:37
 */
public class AutoTenderExceptionResponse extends Response<AdminPlanAccedeListCustomizeVO> {
    private  Integer  count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
