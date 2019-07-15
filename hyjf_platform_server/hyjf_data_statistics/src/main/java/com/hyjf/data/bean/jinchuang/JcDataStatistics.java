/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.data.bean.jinchuang;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author yaoyong
 * @version JcDataStatistics, v0.1 2019/6/19 16:01
 * 金创统计出借笔数表
 */
@Document(collection = "ht_jc_data_statistics")
public class JcDataStatistics {

    private String id;

    /**总出借笔数**/
    private Integer investCount;
    /**
     * 结束状态 0：初始 1：结束
     */
    private Integer investStatus;

    private Integer ztInvestCount; //散标出借笔数

    private Integer ztInvestStatus;

    private Integer jhInvestCount; //智投出借笔数
    private Integer jhInvestStatus;

    private Integer creditCount;  //承接债转笔数
    private Integer creditStatus;

    private String time;          //统计时间

    private String createTime;

    private String updateTime;

    public Integer getInvestStatus() {
        return investStatus;
    }

    public void setInvestStatus(Integer investStatus) {
        this.investStatus = investStatus;
    }

    public Integer getZtInvestStatus() {
        return ztInvestStatus;
    }

    public void setZtInvestStatus(Integer ztInvestStatus) {
        this.ztInvestStatus = ztInvestStatus;
    }

    public Integer getJhInvestStatus() {
        return jhInvestStatus;
    }

    public void setJhInvestStatus(Integer jhInvestStatus) {
        this.jhInvestStatus = jhInvestStatus;
    }

    public Integer getCreditStatus() {
        return creditStatus;
    }

    public void setCreditStatus(Integer creditStatus) {
        this.creditStatus = creditStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getInvestCount() {
        return investCount;
    }

    public void setInvestCount(Integer investCount) {
        this.investCount = investCount;
    }

    public Integer getZtInvestCount() {
        return ztInvestCount;
    }

    public void setZtInvestCount(Integer ztInvestCount) {
        this.ztInvestCount = ztInvestCount;
    }

    public Integer getJhInvestCount() {
        return jhInvestCount;
    }

    public void setJhInvestCount(Integer jhInvestCount) {
        this.jhInvestCount = jhInvestCount;
    }

    public Integer getCreditCount() {
        return creditCount;
    }

    public void setCreditCount(Integer creditCount) {
        this.creditCount = creditCount;
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
