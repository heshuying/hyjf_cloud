package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.BorrowCreditInfoSumVO;
import com.hyjf.am.vo.admin.BorrowCreditInfoVO;

public class AdminBorrowCreditInfoResponse extends Response<BorrowCreditInfoVO> {

    private Integer count;

    private BorrowCreditInfoSumVO sumData;


    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public BorrowCreditInfoSumVO getSumData() {
        return sumData;
    }

    public void setSumData(BorrowCreditInfoSumVO sumData) {
        this.sumData = sumData;
    }
}
