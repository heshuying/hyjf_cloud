/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.user;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.user.LoanCoverUserVO;

/**
 * @author nxl
 * @version LoanCoverUserResponse, v0.1 2018/6/26 17:28
 */
public class LoanCoverUserResponse extends Response<LoanCoverUserVO> {
    public int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
