/**
 * Description:前端WEB用户投资债转认购债券实体类
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
 * @Description 债转投资相关
 * @Author sunss
 * @Date 2018/7/3 16:56
 */
public class TenderToCreditAssignCustomizeVO extends BaseVO implements Serializable {

	/**
	 * serialVersionUID:
	 */

	private static final long serialVersionUID = 1L;

	/**
	 * 借款编号
	 */
	private String borrowNid;
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
    private String assignCapital;
	/**
	 * 折价率
	 */
	private String creditDiscount;
	/**
     * 折后价格
     */
	private String assignPrice;
	/**
     * 实际支付
     */
    private String assignPay;
	/**
     * 预期收益
     */
    private String assignInterest;
	/**
     * 垫付利息
     */
	private String assignInterestAdvance;
	/**
     * 实际支付文本
     */
    private String assignPayText;
    /**
     * 债转期全部利息
     */
    private String creditInterest;
    /**
     * 债转利息
     */
    private String assignPayInterest;
    
    /***
     *实际支付金额
     */
    private String assignTotal;
    
    /***
     *实际支付金额本金
     */
    private String assignCapitalMax;
	
    public String getBorrowNid() {
        return borrowNid;
    }
    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
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
    public String getAssignCapital() {
        return assignCapital;
    }
    public void setAssignCapital(String assignCapital) {
        this.assignCapital = assignCapital;
    }
    public String getCreditDiscount() {
        return creditDiscount;
    }
    public void setCreditDiscount(String creditDiscount) {
        this.creditDiscount = creditDiscount;
    }
    public String getAssignPrice() {
        return assignPrice;
    }
    public void setAssignPrice(String assignPrice) {
        this.assignPrice = assignPrice;
    }
    public String getAssignInterestAdvance() {
        return assignInterestAdvance;
    }
    public void setAssignInterestAdvance(String assignInterestAdvance) {
        this.assignInterestAdvance = assignInterestAdvance;
    }
    public String getAssignInterest() {
        return assignInterest;
    }
    public void setAssignInterest(String assignInterest) {
        this.assignInterest = assignInterest;
    }
    public String getAssignPayText() {
        return assignPayText;
    }
    public void setAssignPayText(String assignPayText) {
        this.assignPayText = assignPayText;
    }
    public String getAssignPay() {
        return assignPay;
    }
    public void setAssignPay(String assignPay) {
        this.assignPay = assignPay;
    }
    public String getCreditInterest() {
        return creditInterest;
    }
    public void setCreditInterest(String creditInterest) {
        this.creditInterest = creditInterest;
    }
    public String getAssignPayInterest() {
        return assignPayInterest;
    }
    public void setAssignPayInterest(String assignPayInterest) {
        this.assignPayInterest = assignPayInterest;
    }
	public String getAssignTotal() {
		return assignTotal;
	}
	public void setAssignTotal(String assignTotal) {
		this.assignTotal = assignTotal;
	}
	public String getAssignCapitalMax() {
		return assignCapitalMax;
	}
	public void setAssignCapitalMax(String assignCapitalMax) {
		this.assignCapitalMax = assignCapitalMax;
	}
}
