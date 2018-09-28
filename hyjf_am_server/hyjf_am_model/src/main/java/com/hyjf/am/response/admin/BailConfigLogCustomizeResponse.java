/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.BailConfigLogCustomizeVO;

/**
 * @author PC-LIUSHOUYI
 * @version BailConfigLogCustomizeResponse, v0.1 2018/9/28 11:20
 */
public class BailConfigLogCustomizeResponse extends Response<BailConfigLogCustomizeVO> {
    private int recordTotal;

    public int getRecordTotal() {
        return recordTotal;
    }

    public void setRecordTotal(int recordTotal) {
        this.recordTotal = recordTotal;
    }
}
