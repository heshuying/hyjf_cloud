package com.hyjf.am.resquest.trade;


import com.hyjf.am.vo.BasePage;
import com.hyjf.am.vo.BaseVO;

public class TradeDetailBeanRequest extends BasePage {
    // 用户id
    public String userId;
    // 投资开始值
    public String startDate;
    // 投资结束值
    public String endDate;
    // 交易类型
    public String trade;
    // 状态
    public String status;

    private Integer limitStart;

    private Integer limitEnd;

    private String roleId;

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
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

    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
