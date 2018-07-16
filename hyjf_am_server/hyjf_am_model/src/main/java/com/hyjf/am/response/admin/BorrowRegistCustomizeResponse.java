/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.BorrowRegistCustomizeVO;

/**
 * @author wangjun
 * @version BorrowRegistCustomizeResponse, v0.1 2018/6/29 18:44
 */
public class BorrowRegistCustomizeResponse extends Response<BorrowRegistCustomizeVO> {
    private Integer total;

    private String sumAccount;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getSumAccount() {
        return sumAccount;
    }

    public void setSumAccount(String sumAccount) {
        this.sumAccount = sumAccount;
    }
}
