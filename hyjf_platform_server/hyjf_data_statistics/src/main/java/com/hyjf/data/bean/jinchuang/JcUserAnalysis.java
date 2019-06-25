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
    private Integer primaryInvest;

    /**
     * 5千至1万出借额
     */
    private Integer middleInvest;

    /**
     * 1万至5万出借额
     */
    private Integer seniorInvest;

    /**
     * 5万以上出借额
     */
    private Integer highestInvest;

    /**
     * 出借次数为1次数
     */
    private Integer oneInvest;

    /**
     * 出借次数为2次数
     */
    private Integer twoInvest;

    /**
     * 出借次数为3-5次数
     */
    private Integer threeInvest;

    /**
     * 出借次数为6-10次数
     */
    private Integer fourInvest;

    /**
     * 出借次数为10次以上数
     */
    private Integer fiveInvest;

    /**
     * 用户收益
     */
    private BigDecimal interest;

    /**
     * 男性人数
     */
    private Integer maleCount;

    /**
     * 女性人数
     */
    private Integer femaleCount;

    /**
     * 统计日期
     */
    private String time;

    private Integer createTime;

    private Integer updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPrimaryInvest() {
        return primaryInvest;
    }

    public void setPrimaryInvest(Integer primaryInvest) {
        this.primaryInvest = primaryInvest;
    }

    public Integer getMiddleInvest() {
        return middleInvest;
    }

    public void setMiddleInvest(Integer middleInvest) {
        this.middleInvest = middleInvest;
    }

    public Integer getSeniorInvest() {
        return seniorInvest;
    }

    public void setSeniorInvest(Integer seniorInvest) {
        this.seniorInvest = seniorInvest;
    }

    public Integer getHighestInvest() {
        return highestInvest;
    }

    public void setHighestInvest(Integer highestInvest) {
        this.highestInvest = highestInvest;
    }

    public Integer getOneInvest() {
        return oneInvest;
    }

    public void setOneInvest(Integer oneInvest) {
        this.oneInvest = oneInvest;
    }

    public Integer getTwoInvest() {
        return twoInvest;
    }

    public void setTwoInvest(Integer twoInvest) {
        this.twoInvest = twoInvest;
    }

    public Integer getThreeInvest() {
        return threeInvest;
    }

    public void setThreeInvest(Integer threeInvest) {
        this.threeInvest = threeInvest;
    }

    public Integer getFourInvest() {
        return fourInvest;
    }

    public void setFourInvest(Integer fourInvest) {
        this.fourInvest = fourInvest;
    }

    public Integer getFiveInvest() {
        return fiveInvest;
    }

    public void setFiveInvest(Integer fiveInvest) {
        this.fiveInvest = fiveInvest;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public Integer getMaleCount() {
        return maleCount;
    }

    public void setMaleCount(Integer maleCount) {
        this.maleCount = maleCount;
    }

    public Integer getFemaleCount() {
        return femaleCount;
    }

    public void setFemaleCount(Integer femaleCount) {
        this.femaleCount = femaleCount;
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
