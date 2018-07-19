package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;

/**
 * @author by xiehuili on 2018/7/12.
 */
public class AdminBorrowStyleResponse extends Response<BorrowStyleVO> {
    private Integer recordTotal;

    public Integer getRecordTotal() {
        return recordTotal;
    }

    public void setRecordTotal(Integer recordTotal) {
        this.recordTotal = recordTotal;
    }
}
