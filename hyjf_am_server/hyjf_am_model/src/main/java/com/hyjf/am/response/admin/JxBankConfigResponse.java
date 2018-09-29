package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.JxBankConfigVO;

/**
 * @author by nxl on 2018/7/24.
 */
public class JxBankConfigResponse extends Response<JxBankConfigVO> {

    private int recordTotal;

    public int getRecordTotal() {
        return recordTotal;
    }

    public void setRecordTotal(int recordTotal) {
        this.recordTotal = recordTotal;
    }
}
