/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.borrow.BorrowApicronVO;

/**
 * @author ${yaoy}
 * @version BorrowApicronResponse, v0.1 2018/6/13 16:50
 */
public class BorrowApicronResponse extends Response<BorrowApicronVO> {

    private int count;

    private int flag;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

}
