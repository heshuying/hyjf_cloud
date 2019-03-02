package com.hyjf.am.resquest.trade;

/**
 * @author wgx
 * @date 2019/3/2
 */
public class BatchRepayTotalRequest {

    private String userId;
    private String userName;
    private String roleId;
    private String password;
    private boolean bankOpenAccount = false;// 是否银行开户
    private String account;// 还款人电子账号
    private String orderId;// 冻结订单号
    private String startDate;
    private String endDate;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isBankOpenAccount() {
        return bankOpenAccount;
    }

    public void setBankOpenAccount(boolean bankOpenAccount) {
        this.bankOpenAccount = bankOpenAccount;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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
}
