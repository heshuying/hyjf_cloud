package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.BorrowRepaymentInfoListCustomizeVO;

/**
 * @author pangchengchao
 * @version AdminBorrowRepaymentInfoListResponse, v0.1 2018/7/10 10:59
 */
public class AdminBorrowRepaymentInfoListResponse extends Response<BorrowRepaymentInfoListCustomizeVO> {
    private Integer total;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
