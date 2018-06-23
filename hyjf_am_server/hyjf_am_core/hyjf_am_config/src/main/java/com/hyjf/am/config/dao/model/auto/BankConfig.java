package com.hyjf.am.config.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class BankConfig implements Serializable {
    private Integer id;

    private String name;

    private String code;

    private String appLogo;

    private String logo;

    private Integer personalEbank;

    private Integer enterpriseEbank;

    private Integer quickPayment;

    private Integer immediatelyWithdraw;

    private Integer quickWithdraw;

    private Integer normalWithdraw;

    private Integer withdrawDefaulttype;

    private Integer status;

    private Integer createUserId;

    private Integer updateUserId;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getAppLogo() {
        return appLogo;
    }

    public void setAppLogo(String appLogo) {
        this.appLogo = appLogo == null ? null : appLogo.trim();
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
    }

    public Integer getPersonalEbank() {
        return personalEbank;
    }

    public void setPersonalEbank(Integer personalEbank) {
        this.personalEbank = personalEbank;
    }

    public Integer getEnterpriseEbank() {
        return enterpriseEbank;
    }

    public void setEnterpriseEbank(Integer enterpriseEbank) {
        this.enterpriseEbank = enterpriseEbank;
    }

    public Integer getQuickPayment() {
        return quickPayment;
    }

    public void setQuickPayment(Integer quickPayment) {
        this.quickPayment = quickPayment;
    }

    public Integer getImmediatelyWithdraw() {
        return immediatelyWithdraw;
    }

    public void setImmediatelyWithdraw(Integer immediatelyWithdraw) {
        this.immediatelyWithdraw = immediatelyWithdraw;
    }

    public Integer getQuickWithdraw() {
        return quickWithdraw;
    }

    public void setQuickWithdraw(Integer quickWithdraw) {
        this.quickWithdraw = quickWithdraw;
    }

    public Integer getNormalWithdraw() {
        return normalWithdraw;
    }

    public void setNormalWithdraw(Integer normalWithdraw) {
        this.normalWithdraw = normalWithdraw;
    }

    public Integer getWithdrawDefaulttype() {
        return withdrawDefaulttype;
    }

    public void setWithdrawDefaulttype(Integer withdrawDefaulttype) {
        this.withdrawDefaulttype = withdrawDefaulttype;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}