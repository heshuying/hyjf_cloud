/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.user;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.user.BankMobileModifyVO;

/**
 * @author PC-LIUSHOUYI
 * @version BankMobileModifyResponse, v0.1 2019/5/9 15:33
 */
public class BankMobileModifyResponse extends Response<BankMobileModifyVO> {
    private int cnt;

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }
}
