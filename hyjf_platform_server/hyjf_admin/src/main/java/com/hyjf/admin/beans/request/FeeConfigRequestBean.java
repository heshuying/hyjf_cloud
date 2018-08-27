package com.hyjf.admin.beans.request;

import com.hyjf.admin.beans.BaseRequest;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author by xiehuili on 2018/7/11.
 */
public class FeeConfigRequestBean extends BaseRequest implements Serializable{
    /**
     * 前台时间接收
     */
    @ApiModelProperty(value = "查询的id")
    private String ids;
    @ApiModelProperty(value = "查询的开始时间")
    private String startCreate;
    @ApiModelProperty(value = "查询的结束时间")
    private String endCreate;
    private Integer id;
    @ApiModelProperty(value = "bankCode")
    private String bankCode;
    @ApiModelProperty(value = "银行名称")
    private String name;
    @ApiModelProperty(value = "个人网银充值")
    private String personalCredit;
    @ApiModelProperty(value = "企业网银充值")
    private String enterpriseCredit;
    @ApiModelProperty(value = "快捷支付充值")
    private String quickPayment;
    @ApiModelProperty(value = "即时提现")
    private String directTakeout;
    @ApiModelProperty(value = "即时提现、千分比")
    private String directTakeoutPercent;
    @ApiModelProperty(value = "快速提现")
    private String quickTakeout;
    @ApiModelProperty(value = "快速提现、千分比")
    private String quickTakeoutPercent;
    @ApiModelProperty(value = "普通提现")
    private String normalTakeout;
    @ApiModelProperty(value = "说明")
    private String remark;

    private Integer createUserId;

    private Integer updateUserId;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getStartCreate() {
        return startCreate;
    }

    public void setStartCreate(String startCreate) {
        this.startCreate = startCreate;
    }

    public String getEndCreate() {
        return endCreate;
    }

    public void setEndCreate(String endCreate) {
        this.endCreate = endCreate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPersonalCredit() {
        return personalCredit;
    }

    public void setPersonalCredit(String personalCredit) {
        this.personalCredit = personalCredit;
    }

    public String getEnterpriseCredit() {
        return enterpriseCredit;
    }

    public void setEnterpriseCredit(String enterpriseCredit) {
        this.enterpriseCredit = enterpriseCredit;
    }

    public String getQuickPayment() {
        return quickPayment;
    }

    public void setQuickPayment(String quickPayment) {
        this.quickPayment = quickPayment;
    }

    public String getDirectTakeout() {
        return directTakeout;
    }

    public void setDirectTakeout(String directTakeout) {
        this.directTakeout = directTakeout;
    }

    public String getDirectTakeoutPercent() {
        return directTakeoutPercent;
    }

    public void setDirectTakeoutPercent(String directTakeoutPercent) {
        this.directTakeoutPercent = directTakeoutPercent;
    }

    public String getQuickTakeout() {
        return quickTakeout;
    }

    public void setQuickTakeout(String quickTakeout) {
        this.quickTakeout = quickTakeout;
    }

    public String getQuickTakeoutPercent() {
        return quickTakeoutPercent;
    }

    public void setQuickTakeoutPercent(String quickTakeoutPercent) {
        this.quickTakeoutPercent = quickTakeoutPercent;
    }

    public String getNormalTakeout() {
        return normalTakeout;
    }

    public void setNormalTakeout(String normalTakeout) {
        this.normalTakeout = normalTakeout;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
