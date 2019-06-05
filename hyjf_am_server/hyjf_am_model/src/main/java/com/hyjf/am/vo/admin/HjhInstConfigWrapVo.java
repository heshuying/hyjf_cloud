package com.hyjf.am.vo.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author by xiehuili on 2018/7/6.
 */
public class HjhInstConfigWrapVo extends BaseVO implements Serializable {

    private Integer id;

    private String instCode;

    private String instName;

    private Integer instType;

    private BigDecimal capitalToplimit;

    @ApiModelProperty(value = "担保公司")
    private String cooperativeAgency;

    private BigDecimal commissionFee;

    private String remark;

    private Integer delFlag;

    private Integer createUser;

    private Integer updateUser;

    private Date createTime;

    private Date updateTime;

    private Integer repayCapitalType;

    private static final long serialVersionUID = 1L;

    private String capitalAvailable;

    public String getCapitalAvailable() {
        return capitalAvailable;
    }

    public void setCapitalAvailable(String capitalAvailable) {
        this.capitalAvailable = capitalAvailable;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode;
    }

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    public Integer getInstType() {
        return instType;
    }

    public void setInstType(Integer instType) {
        this.instType = instType;
    }

    public BigDecimal getCapitalToplimit() {
        return capitalToplimit;
    }

    public void setCapitalToplimit(BigDecimal capitalToplimit) {
        this.capitalToplimit = capitalToplimit;
    }

    public BigDecimal getCommissionFee() {
        return commissionFee;
    }

    public void setCommissionFee(BigDecimal commissionFee) {
        this.commissionFee = commissionFee;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getRepayCapitalType() {
        return repayCapitalType;
    }

    public void setRepayCapitalType(Integer repayCapitalType) {
        this.repayCapitalType = repayCapitalType;
    }

    public String getCooperativeAgency() {
        return cooperativeAgency;
    }

    public void setCooperativeAgency(String cooperativeAgency) {
        this.cooperativeAgency = cooperativeAgency;
    }
}
