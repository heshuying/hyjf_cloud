/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.PoundageCustomizeVO;

/**
 * @author: sunpeikai
 * @version: PoundageCustomizeResponse, v0.1 2018/9/3 15:11
 */
public class PoundageCustomizeResponse extends Response<PoundageCustomizeVO> {
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
