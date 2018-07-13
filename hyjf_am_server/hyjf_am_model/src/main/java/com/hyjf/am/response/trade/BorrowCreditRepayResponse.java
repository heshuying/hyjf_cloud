package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.borrow.BorrowCreditRepayInfoVO;

import java.util.Map;

public class BorrowCreditRepayResponse extends Response<BorrowCreditRepayInfoVO> {

    private Integer count;

    private Map<String,Object> sumData;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Map<String, Object> getSumData() {
        return sumData;
    }

    public void setSumData(Map<String, Object> sumData) {
        this.sumData = sumData;
    }
}
