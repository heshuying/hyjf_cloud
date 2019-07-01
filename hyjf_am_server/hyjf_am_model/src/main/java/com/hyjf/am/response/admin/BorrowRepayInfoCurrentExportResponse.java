/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.BorrowRepayInfoCurrentCustomizeVO;
import com.hyjf.am.vo.admin.BorrowRepayInfoCurrentExportCustomizeVO;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hesy
 */
public class BorrowRepayInfoCurrentExportResponse extends Response<BorrowRepayInfoCurrentExportCustomizeVO>{
    /**
     * 列表总记录数
     */
    private Integer count = 0;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

}
