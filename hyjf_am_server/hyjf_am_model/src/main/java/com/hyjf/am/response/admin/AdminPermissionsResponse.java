/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.AdminPermissionsVO;

/**
 * @author: sunpeikai
 * @version: AdminPermissionsResponse, v0.1 2018/9/5 14:08
 */
public class AdminPermissionsResponse extends Response<AdminPermissionsVO> {
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
