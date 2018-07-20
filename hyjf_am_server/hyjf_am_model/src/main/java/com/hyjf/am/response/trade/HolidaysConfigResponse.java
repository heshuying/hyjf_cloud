/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.HolidaysConfigVO;

/**
 * @author yaoy
 * @version HolidaysConfigResponse, v0.1 2018/6/21 17:38
 */
public class HolidaysConfigResponse extends Response<HolidaysConfigVO> {

    private int recordTotal;

    public int getRecordTotal() {
        return recordTotal;
    }

    public void setRecordTotal(int recordTotal) {
        this.recordTotal = recordTotal;
    }
}
