/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.config.CouponCheckVO;

/**
 * @author yaoyong
 * @version CouponCheckResponse, v0.1 2018/7/4 11:01
 */
public class CouponCheckResponse extends Response<CouponCheckVO> {
    private int recordTotal;

    private boolean bool;

    public int getRecordTotal() {
        return recordTotal;
    }

    public void setRecordTotal(int recordTotal) {
        this.recordTotal = recordTotal;
    }

    public boolean isBool() {
        return bool;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }
}
