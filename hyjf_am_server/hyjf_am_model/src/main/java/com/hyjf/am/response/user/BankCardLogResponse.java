/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.user;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.user.BankCardLogVO;

/**
 * @author nxl
 * @version BankCardLogResponse, v0.1 2018/7/7 15:57
 */
public class BankCardLogResponse extends Response<BankCardLogVO>{
    int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
