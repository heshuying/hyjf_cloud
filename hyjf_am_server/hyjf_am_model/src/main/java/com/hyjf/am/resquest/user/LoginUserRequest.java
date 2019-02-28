package com.hyjf.am.resquest.user;

import java.io.Serializable;

/**
 * @author xiasq
 * @version LoginUserRequest, v0.1 2019-02-28 15:49
 */
public class LoginUserRequest implements Serializable{
    private static final long serialVersionUID = 2829600801340997936L;

    private int userId;

    /**
     * 登录ip
     */
    private String ip;

    public LoginUserRequest(int userId, String ip) {
        this.userId = userId;
        this.ip = ip;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "LoginUserRequest{" +
                "userId=" + userId +
                ", ip='" + ip + '\'' +
                '}';
    }
}
