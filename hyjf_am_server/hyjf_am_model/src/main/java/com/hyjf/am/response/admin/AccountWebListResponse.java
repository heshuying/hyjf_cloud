/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.datacollect.AccountWebListVO;

/**
 * @author zhangqingqing
 * @version AccountWebListResponse, v0.1 2018/7/6 14:00
 */
public class AccountWebListResponse extends Response<AccountWebListVO> {
    private int recordTotal;

    public int getRecordTotal() {
        return recordTotal;
    }

    public void setRecordTotal(int recordTotal) {
        this.recordTotal = recordTotal;
    }
}
