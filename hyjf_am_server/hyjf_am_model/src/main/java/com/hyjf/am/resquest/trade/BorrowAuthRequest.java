package com.hyjf.am.resquest.trade;

import com.hyjf.am.vo.BasePage;

/**
 * 借款人受托支付列表
 * @author hesy
 * @version BorrowAuthRequest, v0.1 2018/7/6 11:27
 */
public class BorrowAuthRequest extends BasePage {
    private String borrowNid;
    // 用户id
    public String userId;
    //用户角色 2借款人，3垫付机构
    public String roleId;

    // 应还时间开始
    public String startDate;
    // 应还时间结束
    public String endDate;
    /**
     * 还款时间排序 0:升序 1:降序
     */
    private String repayOrder;
    /**
     * 到账时间排序 0:升序 1:降序
     */
    private String checkTimeOrder;

    Integer limitStart;
    Integer limitEnd;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
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

    public String getRepayOrder() {
        return repayOrder;
    }

    public void setRepayOrder(String repayOrder) {
        this.repayOrder = repayOrder;
    }

    public String getCheckTimeOrder() {
        return checkTimeOrder;
    }

    public void setCheckTimeOrder(String checkTimeOrder) {
        this.checkTimeOrder = checkTimeOrder;
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

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }
}
