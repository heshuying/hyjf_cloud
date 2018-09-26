package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.datacollect.AppChannelStatisticsDetailVO;

/**
 * @author lisheng
 * @version AppChannelStatisticsDetailResponse, v0.1 2018/9/21 17:26
 */

public class AppChannelStatisticsDetailResponse extends Response<AppChannelStatisticsDetailVO> {
    private int recordTotal;

    public int getRecordTotal() {
        return recordTotal;
    }

    public void setRecordTotal(int recordTotal) {
        this.recordTotal = recordTotal;
    }
}
