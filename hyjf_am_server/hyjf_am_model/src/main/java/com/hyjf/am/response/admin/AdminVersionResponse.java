package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.VersionVO;

/**
 * @author by xiehuili on 2018/7/16.
 */
public class AdminVersionResponse extends Response<VersionVO> {
    private int recordTotal;

    public int getRecordTotal() {
        return recordTotal;
    }

    public void setRecordTotal(int recordTotal) {
        this.recordTotal = recordTotal;
    }
}
