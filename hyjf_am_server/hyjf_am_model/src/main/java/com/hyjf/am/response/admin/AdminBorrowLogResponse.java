package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.BorrowLogCustomizeVO;

/**
 * @author pangchengchao
 * @version BorrowRecoverResponse, v0.1 2018/7/2 15:53
 */
public class AdminBorrowLogResponse extends Response<BorrowLogCustomizeVO> {
    private Integer total;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
