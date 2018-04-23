/*
 * Copyright(c) 2012-2016 JD Pharma.Ltd. All Rights Reserved.
 */
package com.hyjf.am.user.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 集中式session对象
 * @author renxingchen
 * @version hyjf 1.0
 * @since hyjf 1.0 2016年3月28日
 * @see 下午2:50:30
 */
public class HyjfSession implements Serializable {

    /**
     * 此处为属性说明
     */
    private static final long serialVersionUID = -7990567437141781332L;

    public WebViewUser getUser() {
        return user;
    }

    public void setUser(WebViewUser user) {
        this.user = user;
    }

    public List<String> getTokenList() {
        return tokenList;
    }

    public void setTokenList(List<String> tokenList) {
        this.tokenList = tokenList;
    }

    private WebViewUser user;

    private List<String> tokenList = new ArrayList<>();

    public boolean removeToken(String token) {
        return this.tokenList.remove(token);
    }

    public boolean addToken(String token) {
        return this.tokenList.add(token);
    }

}
