package com.hyjf.am.config.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class HjhUserAuthConfigLog implements Serializable {
    /**
     * id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 关联的授权配置id
     *
     * @mbggenerated
     */
    private Integer authConfigId;

    /**
     * 授权类型 1缴费授权 2还款授权 3自动投标 4自动债转
     *
     * @mbggenerated
     */
    private Integer authType;

    /**
     * 个人最高金额(元)
     *
     * @mbggenerated
     */
    private Integer personalMaxAmount;

    /**
     * 企业最高金额(元)
     *
     * @mbggenerated
     */
    private Integer enterpriseMaxAmount;

    /**
     * 授权期限(年)
     *
     * @mbggenerated
     */
    private Integer authPeriod;

    /**
     * 启用状态 0未启用 1启用
     *
     * @mbggenerated
     */
    private Integer enabledStatus;

    /**
     * 备注
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * ip地址
     *
     * @mbggenerated
     */
    private String ip;

    /**
     * mac地址
     *
     * @mbggenerated
     */
    private String mac;

    /**
     * 创建人
     *
     * @mbggenerated
     */
    private Integer createUserId;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAuthConfigId() {
        return authConfigId;
    }

    public void setAuthConfigId(Integer authConfigId) {
        this.authConfigId = authConfigId;
    }

    public Integer getAuthType() {
        return authType;
    }

    public void setAuthType(Integer authType) {
        this.authType = authType;
    }

    public Integer getPersonalMaxAmount() {
        return personalMaxAmount;
    }

    public void setPersonalMaxAmount(Integer personalMaxAmount) {
        this.personalMaxAmount = personalMaxAmount;
    }

    public Integer getEnterpriseMaxAmount() {
        return enterpriseMaxAmount;
    }

    public void setEnterpriseMaxAmount(Integer enterpriseMaxAmount) {
        this.enterpriseMaxAmount = enterpriseMaxAmount;
    }

    public Integer getAuthPeriod() {
        return authPeriod;
    }

    public void setAuthPeriod(Integer authPeriod) {
        this.authPeriod = authPeriod;
    }

    public Integer getEnabledStatus() {
        return enabledStatus;
    }

    public void setEnabledStatus(Integer enabledStatus) {
        this.enabledStatus = enabledStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac == null ? null : mac.trim();
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}