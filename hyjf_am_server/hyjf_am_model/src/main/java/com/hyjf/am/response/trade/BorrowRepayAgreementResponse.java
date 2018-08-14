package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.BorrowRepayAgreementVO;
import com.hyjf.am.vo.trade.borrow.ApplyAgreementVO;

/**
 * @version ApplyAgreementResponse, v0.1 2018/8/10 14:22
 * @Author: Zha Daojian
 */
public class BorrowRepayAgreementResponse extends Response<BorrowRepayAgreementVO> {

    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

}
