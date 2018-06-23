package com.hyjf.am.resquest.trade;

import com.hyjf.am.vo.BaseVO;

/**
 * 我的邀请及奖励列表
 * @author hesy
 * @version MyCouponListRequest, v0.1 2018/6/23 11:46
 */
public class MyInviteListRequest extends BaseVO {

    private String userId;

    private Integer limitStart;

    private Integer limitEnd;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(Integer limitStart) {
        this.limitStart = limitStart;
    }

    public Integer getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(Integer limitEnd) {
        this.limitEnd = limitEnd;
    }
}
