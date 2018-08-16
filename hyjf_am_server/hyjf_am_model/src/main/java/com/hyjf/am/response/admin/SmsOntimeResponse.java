/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.SmsOntimeVO;

/**
 * @author fq
 * @version SmsOntimeResponse, v0.1 2018/8/14 19:09
 */
public class SmsOntimeResponse extends Response<SmsOntimeVO> {
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
