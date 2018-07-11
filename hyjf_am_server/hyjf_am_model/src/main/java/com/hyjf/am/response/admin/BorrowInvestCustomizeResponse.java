/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.BorrowInvestCustomizeVO;

/**
 * @author wangjun
 * @version BorrowInvestCustomizeResponse, v0.1 2018/7/10 15:34
 */
public class BorrowInvestCustomizeResponse extends Response<BorrowInvestCustomizeVO> {
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
