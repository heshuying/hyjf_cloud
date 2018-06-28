package com.hyjf.am.vo.user;

import com.hyjf.am.vo.BaseVO;

/**
 * 邀请记录
 * @author hesy
 * @version MyInviteListCustomize, v0.1 2018/6/22 19:53
 */
public class MyInviteListCustomizeVO extends BaseVO{
    public Integer userId;
    public String username;
    /**  邀请时间  */
    public String inviteTime;
    /**  是否开户 0 未开户；1 已开户 */
    public String userStatus;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getInviteTime() {
        return inviteTime;
    }

    public void setInviteTime(String inviteTime) {
        this.inviteTime = inviteTime;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }
}
