/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.HtlProductIntoRecordVO;

/**
 * @author: sunpeikai
 * @version: HtlProductIntoRecordResponse, v0.1 2018/7/25 15:42
 */
public class HtlProductIntoRecordResponse extends Response<HtlProductIntoRecordVO> {
    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
