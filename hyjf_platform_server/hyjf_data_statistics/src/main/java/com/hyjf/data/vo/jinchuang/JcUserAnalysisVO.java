/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.data.vo.jinchuang;

import com.hyjf.data.vo.BaseVO;

import java.math.BigDecimal;

/**
 * @author yaoyong
 * @version JcUserAnalysisVO, v0.1 2019/6/20 11:17
 */
public class JcUserAnalysisVO extends BaseVO {

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
     * 用户收益
     */
    private BigDecimal interest;

    /**
     * 男性人数
     */
    private String maleCount;

    /**
     * 女性人数
     */
    private String femaleCount;

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

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
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
}
