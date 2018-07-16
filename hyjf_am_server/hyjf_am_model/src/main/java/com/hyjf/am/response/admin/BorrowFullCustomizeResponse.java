/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.BorrowFullCustomizeVO;

/**
 * @author wangjun
 * @version BorrowFullCustomizeResponse, v0.1 2018/7/6 10:12
 */
public class BorrowFullCustomizeResponse extends Response<BorrowFullCustomizeVO> {
    private Integer total;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
