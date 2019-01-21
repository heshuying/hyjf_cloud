package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PoundageLedger implements Serializable {
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
     * 真实姓名
     *
     * @mbggenerated
     */
    private String truename;

    /**
     * 电子账号
     *
     * @mbggenerated
     */
    private String account;

    /**
     * 分账类型   1:按投资人分账； 2:按借款人分账
     *
     * @mbggenerated
     */
    private Integer type;

    /**
     * 分账来源  0:全部； 1:服务费； 2:债转服务费； 3:管理费
     *
     * @mbggenerated
     */
    private String source;

    /**
     * 服务费分账比例
     *
     * @mbggenerated
     */
    private BigDecimal serviceRatio;

    /**
     * 债转服务费分账比例
     *
     * @mbggenerated
     */
    private BigDecimal creditRatio;

    /**
     * 管理费分账比例
     *
     * @mbggenerated
     */
    private BigDecimal manageRatio;

    /**
     * 投资人分公司id
     *
     * @mbggenerated
     */
    private Integer investorCompanyId;

    /**
     * 投资人分公司名称
     *
     * @mbggenerated
     */
    private String investorCompany;

    /**
     * 项目类型，可保存所有
     *
     * @mbggenerated
     */
    private String projectType;

    /**
     * 启用状态  0：禁用    1：启用
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 说明
     *
     * @mbggenerated
     */
    private String explan;

    /**
     * 创建人
     *
     * @mbggenerated
     */
    private Integer createUserId;

    /**
     * 修改人
     *
     * @mbggenerated
     */
    private Integer updateUserId;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 更新时间
     *
     * @mbggenerated
     */
    private Date updateTime;

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

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename == null ? null : truename.trim();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public BigDecimal getServiceRatio() {
        return serviceRatio;
    }

    public void setServiceRatio(BigDecimal serviceRatio) {
        this.serviceRatio = serviceRatio;
    }

    public BigDecimal getCreditRatio() {
        return creditRatio;
    }

    public void setCreditRatio(BigDecimal creditRatio) {
        this.creditRatio = creditRatio;
    }

    public BigDecimal getManageRatio() {
        return manageRatio;
    }

    public void setManageRatio(BigDecimal manageRatio) {
        this.manageRatio = manageRatio;
    }

    public Integer getInvestorCompanyId() {
        return investorCompanyId;
    }

    public void setInvestorCompanyId(Integer investorCompanyId) {
        this.investorCompanyId = investorCompanyId;
    }

    public String getInvestorCompany() {
        return investorCompany;
    }

    public void setInvestorCompany(String investorCompany) {
        this.investorCompany = investorCompany == null ? null : investorCompany.trim();
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType == null ? null : projectType.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getExplan() {
        return explan;
    }

    public void setExplan(String explan) {
        this.explan = explan == null ? null : explan.trim();
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