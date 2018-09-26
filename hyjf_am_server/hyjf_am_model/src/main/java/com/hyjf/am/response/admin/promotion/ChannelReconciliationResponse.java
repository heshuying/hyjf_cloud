/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin.promotion;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.promotion.channel.ChannelReconciliationVO;

/**
 * @author fq
 * @version ChannelReconciliationResponse, v0.1 2018/9/21 10:19
 */
public class ChannelReconciliationResponse extends Response<ChannelReconciliationVO> {
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
