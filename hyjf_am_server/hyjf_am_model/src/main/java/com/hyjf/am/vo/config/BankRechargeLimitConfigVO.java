package com.hyjf.am.vo.config;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hyjf.am.vo.BaseVO;
import com.hyjf.am.vo.trade.BankConfigVO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class BankRechargeLimitConfigVO extends BaseVO implements Serializable {
    private Integer id;

    private Integer bankId;

    private Integer accessCode;

    private Integer bankType;

    private BigDecimal singleQuota;

    private BigDecimal singleCardQuota;

    private Integer status;

    private Integer createBy;

    private Date createTime;

    private Integer updateBy;

    private Date updateTime;
//    获取银行列表（快捷卡）
    private List<BankConfigVO> bankConfigs;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public Integer getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(Integer accessCode) {
        this.accessCode = accessCode;
    }

    public Integer getBankType() {
        return bankType;
    }

    public void setBankType(Integer bankType) {
        this.bankType = bankType;
    }

    public BigDecimal getSingleQuota() {
        return singleQuota;
    }

    public void setSingleQuota(BigDecimal singleQuota) {
        this.singleQuota = singleQuota;
    }

    public BigDecimal getSingleCardQuota() {
        return singleCardQuota;
    }

    public void setSingleCardQuota(BigDecimal singleCardQuota) {
        this.singleCardQuota = singleCardQuota;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<BankConfigVO> getBankConfigs() {
        return bankConfigs;
    }

    public void setBankConfigs(List<BankConfigVO> bankConfigs) {
        this.bankConfigs = bankConfigs;
    }
}