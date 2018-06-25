package com.hyjf.am.resquest.trade;

import com.hyjf.am.vo.BaseVO;

import java.math.BigDecimal;

/**
 * 我的优惠券列表
 * @author hesy
 * @version MyCouponListRequest, v0.1 2018/6/23 11:46
 */
public class MyCouponListRequest extends BaseVO {

    private String userId;

    private String usedFlag;

    private Integer limitStart;

    private Integer limitEnd;

    private String platform;
    private String money;
    private String borrowNid;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsedFlag() {
        return usedFlag;
    }

    public void setUsedFlag(String usedFlag) {
        this.usedFlag = usedFlag;
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

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }
}
