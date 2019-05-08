/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.BorrowRepayInfoCurrentCustomizeVO;
import com.hyjf.am.vo.trade.hjh.BorrowRepayInfoCurrentVO;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hesy
 */
public class BorrowRepayInfoCurrentResponse extends Response<BorrowRepayInfoCurrentCustomizeVO>{
    /**
     * 列表总记录数
     */
    private Integer count = 0;

    private Map<String,Object> sumInfo = new HashMap<String,Object>();

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Map<String, Object> getSumInfo() {
        return sumInfo;
    }

    public void setSumInfo(Map<String, Object> sumInfo) {
        this.sumInfo = sumInfo;
    }
}
