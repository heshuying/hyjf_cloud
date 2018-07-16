/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.BorrowFirstCustomizeVO;

/**
 * @author wangjun
 * @version BorrowFirstCustomizeResponse, v0.1 2018/7/3 15:16
 */
public class BorrowFirstCustomizeResponse extends Response<BorrowFirstCustomizeVO> {
    private Integer total;

    private String sumAccount;

    private boolean flag;

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

    public boolean getFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
