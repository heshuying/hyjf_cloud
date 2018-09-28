/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.AssetExceptionCustomizeVO;

/**
 * @author PC-LIUSHOUYI
 * @version AssetExceptionCustomizeResponse, v0.1 2018/9/28 18:45
 */
public class AssetExceptionCustomizeResponse extends Response<AssetExceptionCustomizeVO> {
    private int recordTotal;

    public int getRecordTotal() {
        return recordTotal;
    }

    public void setRecordTotal(int recordTotal) {
        this.recordTotal = recordTotal;
    }

}
