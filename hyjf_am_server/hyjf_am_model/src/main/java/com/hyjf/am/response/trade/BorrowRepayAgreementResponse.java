package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.BorrowRepayAgreementCustomizeVO;

/**
 * @version ApplyAgreementResponse, v0.1 2018/8/10 14:22
 * @Author: Zha Daojian
 */
public class BorrowRepayAgreementResponse extends Response<BorrowRepayAgreementCustomizeVO> {

    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
