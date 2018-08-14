package com.hyjf.common.bean;

/**
 * @author xiasq
 * @version AccessToken, v0.1 2018/8/10 11:27
 * jwt token entity
 */
public class AccessToken {
    /** jwt token */
    private String token;
    /** 用户id */
    private int userId;
    /** 用户名 */
    private String username;
    /** 时间毫秒数 */
    private long ts;

    public AccessToken() {
    }

    public AccessToken(String token) {
        this.token = token;
    }

    public AccessToken(int userId, String username, long ts) {
        this.userId = userId;
        this.username = username;
        this.ts = ts;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    @Override
    public String toString() {
        return "AccessToken{" +
                "token='" + token + '\'' +
                ", userId=" + userId +
                ", username='" + username + '\'' +
                ", ts=" + ts +
                '}';
    }
}
