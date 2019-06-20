/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.data.bean;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author yaoyong
 * @version JcDataStatistics, v0.1 2019/6/19 16:01
 */
@Document(collection = "ht_jc_data_statistics")
public class JcDataStatistics {

    private String id;

    /**总出借笔数**/
    private Integer investCount;

    private Integer ztInvestCount; //散标出借笔数

    private Integer jhInvestCount; //智投出借笔数

    private Integer creditCount;  //承接债转笔数

    private String time;          //统计时间

    private Integer createTime;

    private Integer updateTime;

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

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }
}
