package com.hyjf.am.response.user;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.user.BankOpenAccountRecordVO;

public class BankAccountRecordResponse extends Response<BankOpenAccountRecordVO> {
    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}