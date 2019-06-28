/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.data.bean.jinchuang;

import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

/**
 * @author yaoyong
 * @version JcUserAnalysis, v0.1 2019/6/20 11:01
 * 用户出借分析表
 */
@Document(collection = "ht_jc_user_analysis")
public class JcUserAnalysis {

    private String id;

    /**
     * 小于5000出借额数
     */
    private String primaryInvest;

    /**
     * 5千至1万出借额
     */
    private String middleInvest;

    /**
     * 1万至5万出借额
     */
    private String seniorInvest;

    /**
     * 5万以上出借额
     */
    private String highestInvest;

    /**
     * 出借次数为1次数
     */
    private String oneInvest;

    /**
     * 出借次数为2次数
     */
    private String twoInvest;

    /**
     * 出借次数为3-5次数
     */
    private String threeInvest;

    /**
     * 出借次数为6-10次数
     */
    private String fourInvest;

    /**
     * 出借次数为10次以上数
     */
    private String fiveInvest;

    /**
     * 男性人数
     */
    private String maleCount;

    /**
     * 女性人数
     */
    private String femaleCount;

    /**
     * 统计日期
     */
    private String time;

    private String createTime;

    private String updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrimaryInvest() {
        return primaryInvest;
    }

    public void setPrimaryInvest(String primaryInvest) {
        this.primaryInvest = primaryInvest;
    }

    public String getMiddleInvest() {
        return middleInvest;
    }

    public void setMiddleInvest(String middleInvest) {
        this.middleInvest = middleInvest;
    }

    public String getSeniorInvest() {
        return seniorInvest;
    }

    public void setSeniorInvest(String seniorInvest) {
        this.seniorInvest = seniorInvest;
    }

    public String getHighestInvest() {
        return highestInvest;
    }

    public void setHighestInvest(String highestInvest) {
        this.highestInvest = highestInvest;
    }

    public String getOneInvest() {
        return oneInvest;
    }

    public void setOneInvest(String oneInvest) {
        this.oneInvest = oneInvest;
    }

    public String getTwoInvest() {
        return twoInvest;
    }

    public void setTwoInvest(String twoInvest) {
        this.twoInvest = twoInvest;
    }

    public String getThreeInvest() {
        return threeInvest;
    }

    public void setThreeInvest(String threeInvest) {
        this.threeInvest = threeInvest;
    }

    public String getFourInvest() {
        return fourInvest;
    }

    public void setFourInvest(String fourInvest) {
        this.fourInvest = fourInvest;
    }

    public String getFiveInvest() {
        return fiveInvest;
    }

    public void setFiveInvest(String fiveInvest) {
        this.fiveInvest = fiveInvest;
    }

    public String getMaleCount() {
        return maleCount;
    }

    public void setMaleCount(String maleCount) {
        this.maleCount = maleCount;
    }

    public String getFemaleCount() {
        return femaleCount;
    }

    public void setFemaleCount(String femaleCount) {
        this.femaleCount = femaleCount;
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
