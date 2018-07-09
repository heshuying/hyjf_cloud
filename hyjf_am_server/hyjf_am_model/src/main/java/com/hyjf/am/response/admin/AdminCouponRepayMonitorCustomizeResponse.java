/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.AdminCouponRepayMonitorCustomizeVO;

/**
 * @author zhangqingqing
 * @version AdminCouponRepayMonitorCustomizeResponse, v0.1 2018/7/9 15:01
 */
public class AdminCouponRepayMonitorCustomizeResponse extends Response<AdminCouponRepayMonitorCustomizeVO>{
    private int recordTotal;

    public int getRecordTotal() {
        return recordTotal;
    }

    public void setRecordTotal(int recordTotal) {
        this.recordTotal = recordTotal;
    }
}
