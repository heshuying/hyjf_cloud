/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.statistics.bean;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author fuqiang
 * @version StatisticsTzjUtm, v0.1 2018/7/2 11:17
 */
@Document(collection = "t_statistics_tzj_utm")
public class StatisticsTzjUtm implements Serializable {
    private Integer id;

    private String day;

    private Integer registCount;

    private Integer openCount;

    private BigDecimal openRate;

    private Integer cardbindCount;

    private BigDecimal cardbindRate;

    private Integer rechargenewCount;

    private Integer rechargeCount;

    private Integer tendernewCount;

    private Integer tenderfirstCount;

    private BigDecimal tenderfirstMoney;

    private BigDecimal tendernewRate;

    private Integer tenderCount;

    private BigDecimal tenderMoney;

    private Integer tenderAgainCount;

    private BigDecimal tenderAgainRate;

    private String channelName;

    private Integer channelFlg;

    private Integer addTime;

    private Integer updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getCardbindCount() {
        return cardbindCount;
    }

    public void setCardbindCount(Integer cardbindCount) {
        this.cardbindCount = cardbindCount;
    }

    public BigDecimal getCardbindRate() {
        return cardbindRate;
    }

    public void setCardbindRate(BigDecimal cardbindRate) {
        this.cardbindRate = cardbindRate;
    }

    public Integer getRechargenewCount() {
        return rechargenewCount;
    }

    public void setRechargenewCount(Integer rechargenewCount) {
        this.rechargenewCount = rechargenewCount;
    }

    public Integer getRechargeCount() {
        return rechargeCount;
    }

    public void setRechargeCount(Integer rechargeCount) {
        this.rechargeCount = rechargeCount;
    }

    public Integer getTendernewCount() {
        return tendernewCount;
    }

    public void setTendernewCount(Integer tendernewCount) {
        this.tendernewCount = tendernewCount;
    }

    public Integer getTenderfirstCount() {
        return tenderfirstCount;
    }

    public void setTenderfirstCount(Integer tenderfirstCount) {
        this.tenderfirstCount = tenderfirstCount;
    }

    public BigDecimal getTenderfirstMoney() {
        return tenderfirstMoney;
    }

    public void setTenderfirstMoney(BigDecimal tenderfirstMoney) {
        this.tenderfirstMoney = tenderfirstMoney;
    }

    public BigDecimal getTendernewRate() {
        return tendernewRate;
    }

    public void setTendernewRate(BigDecimal tendernewRate) {
        this.tendernewRate = tendernewRate;
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
        this.channelName = channelName == null ? null : channelName.trim();
    }

    public Integer getChannelFlg() {
        return channelFlg;
    }

    public void setChannelFlg(Integer channelFlg) {
        this.channelFlg = channelFlg;
    }

    public Integer getAddTime() {
        return addTime;
    }

    public void setAddTime(Integer addTime) {
        this.addTime = addTime;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }
}
