package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

public class HjhUserAuthConfigLogCustomizeVO extends BasePage implements Serializable {

    @ApiModelProperty(value = "主键ID")
    private Integer id;
    @ApiModelProperty(value = "授权配置ID")
    private Integer authConfigId;
    @ApiModelProperty(value = "授权配置类型 1服务费授权 2还款授权 3自动投标 4智投债转")
    private Integer authType;
    @ApiModelProperty(value = "个人最高金额（元）")
    private Integer personalMaxAmount;
    @ApiModelProperty(value = "企业最高金额（元）")
    private Integer enterpriseMaxAmount;
    @ApiModelProperty(value = "授权期限（年）")
    private Integer authPeriod;
    @ApiModelProperty(value = "启用状态 0未启用 1启用")
    private Integer enabledStatus;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "ip地址")
    private String ip;
    @ApiModelProperty(value = "mac地址")
    private String mac;
    @ApiModelProperty(value = "创建人userId")
    private Integer createUser;
    @ApiModelProperty(value = "创建人名称")
    private String createUserStr;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "创建时间字符串")
    private String  createTimeStr;

    /**
     * 检索条件 limitStart
     */
    private int limitStart = -1;
    /**
     * 检索条件 limitEnd
     */
    private int limitEnd = -1;

    public int getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    public int getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(int limitEnd) {
        this.limitEnd = limitEnd;
    }

    public String getCreateUserStr() {
        return createUserStr;
    }

    public void setCreateUserStr(String createUserStr) {
        this.createUserStr = createUserStr;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

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

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}