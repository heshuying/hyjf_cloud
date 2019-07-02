/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.data.bean.jinchuang;

import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

/**
 * @author yaoyong
 * @version JcRegisterTrade, v0.1 2019/6/20 14:56
 * 金创注册、交易规模表
 */
@Document(collection = "ht_jc_register_trade")
public class JcRegisterTrade {

    private String id;

    private BigDecimal dealSize;

    /**
     * 结束状态 0初始，1结束
     */
    private Integer dealStatus;

    private Integer registerCount;
    /**
     * 结束状态 0初始，1结束
     */
    private Integer registerStatus;

    private String time;

    private String createTime;

    private String updateTime;

    public Integer getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(Integer dealStatus) {
        this.dealStatus = dealStatus;
    }

    public Integer getRegisterStatus() {
        return registerStatus;
    }

    public void setRegisterStatus(Integer registerStatus) {
        this.registerStatus = registerStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getDealSize() {
        return dealSize;
    }

    public void setDealSize(BigDecimal dealSize) {
        this.dealSize = dealSize;
    }

    public Integer getRegisterCount() {
        return registerCount;
    }

    public void setRegisterCount(Integer registerCount) {
        this.registerCount = registerCount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
