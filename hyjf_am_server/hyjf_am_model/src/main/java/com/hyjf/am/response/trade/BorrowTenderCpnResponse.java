/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.borrow.BorrowTenderCpnVO;

/**
 * @author yaoy
 * @version BorrowTenderCpnResponse, v0.1 2018/6/26 19:51
 */
public class BorrowTenderCpnResponse extends Response<BorrowTenderCpnVO> {
    //用户更新或者新增时的结果标识
    private Integer flag;

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}
