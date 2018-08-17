package com.hyjf.am.resquest.trade;

/**
 * 还款申请
 * @author hesy
 * @version RepayRequest, v0.1 2018/7/10 11:01
 */
public class RepayRequest {
    // 标的编号
    private String borrowNid;
    // 平台登录密码
    private String password;

    private String isAllRepay;

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIsAllRepay() {
        return isAllRepay;
    }

    public void setIsAllRepay(String isAllRepay) {
        this.isAllRepay = isAllRepay;
    }
}
