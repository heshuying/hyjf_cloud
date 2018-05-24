/*
 * Copyright(c) 2012-2016 JD Pharma.Ltd. All Rights Reserved.
 */
package com.hyjf.cs.user.util;

import java.io.Serializable;

/**
 * app用户的token对象
 * @author renxingchen
 * @version hyjf 1.0
 * @since hyjf 1.0 2016年2月18日
 */
public class AppUserToken implements Serializable {

    /**
     * 此处为属性说明
     */
    private static final long serialVersionUID = 6442288801361668410L;

    public AppUserToken() {
        super();
    }

    public AppUserToken(Integer userId, String username) {
        super();
        this.userId = userId;
        this.username = username;
    }

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

    private Integer userId;

    private String username;

}
