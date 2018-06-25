package com.hyjf.am.vo.trade;

import com.hyjf.am.vo.BaseVO;

import java.math.BigDecimal;

public class MyRewardRecordCustomizeVO extends BaseVO {

	public Integer userId;
	public String username;
	/** 发奖时间  */
	public String rewardTime;

	public String borrowNid; //投资编号
	
	public BigDecimal account= new BigDecimal(0);//投资金额

	public BigDecimal pushMoney= new BigDecimal(0);//提成
	//奖励类型
	public Integer type;
	//优惠券编号
	public Integer couponType;
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRewardTime() {
		return rewardTime;
	}

	public void setRewardTime(String rewardTime) {
		this.rewardTime = rewardTime;
	}

	public String getBorrowNid() {
		return borrowNid;
	}

	public void setBorrowNid(String borrowNid) {
		this.borrowNid = borrowNid;
	}

	public BigDecimal getAccount() {
		return account;
	}

	public void setAccount(BigDecimal account) {
		this.account = account;
	}

	public BigDecimal getPushMoney() {
		return pushMoney;
	}

	public void setPushMoney(BigDecimal pushMoney) {
		this.pushMoney = pushMoney;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCouponType() {
        return couponType;
    }

    public void setCouponType(Integer couponType) {
        this.couponType = couponType;
    }

	
}
