/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.BindCardExceptionCustomizeVO;

/**
 * @author: sunpeikai
 * @version: AdminBindCardExceptionResponse, v0.1 2018/10/9 11:24
 */
public class AdminBindCardExceptionResponse extends Response<BindCardExceptionCustomizeVO> {
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
