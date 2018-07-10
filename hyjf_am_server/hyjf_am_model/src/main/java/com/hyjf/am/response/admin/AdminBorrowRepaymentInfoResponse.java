package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.BorrowRepaymentInfoCustomizeVO;

/**
 * @author pangchengchao
 * @version AdminBorrowRepaymentInfoResponse, v0.1 2018/7/9 9:35
 */
public class AdminBorrowRepaymentInfoResponse  extends Response<BorrowRepaymentInfoCustomizeVO> {
    private Integer total;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
