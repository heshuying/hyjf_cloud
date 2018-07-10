/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.STZHWhiteListVO;

/**
 * @author fuqiang
 * @version STZHWhiteListResponse, v0.1 2018/6/12 13:57
 */
public class STZHWhiteListResponse extends Response<STZHWhiteListVO> {
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
