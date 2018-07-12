package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.BorrowCreditTenderSumVO;
import com.hyjf.am.vo.trade.borrow.BorrowCreditRepayVO;

public class BorrowCreditTenderResponse extends Response<BorrowCreditRepayVO> {

    private Integer count;

    private BorrowCreditTenderSumVO sumData;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public BorrowCreditTenderSumVO getSumData() {
        return sumData;
    }

    public void setSumData(BorrowCreditTenderSumVO sumData) {
        this.sumData = sumData;
    }
}
