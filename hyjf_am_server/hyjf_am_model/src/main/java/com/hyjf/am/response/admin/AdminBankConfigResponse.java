package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.BankConfigVO;

/**
 * @author by xiehuili on 2018/7/11.
 */
public class AdminBankConfigResponse extends Response<BankConfigVO> {

    private int recordTotal;

    public int getRecordTotal() {
        return recordTotal;
    }

    public void setRecordTotal(int recordTotal) {
        this.recordTotal = recordTotal;
    }
}
