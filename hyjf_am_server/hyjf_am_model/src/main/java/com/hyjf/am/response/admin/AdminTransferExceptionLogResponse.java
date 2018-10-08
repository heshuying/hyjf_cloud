/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.AdminTransferExceptionLogCustomizeVO;

/**
 * @author jun
 * @version TransferExceptionLogResponse, v0.1 2018/7/10 11:09
 */
public class AdminTransferExceptionLogResponse extends Response<AdminTransferExceptionLogCustomizeVO> {

    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
