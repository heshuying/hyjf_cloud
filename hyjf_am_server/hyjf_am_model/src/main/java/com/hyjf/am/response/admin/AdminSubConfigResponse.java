package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.SubCommissionListConfigVo;

/**
 * @author by xiehuili on 2018/7/9.
 */
public class AdminSubConfigResponse extends Response<SubCommissionListConfigVo> {

    private int recordTotal;

    public int getRecordTotal() {
        return recordTotal;
    }

    public void setRecordTotal(int recordTotal) {
        this.recordTotal = recordTotal;
    }

}
