/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.statistics.bean;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author fuqiang
 * @version StatisticsTzj, v0.1 2018/7/2 11:11
 */
@Document(collection = "t_statistics_tzj")
@Deprecated
public class StatisticsTzj implements Serializable {
    private String id;

    private String day;

    private Integer registCount;

    private Integer openCount;

    private BigDecimal openRate;

    private Integer cardBindCount;

    private BigDecimal cardBindRate;

    private Integer rechargeNewCount;

    private Integer rechargeCount;

    private Integer tenderNewCount;

    private Integer tenderFirstCount;

    private BigDecimal tenderFirstMoney;

    private BigDecimal tenderNewRate;

    private Integer tenderCount;

    private BigDecimal tenderMoney;

    private Integer tenderAgainCount;

    private BigDecimal tenderAgainRate;

    private String channelName;

    private Integer channelFlag;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day == null ? null : day.trim();
    }

    public Integer getRegistCount() {
        return registCount;
    }

    public void setRegistCount(Integer registCount) {
        this.registCount = registCount;
    }

    public Integer getOpenCount() {
        return openCount;
    }

    public void setOpenCount(Integer openCount) {
        this.openCount = openCount;
    }

    public BigDecimal getOpenRate() {
        return openRate;
    }

    public void setOpenRate(BigDecimal openRate) {
        this.openRate = openRate;
    }

    public Integer getCardBindCount() {
        return cardBindCount;
    }

    public void setCardBindCount(Integer cardBindCount) {
        this.cardBindCount = cardBindCount;
    }

    public BigDecimal getCardBindRate() {
        return cardBindRate;
    }

    public void setCardBindRate(BigDecimal cardBindRate) {
        this.cardBindRate = cardBindRate;
    }

    public Integer getRechargeNewCount() {
        return rechargeNewCount;
    }

    public void setRechargeNewCount(Integer rechargeNewCount) {
        this.rechargeNewCount = rechargeNewCount;
    }

    public Integer getRechargeCount() {
        return rechargeCount;
    }

    public void setRechargeCount(Integer rechargeCount) {
        this.rechargeCount = rechargeCount;
    }

    public Integer getTenderNewCount() {
        return tenderNewCount;
    }

    public void setTenderNewCount(Integer tenderNewCount) {
        this.tenderNewCount = tenderNewCount;
    }

    public Integer getTenderFirstCount() {
        return tenderFirstCount;
    }

    public void setTenderFirstCount(Integer tenderFirstCount) {
        this.tenderFirstCount = tenderFirstCount;
    }

    public BigDecimal getTenderFirstMoney() {
        return tenderFirstMoney;
    }

    public void setTenderFirstMoney(BigDecimal tenderFirstMoney) {
        this.tenderFirstMoney = tenderFirstMoney;
    }

    public BigDecimal getTenderNewRate() {
        return tenderNewRate;
    }

    public void setTenderNewRate(BigDecimal tenderNewRate) {
        this.tenderNewRate = tenderNewRate;
    }

    public Integer getTenderCount() {
        return tenderCount;
    }

    public void setTenderCount(Integer tenderCount) {
        this.tenderCount = tenderCount;
    }

    public BigDecimal getTenderMoney() {
        return tenderMoney;
    }

    public void setTenderMoney(BigDecimal tenderMoney) {
        this.tenderMoney = tenderMoney;
    }

    public Integer getTenderAgainCount() {
        return tenderAgainCount;
    }

    public void setTenderAgainCount(Integer tenderAgainCount) {
        this.tenderAgainCount = tenderAgainCount;
    }

    public BigDecimal getTenderAgainRate() {
        return tenderAgainRate;
    }

    public void setTenderAgainRate(BigDecimal tenderAgainRate) {
        this.tenderAgainRate = tenderAgainRate;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Integer getChannelFlag() {
        return channelFlag;
    }

    public void setChannelFlag(Integer channelFlag) {
        this.channelFlag = channelFlag;
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
