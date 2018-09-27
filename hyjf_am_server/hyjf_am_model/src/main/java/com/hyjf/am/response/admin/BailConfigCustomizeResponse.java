/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.BailConfigCustomizeVO;

/**
 * @author PC-LIUSHOUYI
 * @version BailConfigCustomizeResponse, v0.1 2018/9/26 16:48
 */
public class BailConfigCustomizeResponse extends Response<BailConfigCustomizeVO> {
    private int recordTotal;

    public int getRecordTotal() {
        return recordTotal;
    }

    public void setRecordTotal(int recordTotal) {
        this.recordTotal = recordTotal;
    }
}
