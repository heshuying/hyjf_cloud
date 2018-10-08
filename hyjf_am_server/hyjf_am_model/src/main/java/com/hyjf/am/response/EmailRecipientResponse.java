package com.hyjf.am.response;

import com.hyjf.am.vo.admin.SellDailyDistributionVO;

/**
 * @author lisheng
 * @version EmailRecipientResponse, v0.1 2018/10/8 11:33
 */

public class EmailRecipientResponse extends Response<SellDailyDistributionVO> {
    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
