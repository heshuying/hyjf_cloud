package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;

public class BorrowTenderResponse extends Response<BorrowTenderVO> {

    private Integer tenderCount;

    public Integer getTenderCount() {
        return tenderCount;
    }

    public void setTenderCount(Integer tenderCount) {
        this.tenderCount = tenderCount;
    }
}
