package com.hyjf.am.user.dao.model.customize;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AppUtmRegCustomize implements Serializable {
    private Long id;

    /**
     * 渠道编号
     *
     * @mbggenerated
     */
    private Integer sourceId;

    /**
     * 渠道名称
     *
     * @mbggenerated
     */
    private String sourceName;

    private Integer userId;

    /**
     * 用户名
     *
     * @mbggenerated
     */
    private String userName;

    /**
     * 注册时间
     *
     * @mbggenerated
     */
    private Date registerTime;

    /**
     * 开户时间
     *
     * @mbggenerated
     */
    private Date openAccountTime;

    /**
     * 首次出借时间
     *
     * @mbggenerated
     */
    private Integer firstInvestTime;

    /**
     * 首投金额
     *
     * @mbggenerated
     */
    private BigDecimal investAmount;

    /**
     * 首次出借标的的项目类型
     *
     * @mbggenerated
     */
    private String investProjectType;

    /**
     * 首次出借标的的项目期限
     *
     * @mbggenerated
     */
    private String investProjectPeriod;

    /**
     * 累积出借金额
     *
     * @mbggenerated
     */
    private BigDecimal cumulativeInvest;

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

    /**
     * 手机号
     *
     * @mbggenerated
     */
    private String mobile;

    /**
     * 推荐人
     *
     * @mbggenerated
     */
    private String reffer;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName == null ? null : sourceName.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Date getOpenAccountTime() {
        return openAccountTime;
    }

    public void setOpenAccountTime(Date openAccountTime) {
        this.openAccountTime = openAccountTime;
    }

    public Integer getFirstInvestTime() {
        return firstInvestTime;
    }

    public void setFirstInvestTime(Integer firstInvestTime) {
        this.firstInvestTime = firstInvestTime;
    }

    public BigDecimal getInvestAmount() {
        return investAmount;
    }

    public void setInvestAmount(BigDecimal investAmount) {
        this.investAmount = investAmount;
    }

    public String getInvestProjectType() {
        return investProjectType;
    }

    public void setInvestProjectType(String investProjectType) {
        this.investProjectType = investProjectType == null ? null : investProjectType.trim();
    }

    public String getInvestProjectPeriod() {
        return investProjectPeriod;
    }

    public void setInvestProjectPeriod(String investProjectPeriod) {
        this.investProjectPeriod = investProjectPeriod == null ? null : investProjectPeriod.trim();
    }

    public BigDecimal getCumulativeInvest() {
        return cumulativeInvest;
    }

    public void setCumulativeInvest(BigDecimal cumulativeInvest) {
        this.cumulativeInvest = cumulativeInvest;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getReffer() {
        return reffer;
    }

    public void setReffer(String reffer) {
        this.reffer = reffer;
    }
}