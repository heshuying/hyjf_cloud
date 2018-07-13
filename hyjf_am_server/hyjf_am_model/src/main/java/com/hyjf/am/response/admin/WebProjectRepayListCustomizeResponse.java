/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.WebProjectRepayListCustomizeVO;

/**
 * @author wangjun
 * @version WebProjectRepayListCustomizeResponse, v0.1 2018/7/12 11:35
 */
public class WebProjectRepayListCustomizeResponse extends Response<WebProjectRepayListCustomizeVO> {
    private Integer total;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
