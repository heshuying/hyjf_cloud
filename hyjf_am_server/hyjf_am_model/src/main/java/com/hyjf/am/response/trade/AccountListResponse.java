package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.account.AccountListVO;

/**
 * @author pangchengchao
 * @version AccountListResponse, v0.1 2018/6/13 10:52
 */
public class AccountListResponse extends Response<AccountListVO> {
    //总记录数
    private Integer totalRecord;

    public Integer getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(Integer totalRecord) {
        this.totalRecord = totalRecord;
    }
}
