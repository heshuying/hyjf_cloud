/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.PoundageDetailVO;

/**
 * @author: sunpeikai
 * @version: AdminPoundageDetailResponse, v0.1 2018/9/7 10:07
 */
public class AdminPoundageDetailResponse extends Response<PoundageDetailVO> {
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
