/*
 * Copyright(c) 2012-2016 JD Pharma.Ltd. All Rights Reserved.
 */
package com.hyjf.cs.user.util;

import java.io.Serializable;

/**
 * sign对应的值类型
 * @author renxingchen
 * @version hyjf 1.0
 * @since hyjf 1.0 2016年2月18日
 */
public class SignValue implements Serializable {

    /**
     * 此处为属性说明
     */
    private static final long serialVersionUID = 3985591633168577199L;

    public SignValue() {
        super();
    }

    public SignValue(String initKey) {
        super();
        this.initKey = initKey;
    }

    public String getInitKey() {
        return initKey;
    }

    public void setInitKey(String initKey) {
        this.initKey = initKey;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    private String initKey;// 初始化密钥

    private String key;// DES加解密密钥

    private String token;// 用户token信息
    
    // 应用版本号
    private String version;

}
