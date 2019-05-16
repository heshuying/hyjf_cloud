package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;

import java.math.BigDecimal;

/**
 * @author xiehuili on 2019/3/25.
 */
public class NewYearActivityVO extends BaseVO {

    public Integer id;

    public Integer userId;

    public String userName;

    public String trueName;

    public BigDecimal investAmount;

    //奖励名称
    public String reward;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public BigDecimal getInvestAmount() {
        return investAmount;
    }

    public void setInvestAmount(BigDecimal investAmount) {
        this.investAmount = investAmount;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }
}
