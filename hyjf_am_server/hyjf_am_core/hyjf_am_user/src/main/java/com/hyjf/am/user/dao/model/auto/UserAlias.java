package com.hyjf.am.user.dao.model.auto;

import java.io.Serializable;

public class UserAlias implements Serializable {
    private Integer id;

    /**
     * 用户id
     *
     * @mbggenerated
     */
    private Integer userId;

    private String sign;

    private String alias;

    /**
     * 所属平台（2 安卓 3 ios）
     *
     * @mbggenerated
     */
    private String client;

    /**
     * 属于哪个平台（用于推送时选用不同极光账号）
     *
     * @mbggenerated
     */
    private String packageCode;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign == null ? null : sign.trim();
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias == null ? null : alias.trim();
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client == null ? null : client.trim();
    }

    public String getPackageCode() {
        return packageCode;
    }

    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode == null ? null : packageCode.trim();
    }
}