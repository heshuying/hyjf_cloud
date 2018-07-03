package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.BorrowRecoverCustomizeVO;

/**
 * @author pangchengchao
 * @version BorrowRecoverResponse, v0.1 2018/7/2 15:53
 */
public class AdminBorrowRecoverResponse extends Response<BorrowRecoverCustomizeVO> {
    private Integer total;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
