package com.hyjf.cs.user.bean;

/**
 * 用户信息查询Bean
 */
public class AemsSyncUserInfoRequest extends BaseBean {

    private String accountIds;

    public String getAccountIds() {
        return accountIds;
    }

    public void setAccountIds(String accountIds) {
        this.accountIds = accountIds;
    }
}
