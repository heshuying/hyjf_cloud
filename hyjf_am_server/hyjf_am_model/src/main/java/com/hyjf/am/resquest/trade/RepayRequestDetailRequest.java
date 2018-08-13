package com.hyjf.am.resquest.trade;

/**
 * @author hesy
 * @version RepayRequestDetailRequest, v0.1 2018/8/7 16:26
 */
public class RepayRequestDetailRequest {
    Integer userId;

    String userName;

    String roleId;

    /**
     * 是否是一次性还款
     */
    Boolean isAllRepay;

    String borrowNid;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public Boolean getAllRepay() {
        return isAllRepay;
    }

    public void setAllRepay(Boolean allRepay) {
        isAllRepay = allRepay;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }
}
