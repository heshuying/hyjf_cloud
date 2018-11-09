/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.user;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.user.EvalationCustomizeVO;
import com.hyjf.am.vo.user.EvalationVO;

/**
 * @author zhangqingqing
 * @version EvalationResponse, v0.1 2018/6/17 23:21
 */
public class EvalationCustomizeResponse extends Response<EvalationCustomizeVO> {
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
