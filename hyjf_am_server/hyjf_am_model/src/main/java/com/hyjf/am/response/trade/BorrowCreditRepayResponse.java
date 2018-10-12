package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.borrow.BorrowCreditRepayInfoVO;

import java.util.Map;

public class BorrowCreditRepayResponse extends Response<BorrowCreditRepayInfoVO> {

    private Integer count;

    private Map<String,String> sumData;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Map<String, String> getSumData() {
        return sumData;
    }

    public void setSumData(Map<String, String> sumData) {
        this.sumData = sumData;
    }
}
