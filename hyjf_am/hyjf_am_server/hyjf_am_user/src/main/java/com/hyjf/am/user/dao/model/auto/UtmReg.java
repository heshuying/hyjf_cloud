package com.hyjf.am.user.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;

public class UtmReg implements Serializable {
    private Integer id;

    private Integer utmId;

    private Integer userId;

    private Integer createTime;

    private Integer investTime;

    private BigDecimal investAmount;

    private String investProjectType;

    private String investProjectPeriod;

    private Integer openAccount;

    private Integer bindCard;

    private Long hxyid;

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

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
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

    public Long getHxyid() {
        return hxyid;
    }

    public void setHxyid(Long hxyid) {
        this.hxyid = hxyid;
    }
}