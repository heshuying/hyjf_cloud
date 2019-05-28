package com.hyjf.am.resquest.trade;

import java.util.List;

/**
 * @author hesy
 * @version RepayRequestDetailRequest, v0.1 2018/8/7 16:26
 */
public class RepayRequestDetailRequest {
    private Integer userId;

    private String userName;

    private String roleId;

    /**
     * 是否是一次性还款
     */
    private Boolean isAllRepay;

    private String borrowNid;

    /**
     * 逾期期数列表
     */
    private List<Integer> lateArray;

    /**
     * 用户选择的逾期期数，为空或0，默认未选择单期或多期还逾期的方式
     */
    private Integer latePeriod;

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

    public List<Integer> getLateArray() {
        return lateArray;
    }

    public void setLateArray(List<Integer> lateArray) {
        this.lateArray = lateArray;
    }

    public Integer getLatePeriod() {
        return latePeriod;
    }

    public void setLatePeriod(Integer latePeriod) {
        this.latePeriod = latePeriod;
    }
}
