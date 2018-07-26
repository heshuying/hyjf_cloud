package com.hyjf.am.vo.trade;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author tanyy
 * @version OperationReportJobVO, v0.1 2018/7/25 10:42
 */
public class OperationReportJobVO extends BaseVO implements Serializable {
	public int citycode;
	public String name;
	public int count;
	public int sex;
	public String title;//标题
	public BigDecimal sumAccount;//总金额
	public String dealMonth;//交易月份
	public Integer dealSum;//成交笔数
	public Integer userId;//用户id
	public String userName;//用户名
	public int getCitycode() {
		return citycode;
	}
	public void setCitycode(int citycode) {
		this.citycode = citycode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BigDecimal getSumAccount() {
		return sumAccount;
	}

	public void setSumAccount(BigDecimal sumAccount) {
		this.sumAccount = sumAccount;
	}

	public String getDealMonth() {
		return dealMonth;
	}

	public void setDealMonth(String dealMonth) {
		this.dealMonth = dealMonth;
	}

	public Integer getDealSum() {
		return dealSum;
	}

	public void setDealSum(Integer dealSum) {
		this.dealSum = dealSum;
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
}
