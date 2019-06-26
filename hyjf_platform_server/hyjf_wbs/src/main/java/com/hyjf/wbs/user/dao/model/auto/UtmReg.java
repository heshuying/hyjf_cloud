package com.hyjf.wbs.user.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class UtmReg implements Serializable {
    private Integer id;

    /**
     * 推广链接id
     *
     * @mbggenerated
     */
    private Integer utmId;

    /**
     * 注册用户id
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 首次出借时间
     *
     * @mbggenerated
     */
    private Integer investTime;

    /**
     * 首次投标的出借金额
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
     * 开户返回
     *
     * @mbggenerated
     */
    private Integer openAccount;

    /**
     * 绑卡返回
     *
     * @mbggenerated
     */
    private Integer bindCard;

    /**
     * 惠享游用户id
     *
     * @mbggenerated
     */
    private Integer hxyid;

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

    public Integer getUtmId() {
        return utmId;
    }

    public void setUtmId(Integer utmId) {
        this.utmId = utmId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getInvestTime() {
        return investTime;
    }

    public void setInvestTime(Integer investTime) {
        this.investTime = investTime;
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

    public Integer getOpenAccount() {
        return openAccount;
    }

    public void setOpenAccount(Integer openAccount) {
        this.openAccount = openAccount;
    }

    public Integer getBindCard() {
        return bindCard;
    }

    public void setBindCard(Integer bindCard) {
        this.bindCard = bindCard;
    }

    public Integer getHxyid() {
        return hxyid;
    }

    public void setHxyid(Integer hxyid) {
        this.hxyid = hxyid;
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