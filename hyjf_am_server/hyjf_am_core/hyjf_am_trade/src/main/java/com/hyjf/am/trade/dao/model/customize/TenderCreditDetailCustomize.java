/**
 * Description:可转让投资列表实体类
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: Administrator
 * @version: 1.0
 * Created at: 2015年11月20日 下午5:24:10
 * Modification History:
 * Modified by : 
 */

package com.hyjf.am.trade.dao.model.customize;

import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * web用户转让记录列表实体
 */

public class TenderCreditDetailCustomize {

	/**
	 * serialVersionUID:
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 债转id
	 */
	private Integer creditId;
	/**
	 * 债转nid
	 */
	private Integer creditNid;
	/**
	 * 转让用户id
	 */
	private Integer creditUserId;
	/**
	 * 原标nid
	 */
	private String bidNid;
	/**
	 * 原标年化利率
	 */
	private String bidApr;
	/**
	 * 原标标题
	 */
	private String bidName;
	/**
	 * 借款类型
	 */
	private String borrowStyle;
	/**
	 * 借款类型名称
	 */
	private String borrowStyleName;
	/**
	 * 投标nid
	 */
	private String tenderNid;
	/**
	 * 转让状态，0.进行中，1.停止
	 */
	private Integer creditStatus;
	/**
	 * 排序
	 */
	private Integer creditOrder;
	/**
	 * 债转期数
	 */
	private Integer creditPeriod;
	/**
	 * 债转期限
	 */
	private Integer creditTerm;
	/**
	 * 债转本金
	 */
	private String creditCapital;
	/**
	 * 债转总额
	 */
	private String creditAccount;
	/**
	 * 债转总利息
	 */
	private String creditInterest;
	/**
	 * 需垫付利息
	 */
	private String creditInterestAdvance;
	/**
	 * 折价率
	 */
	private String creditDiscount;
	/**
	 * 总收入，本金+垫付利息
	 */
	private String creditIncome;
	/**
	 * 服务费
	 */
	private String creditFee;
	/**
	 * 出让价格
	 */
	private String creditPrice;
	/**
	 * 已认购本金
	 */
	private String creditCapitalAssigned;
	/**
	 * 已垫付利息
	 */
	private String creditInterestAssigned;
	/**
	 * 已还款总额
	 */
	private String creditRepayAccount;
	/**
	 * 已还本金
	 */
	private String creditRepayCapital;
	/**
	 * 已还利息
	 */
	private String creditRepayInterest;
	/**
	 * 债转最后还款日
	 */
	private String creditRepayEndTime;
	/**
	 * 上次还款日
	 */
	private String creditRepayLastTime;
	/**
	 * 下次还款日
	 */
	private String creditRepayNextTime;
	/**
	 * 最终实际还款日
	 */
	private String creditRepayYesTime;
	/**
	 * 创建日期
	 */
	private String createDate;
	/**
	 * 创建时间
	 */
	private String addTime;
	/**
	 * 结束时间
	 */
	private String endTime;
	/**
	 * 认购时间
	 */
	private String assignTime;
	/**
	 * 投资次数
	 */
	private Integer assignNum;
	/**
     * 
     */
	private Integer recoverPeriod;

	/**
	 * 转让进度
	 */
	private String creditInProgress;
	/**
	 * 创建时间INT
	 */
	private Integer addTimeInt;
	/**
	 * 投资时间
	 */
	private String tenderTime;

	/**
	 * 持有期限
	 */
	private String tenderPeriod;

	/**
	 * 剩余期限
	 */
	private String lastDays;
	/**
	 * 累计已收金额
	 */
	private String receivedAccount;
	
	/**
	 * 项目名称
	 */
	private String projectName;

	public Integer getCreditId() {
		return creditId;
	}

	public void setCreditId(Integer creditId) {
		this.creditId = creditId;
	}

	public Integer getCreditNid() {
		return creditNid;
	}

	public void setCreditNid(Integer creditNid) {
		this.creditNid = creditNid;
	}

	public Integer getCreditUserId() {
		return creditUserId;
	}

	public void setCreditUserId(Integer creditUserId) {
		this.creditUserId = creditUserId;
	}

	public String getBidNid() {
		return bidNid;
	}

	public void setBidNid(String bidNid) {
		this.bidNid = bidNid;
	}

	public String getBidApr() {
		return bidApr;
	}

	public void setBidApr(String bidApr) {
		this.bidApr = bidApr;
	}

	public String getBidName() {
		return bidName;
	}

	public void setBidName(String bidName) {
		this.bidName = bidName;
	}

	public String getTenderNid() {
		return tenderNid;
	}

	public void setTenderNid(String tenderNid) {
		this.tenderNid = tenderNid;
	}

	public Integer getCreditStatus() {
		return creditStatus;
	}

	public void setCreditStatus(Integer creditStatus) {
		this.creditStatus = creditStatus;
	}

	public Integer getCreditOrder() {
		return creditOrder;
	}

	public void setCreditOrder(Integer creditOrder) {
		this.creditOrder = creditOrder;
	}

	public Integer getCreditPeriod() {
		return creditPeriod;
	}

	public void setCreditPeriod(Integer creditPeriod) {
		this.creditPeriod = creditPeriod;
	}

	public Integer getCreditTerm() {
		return creditTerm;
	}

	public void setCreditTerm(Integer creditTerm) {
		this.creditTerm = creditTerm;
	}

	public String getCreditCapital() {
		return creditCapital;
	}

	public void setCreditCapital(String creditCapital) {
		this.creditCapital = creditCapital;
	}

	public String getCreditAccount() {
		return creditAccount;
	}

	public void setCreditAccount(String creditAccount) {
		this.creditAccount = creditAccount;
	}

	public String getCreditInterest() {
		return creditInterest;
	}

	public void setCreditInterest(String creditInterest) {
		this.creditInterest = creditInterest;
	}

	public String getCreditInterestAdvance() {
		return creditInterestAdvance;
	}

	public void setCreditInterestAdvance(String creditInterestAdvance) {
		this.creditInterestAdvance = creditInterestAdvance;
	}

	public String getCreditDiscount() {
		return creditDiscount;
	}

	public void setCreditDiscount(String creditDiscount) {
		this.creditDiscount = creditDiscount;
	}

	public String getCreditIncome() {
		return creditIncome;
	}

	public void setCreditIncome(String creditIncome) {
		this.creditIncome = creditIncome;
	}

	public String getCreditFee() {
		return creditFee;
	}

	public void setCreditFee(String creditFee) {
		this.creditFee = creditFee;
	}

	public String getCreditPrice() {
		return creditPrice;
	}

	public void setCreditPrice(String creditPrice) {
		this.creditPrice = creditPrice;
	}

	public String getCreditCapitalAssigned() {
		return creditCapitalAssigned;
	}

	public void setCreditCapitalAssigned(String creditCapitalAssigned) {
		this.creditCapitalAssigned = creditCapitalAssigned;
	}

	public String getCreditInterestAssigned() {
		return creditInterestAssigned;
	}

	public void setCreditInterestAssigned(String creditInterestAssigned) {
		this.creditInterestAssigned = creditInterestAssigned;
	}

	public String getCreditRepayAccount() {
		return creditRepayAccount;
	}

	public void setCreditRepayAccount(String creditRepayAccount) {
		this.creditRepayAccount = creditRepayAccount;
	}

	public String getCreditRepayCapital() {
		return creditRepayCapital;
	}

	public void setCreditRepayCapital(String creditRepayCapital) {
		this.creditRepayCapital = creditRepayCapital;
	}

	public String getCreditRepayInterest() {
		return creditRepayInterest;
	}

	public void setCreditRepayInterest(String creditRepayInterest) {
		this.creditRepayInterest = creditRepayInterest;
	}

	public String getCreditRepayEndTime() {
		return creditRepayEndTime;
	}

	public void setCreditRepayEndTime(String creditRepayEndTime) {
		this.creditRepayEndTime = creditRepayEndTime;
	}

	public String getCreditRepayLastTime() {
		return creditRepayLastTime;
	}

	public void setCreditRepayLastTime(String creditRepayLastTime) {
		this.creditRepayLastTime = creditRepayLastTime;
	}

	public String getCreditRepayNextTime() {
		return creditRepayNextTime;
	}

	public void setCreditRepayNextTime(String creditRepayNextTime) {
		this.creditRepayNextTime = creditRepayNextTime;
	}

	public String getCreditRepayYesTime() {
		return creditRepayYesTime;
	}

	public void setCreditRepayYesTime(String creditRepayYesTime) {
		this.creditRepayYesTime = creditRepayYesTime;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getAssignTime() {
		return assignTime;
	}

	public void setAssignTime(String assignTime) {
		this.assignTime = assignTime;
	}

	public Integer getAssignNum() {
		return assignNum;
	}

	public void setAssignNum(Integer assignNum) {
		this.assignNum = assignNum;
	}

	public Integer getRecoverPeriod() {
		return recoverPeriod;
	}

	public void setRecoverPeriod(Integer recoverPeriod) {
		this.recoverPeriod = recoverPeriod;
	}

	public String getCreditInProgress() {
		return creditInProgress;
	}

	public void setCreditInProgress(String creditInProgress) {
		this.creditInProgress = creditInProgress;
	}

	public String getBorrowStyle() {
		return borrowStyle;
	}

	public void setBorrowStyle(String borrowStyle) {
		this.borrowStyle = borrowStyle;
	}

	public String getBorrowStyleName() {
		return borrowStyleName;
	}

	public void setBorrowStyleName(String borrowStyleName) {
		this.borrowStyleName = borrowStyleName;
	}

	public Integer getAddTimeInt() {
		return addTimeInt;
	}

	public void setAddTimeInt(Integer addTimeInt) {
		this.addTimeInt = addTimeInt;
	}

	public String getTenderTime() {
		if (StringUtils.isNotEmpty(tenderTime)) {
			return tenderTime;
		} else {
			return GetDate.date_sdf.format(new Date());
		}

	}

	public void setTenderTime(String tenderTime) {
		if (StringUtils.isNotEmpty(tenderTime)) {
			this.tenderTime = tenderTime;
		} else {
			this.tenderTime = GetDate.date_sdf.format(new Date());
		}
	}

	public String getTenderPeriod() {
		return tenderPeriod;
	}

	public void setTenderPeriod(String tenderPeriod) {
		this.tenderPeriod = tenderPeriod;
	}

	public String getLastDays() {
		return lastDays;
	}

	public void setLastDays(String lastDays) {
		this.lastDays = lastDays;
	}

	public String getReceivedAccount() {
		return receivedAccount;
	}

	public void setReceivedAccount(String receivedAccount) {
		this.receivedAccount = receivedAccount;
	}

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

}
