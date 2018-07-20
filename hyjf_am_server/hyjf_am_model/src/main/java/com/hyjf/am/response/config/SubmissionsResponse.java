package com.hyjf.am.response.config;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.config.SubmissionsCustomizeVO;

/**
 * @author lisheng
 * @version SubmissionsResponse, v0.1 2018/7/13 16:22
 */

public class SubmissionsResponse extends Response<SubmissionsCustomizeVO> {
    private int recordTotal;
    public int getRecordTotal() {
        return recordTotal;
    }
    public void setRecordTotal(int recordTotal) {
        this.recordTotal = recordTotal;
    }

}
