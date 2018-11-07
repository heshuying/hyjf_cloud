package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;

public class AccountException implements Serializable {
    private Integer id;

    /**
     * 用户id
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 用户名
     *
     * @mbggenerated
     */
    private String username;

    /**
     * 客户号
     *
     * @mbggenerated
     */
    private Long customId;

    /**
     * 手机号
     *
     * @mbggenerated
     */
    private String mobile;

    /**
     * 平台可用金额
     *
     * @mbggenerated
     */
    private BigDecimal balancePlat;

    /**
     * 平台冻结金额
     *
     * @mbggenerated
     */
    private BigDecimal frostPlat;

    /**
     * 汇付可用金额
     *
     * @mbggenerated
     */
    private BigDecimal balanceHuifu;

    /**
     * 汇付冻结金额
     *
     * @mbggenerated
     */
    private BigDecimal frostHuifu;

    private Integer createTime;

    /**
     * 账户角色
     *
     * @mbggenerated
     */
    private String role;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Long getCustomId() {
        return customId;
    }

    public void setCustomId(Long customId) {
        this.customId = customId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public BigDecimal getBalancePlat() {
        return balancePlat;
    }

    public void setBalancePlat(BigDecimal balancePlat) {
        this.balancePlat = balancePlat;
    }

    public BigDecimal getFrostPlat() {
        return frostPlat;
    }

    public void setFrostPlat(BigDecimal frostPlat) {
        this.frostPlat = frostPlat;
    }

    public BigDecimal getBalanceHuifu() {
        return balanceHuifu;
    }

    public void setBalanceHuifu(BigDecimal balanceHuifu) {
        this.balanceHuifu = balanceHuifu;
    }

    public BigDecimal getFrostHuifu() {
        return frostHuifu;
    }

    public void setFrostHuifu(BigDecimal frostHuifu) {
        this.frostHuifu = frostHuifu;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role == null ? null : role.trim();
    }
}