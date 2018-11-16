/**
 * Description:（类功能描述-必填） 需要在每个方法前添加业务描述，方便业务业务行为的BI工作
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: Administrator
 * @version: 1.0
 * Created at: 2015年11月20日 下午5:24:10
 * Modification History:
 * Modified by : 
 */

package com.hyjf.am.vo.trade;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author Administrator
 */

/**
 * @ClassName PlanLockCustomize
 * @Description TODO
 * @author 孙小宝
 * @date 2016年11月24日 下午3:49:17
 */
public class PlanLockCustomizeVO extends BaseVO {

	/**
	 * serialVersionUID:
	 */

	private static final long serialVersionUID = 1L;
	/**
	 * 计划id
	 */
	private String id;
	/**
	 * 计划订单号
	 */
	private String accedeOrderId;
	/**
	 * 冻结订单号
	 */
	private String freezeOrderId;
	/**
	 * 计划编号
	 */
	private String debtPlanNid;
	/**
	 * 计划名称
	 */
	private String debtPlanName;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 加入金额
	 */
	private String accedeAccount;
	/**
	 * 订单余额
	 */
	private String accedeBalance;
	/**
	 * 冻结金额
	 */
	private String accedeFrost;
	/**
	 * 已派息
	 */
	private String money;
	/**
	 * 当前年化
	 */
	private String factApr;
	/**
	 * 当前年化
	 */
	private String actualApr;
	/**
	 * 加入时间
	 */
	private String createTime;
	/**
	 * 加入时间天
	 */
	private String createTimeDay;
	/**
	 * 加入时间分
	 */
	private String createTimeFen;
	/**
	 * 应清算时间
	 */
	private String liquidateShouldTime;
	/**
	 * 实际清算时间
	 */
	private String liquidateFactTime;
	/**
	 * 还款时间
	 */
	private String repayTime;
	/**
	 * 遍历次数
	 */
	private String cycleTimes;
	/**
	 * 此笔加入是否已经完成 0投资中 1投资完成 2还款中 3还款完成
	 */
	private String status;
	/**
	 * 应还本息
	 */
	private String repayAccount;
	/**
	 * 已还本息
	 */
	private String repayAccountYes;
	/**
	 * 待还本息
	 */
	private String repayAccountWait;
	/**
	 * 应还本金
	 */
	private String repayCapital;
	/**
	 * 已还本金
	 */
	private String repayCapitalYes;
	/**
	 * 应还利息
	 */
	private String repayInterest;
	/**
	 * 待还利息
	 */
	private String repayInterestWait;
	/**
	 * 已还利息
	 */
	private String repayInterestYes;
	/**
	 * 锁定期
	 */
	private String debtLockPeriod;
	/**
	 * 退出天数
	 */
	private String debtQuitPeriod;
	/**
	 * 预期利息
	 */
	private String expectApr;
	/**
	 * 计划状态
	 */
	private String debtPlanStatus;
	/**
	 * 实际回款总额
	 */
	private String repayAccountFact;
	/**
	 * 实际回款利息
	 */
	private String repayInterestFact;
	/**
	 * 服务费收取比例
	 */
	private String serviceFeeRate;
	/**
	 * 服务费
	 */
	private String serviceFee;

	/**
	 * 到期公允价值
	 */
	private String expireFairValue;

	/**
	 * 还款待冻结金额
	 */
	private String liquidatesRepayFrost;
	/**
	 * 最晚到账时间
	 */
	private String lastRepayTime;
	
	/**
	 * 投资类型 1 直投  2 汇添金
	 */
	private String tenderType;
	
	/**
	 * 优惠券类型描述
	 */
	private String couponType;
	
	/**
	 * 优惠券编号
	 */
	private String couponUserCode;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccedeOrderId() {
		return accedeOrderId;
	}

	public void setAccedeOrderId(String accedeOrderId) {
		this.accedeOrderId = accedeOrderId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAccedeAccount() {
		return accedeAccount;
	}

	public void setAccedeAccount(String accedeAccount) {
		this.accedeAccount = accedeAccount;
	}

	public String getAccedeBalance() {
		return accedeBalance;
	}

	public void setAccedeBalance(String accedeBalance) {
		this.accedeBalance = accedeBalance;
	}

	public String getAccedeFrost() {
		return accedeFrost;
	}

	public void setAccedeFrost(String accedeFrost) {
		this.accedeFrost = accedeFrost;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getActualApr() {
		return actualApr;
	}

	public void setActualApr(String actualApr) {
		this.actualApr = actualApr;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCycleTimes() {
		return cycleTimes;
	}

	public void setCycleTimes(String cycleTimes) {
		this.cycleTimes = cycleTimes;
	}

	public String getFactApr() {
		return factApr;
	}

	public void setFactApr(String factApr) {
		this.factApr = factApr;
	}

	public String getDebtPlanNid() {
		return debtPlanNid;
	}

	public void setDebtPlanNid(String debtPlanNid) {
		this.debtPlanNid = debtPlanNid;
	}

	public String getDebtPlanName() {
		return debtPlanName;
	}

	public void setDebtPlanName(String debtPlanName) {
		this.debtPlanName = debtPlanName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLiquidateFactTime() {
		return liquidateFactTime;
	}

	public void setLiquidateFactTime(String liquidateFactTime) {
		this.liquidateFactTime = liquidateFactTime;
	}

	public String getRepayTime() {
		return repayTime;
	}

	public void setRepayTime(String repayTime) {
		this.repayTime = repayTime;
	}

	public String getRepayAccount() {
		return repayAccount;
	}

	public void setRepayAccount(String repayAccount) {
		this.repayAccount = repayAccount;
	}

	public String getRepayCapital() {
		return repayCapital;
	}

	public void setRepayCapital(String repayCapital) {
		this.repayCapital = repayCapital;
	}

	public String getRepayInterest() {
		return repayInterest;
	}

	public void setRepayInterest(String repayInterest) {
		this.repayInterest = repayInterest;
	}

	public String getRepayInterestWait() {
		return repayInterestWait;
	}

	public void setRepayInterestWait(String repayInterestWait) {
		this.repayInterestWait = repayInterestWait;
	}

	public String getRepayInterestYes() {
		return repayInterestYes;
	}

	public void setRepayInterestYes(String repayInterestYes) {
		this.repayInterestYes = repayInterestYes;
	}

	public String getRepayCapitalYes() {
		return repayCapitalYes;
	}

	public void setRepayCapitalYes(String repayCapitalYes) {
		this.repayCapitalYes = repayCapitalYes;
	}

	public String getRepayAccountYes() {
		return repayAccountYes;
	}

	public void setRepayAccountYes(String repayAccountYes) {
		this.repayAccountYes = repayAccountYes;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRepayAccountWait() {
		return repayAccountWait;
	}

	public void setRepayAccountWait(String repayAccountWait) {
		this.repayAccountWait = repayAccountWait;
	}

	public String getLiquidateShouldTime() {
		return liquidateShouldTime;
	}

	public void setLiquidateShouldTime(String liquidateShouldTime) {
		this.liquidateShouldTime = liquidateShouldTime;
	}

	public String getDebtLockPeriod() {
		return debtLockPeriod;
	}

	public void setDebtLockPeriod(String debtLockPeriod) {
		this.debtLockPeriod = debtLockPeriod;
	}

	public String getExpectApr() {
		return expectApr;
	}

	public void setExpectApr(String expectApr) {
		this.expectApr = expectApr;
	}

	public String getDebtPlanStatus() {
		return debtPlanStatus;
	}

	public void setDebtPlanStatus(String debtPlanStatus) {
		this.debtPlanStatus = debtPlanStatus;
	}

	public String getRepayAccountFact() {
		return repayAccountFact;
	}

	public void setRepayAccountFact(String repayAccountFact) {
		this.repayAccountFact = repayAccountFact;
	}

	public String getRepayInterestFact() {
		return repayInterestFact;
	}

	public void setRepayInterestFact(String repayInterestFact) {
		this.repayInterestFact = repayInterestFact;
	}

	public String getCreateTimeDay() {
		return createTimeDay;
	}

	public void setCreateTimeDay(String createTimeDay) {
		this.createTimeDay = createTimeDay;
	}

	public String getCreateTimeFen() {
		return createTimeFen;
	}

	public void setCreateTimeFen(String createTimeFen) {
		this.createTimeFen = createTimeFen;
	}

	public String getDebtQuitPeriod() {
		return debtQuitPeriod;
	}

	public void setDebtQuitPeriod(String debtQuitPeriod) {
		this.debtQuitPeriod = debtQuitPeriod;
	}

	public String getFreezeOrderId() {
		return freezeOrderId;
	}

	public void setFreezeOrderId(String freezeOrderId) {
		this.freezeOrderId = freezeOrderId;
	}

	public String getServiceFeeRate() {
		return serviceFeeRate;
	}

	public void setServiceFeeRate(String serviceFeeRate) {
		this.serviceFeeRate = serviceFeeRate;
	}

	public String getExpireFairValue() {
		return expireFairValue;
	}

	public void setExpireFairValue(String expireFairValue) {
		this.expireFairValue = expireFairValue;
	}

	public String getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(String serviceFee) {
		this.serviceFee = serviceFee;
	}

	public String getLiquidatesRepayFrost() {
		return liquidatesRepayFrost;
	}

	public void setLiquidatesRepayFrost(String liquidatesRepayFrost) {
		this.liquidatesRepayFrost = liquidatesRepayFrost;
	}

	public String getLastRepayTime() {
		return lastRepayTime;
	}

	public void setLastRepayTime(String lastRepayTime) {
		this.lastRepayTime = lastRepayTime;
	}

    public String getTenderType() {
        return tenderType;
    }

    public void setTenderType(String tenderType) {
        this.tenderType = tenderType;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public String getCouponUserCode() {
        return couponUserCode;
    }

    public void setCouponUserCode(String couponUserCode) {
        this.couponUserCode = couponUserCode;
    }

}
