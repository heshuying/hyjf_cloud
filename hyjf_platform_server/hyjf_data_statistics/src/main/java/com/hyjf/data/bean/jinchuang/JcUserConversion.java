/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.data.bean.jinchuang;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @author yaoyong
 * @version JcUserConversion, v0.1 2019/6/19 10:23
 * 金创用户行为转化表
 */
@Document(collection = "ht_jc_user_conversion")
public class JcUserConversion implements Serializable {

    private String id;

    private String registerRate;

    private String openAccountRate;

    private String rechargeRate;

    private String investRate;

    private String reInvestRate;

    private String time;

    private String createTime;

    private String updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegisterRate() {
        return registerRate;
    }

    public void setRegisterRate(String registerRate) {
        this.registerRate = registerRate;
    }

    public String getOpenAccountRate() {
        return openAccountRate;
    }

    public void setOpenAccountRate(String openAccountRate) {
        this.openAccountRate = openAccountRate;
    }

    public String getRechargeRate() {
        return rechargeRate;
    }

    public void setRechargeRate(String rechargeRate) {
        this.rechargeRate = rechargeRate;
    }

    public String getInvestRate() {
        return investRate;
    }

    public void setInvestRate(String investRate) {
        this.investRate = investRate;
    }

    public String getReInvestRate() {
        return reInvestRate;
    }

    public void setReInvestRate(String reInvestRate) {
        this.reInvestRate = reInvestRate;
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
