/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.coupon.CouponRepayMonitorVO;

/**
 * @author yaoy
 * @version CouponRepayMonitorResponse, v0.1 2018/6/21 17:18
 */
public class CouponRepayMonitorResponse extends Response<CouponRepayMonitorVO> {
    private int updateFlag;

    private int insertFlag;

    public int getUpdateFlag() {
        return updateFlag;
    }

    public void setUpdateFlag(int updateFlag) {
        this.updateFlag = updateFlag;
    }

    public int getInsertFlag() {
        return insertFlag;
    }

    public void setInsertFlag(int insertFlag) {
        this.insertFlag = insertFlag;
    }
}
