/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.coupon.CouponConfigVO;

/**
 * @author yaoy
 * @version CouponConfigResponse, v0.1 2018/6/19 18:35
 */
public class CouponConfigResponse extends Response<CouponConfigVO> {
    private int count;

    private String selectedClientDisplay;

    private String selectedProjectDisplay;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getSelectedClientDisplay() {
        return selectedClientDisplay;
    }

    public void setSelectedClientDisplay(String selectdeClientDisplay) {
        this.selectedClientDisplay = selectdeClientDisplay;
    }

    public String getSelectedProjectDisplay() {
        return selectedProjectDisplay;
    }

    public void setSelectedProjectDisplay(String selectedProjectDisplay) {
        this.selectedProjectDisplay = selectedProjectDisplay;
    }
}
