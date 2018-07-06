/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.MerchantAccountVO;

/**
 * @author zhangqingqing
 * @version MerchantAccountResponse, v0.1 2018/7/5 13:52
 */
public class MerchantAccountResponse extends Response<MerchantAccountVO> {
    private int recordTotal;

    public int getRecordTotal() {
        return recordTotal;
    }

    public void setRecordTotal(int recordTotal) {
        this.recordTotal = recordTotal;
    }
}
