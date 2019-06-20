/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.data.bean.jinchuang;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @author yaoyong
 * @version JcUserConversion, v0.1 2019/6/19 10:23
 */
@Document(collection = "ht_jc_user_conversion")
public class JcUserConversion implements Serializable {
    private String id;

    private Integer registerNum;

    private Integer openAccountNum;

    private Integer rechargeNum;

    private Integer investNum;

    private Integer reInvestNum;

    private String time;

    private Integer createTime;

    private Integer updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getRegisterNum() {
        return registerNum;
    }

    public void setRegisterNum(Integer registerNum) {
        this.registerNum = registerNum;
    }

    public Integer getOpenAccountNum() {
        return openAccountNum;
    }

    public void setOpenAccountNum(Integer openAccountNum) {
        this.openAccountNum = openAccountNum;
    }

    public Integer getRechargeNum() {
        return rechargeNum;
    }

    public void setRechargeNum(Integer rechargeNum) {
        this.rechargeNum = rechargeNum;
    }

    public Integer getInvestNum() {
        return investNum;
    }

    public void setInvestNum(Integer investNum) {
        this.investNum = investNum;
    }

    public Integer getReInvestNum() {
        return reInvestNum;
    }

    public void setReInvestNum(Integer reInvestNum) {
        this.reInvestNum = reInvestNum;
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
