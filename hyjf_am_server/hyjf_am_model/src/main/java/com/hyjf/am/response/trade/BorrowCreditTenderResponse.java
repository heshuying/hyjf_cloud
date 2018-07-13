package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.BorrowCreditRepaySumVO;
import com.hyjf.am.vo.trade.borrow.BorrowCreditRepayVO;

public class BorrowCreditTenderResponse extends Response<BorrowCreditRepayVO> {

    private Integer count;

    private BorrowCreditRepaySumVO sumData;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public BorrowCreditRepaySumVO getSumData() {
        return sumData;
    }

    public void setSumData(BorrowCreditRepaySumVO sumData) {
        this.sumData = sumData;
    }
}
