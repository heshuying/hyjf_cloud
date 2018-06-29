/**
 * Description:前端WEB用户投资债转详细实体类
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
 * @author jijun 20180628
 */

public class TenderToCreditDetailCustomizeVO extends BaseVO implements Serializable {

	/**
	 * serialVersionUID:
	 */

	private static final long serialVersionUID = 1L;

	/**
	 * 借款编号
	 */
	private String borrowNid;
	/**
	 * 年化收益
	 */
	private String borrowApr;
	/**
	 * 还款方式
	 */
	private String borrowStyle;
	/**
	 * 债转编号
	 */
	private String creditNid;
	/**
	 * 投资编号
	 */
	private String tenderNid;
	/**
	 * 转让本金
	 */
	private String creditCapital;
	/**
	 * 可承接本金
	 */
	private String creditAssignCapital;
	/**
	 * 折价率
	 */
	private String creditDiscount;
	/**
	 * 债转期限
	 */
	private String creditTerm;
	/**
	 * 项目期限
	 */
	private String borrowPeriod;
	/**
	 * 转让价格
	 */
	private String creditPrice;
	
	/**
     * 承接时间
     */
    private String signTime;
	/**
	 * 转让时间
	 */
	private String creditTime;
	/**
	 * 转让时间
	 */
	private int creditTimeInt;
	/**
	 * 转让人
	 */
	private String creditUserTrueName;
	/**
	 * 转让人ID
	 */
	private String creditUserId;
	/**
	 * 标的类型
	 */
	private String type;
	/* 项目状态 status 10 定时发标 11投资中 12复审中 13 还款中 14 已还款 15 已流标 */
	private String status;
	/* 项目区分 comOrPer 项目是个人项目还是企业项目 1企业 2个人 */
	private String comOrPer;
	/**
	 * 债转最后还款日
	 */
	private String creditRepayEndTime;
	/**
	 * 债转持有天数
	 */
	private String creditTermHold;

	public String getComOrPer() {
		return comOrPer;
	}

	public void setComOrPer(String comOrPer) {
		this.comOrPer = comOrPer;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBorrowNid() {
		return borrowNid;
	}

	public void setBorrowNid(String borrowNid) {
		this.borrowNid = borrowNid;
	}

	public String getBorrowApr() {
		return borrowApr;
	}

	public void setBorrowApr(String borrowApr) {
		this.borrowApr = borrowApr;
	}

	public String getBorrowStyle() {
		return borrowStyle;
	}

	public void setBorrowStyle(String borrowStyle) {
		this.borrowStyle = borrowStyle;
	}

	public String getCreditNid() {
		return creditNid;
	}

	public void setCreditNid(String creditNid) {
		this.creditNid = creditNid;
	}

	public String getTenderNid() {
		return tenderNid;
	}

	public void setTenderNid(String tenderNid) {
		this.tenderNid = tenderNid;
	}

	public String getCreditCapital() {
		return creditCapital;
	}

	public void setCreditCapital(String creditCapital) {
		this.creditCapital = creditCapital;
	}

	public String getCreditAssignCapital() {
		return creditAssignCapital;
	}

	public void setCreditAssignCapital(String creditAssignCapital) {
		this.creditAssignCapital = creditAssignCapital;
	}

	public String getCreditDiscount() {
		return creditDiscount;
	}

	public void setCreditDiscount(String creditDiscount) {
		this.creditDiscount = creditDiscount;
	}

	public String getCreditTerm() {
		return creditTerm;
	}

	public void setCreditTerm(String creditTerm) {
		this.creditTerm = creditTerm;
	}

	public String getBorrowPeriod() {
		return borrowPeriod;
	}

	public void setBorrowPeriod(String borrowPeriod) {
		this.borrowPeriod = borrowPeriod;
	}

	public String getCreditPrice() {
		return creditPrice;
	}

	public void setCreditPrice(String creditPrice) {
		this.creditPrice = creditPrice;
	}

	public String getCreditTime() {
		return creditTime;
	}

	public void setCreditTime(String creditTime) {
		this.creditTime = creditTime;
	}

	public String getCreditUserTrueName() {
		return creditUserTrueName;
	}

	public void setCreditUserTrueName(String creditUserTrueName) {
		String creditUserFormat = "";
		if (creditUserTrueName.length() > 1) {
			char[] replaceArry = creditUserTrueName.toCharArray();
			creditUserFormat = String.valueOf(replaceArry[0]);
			for (int i = 1; i < replaceArry.length; i++) {
				creditUserFormat += "*";
			}
		} else if (creditUserTrueName.length() == 1) {
			creditUserFormat = creditUserTrueName + "*";
		} else {
			creditUserFormat = "**";
		}
		this.creditUserTrueName = creditUserFormat;
	}

	public String getSignTime() {
        return signTime;
    }

    public void setSignTime(String signTime) {
        this.signTime = signTime;
    }

    public String getCreditUserId() {
		return creditUserId;
	}

	public void setCreditUserId(String creditUserId) {
		this.creditUserId = creditUserId;
	}

	public String getCreditRepayEndTime() {
		return creditRepayEndTime;
	}

	public void setCreditRepayEndTime(String creditRepayEndTime) {
		this.creditRepayEndTime = creditRepayEndTime;
	}

	public int getCreditTimeInt() {
		return creditTimeInt;
	}

	public void setCreditTimeInt(int creditTimeInt) {
		this.creditTimeInt = creditTimeInt;
	}

	public String getCreditTermHold() {
		return creditTermHold;
	}

	public void setCreditTermHold(String creditTermHold) {
		this.creditTermHold = creditTermHold;
	}

}
