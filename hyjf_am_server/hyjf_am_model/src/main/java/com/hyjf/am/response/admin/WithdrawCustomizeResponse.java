package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.finance.withdraw.WithdrawCustomizeVO;

public class WithdrawCustomizeResponse extends Response<WithdrawCustomizeVO>{

    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
