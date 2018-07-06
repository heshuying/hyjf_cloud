
package com.hyjf.am.vo.trade.repay;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author hesy
 * @version RepayManageServiceImpl, v0.1 2018/6/26 18:15
 */
public class BorrowAuthCustomizeVO extends BaseVO implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5429348260157960848L;

	private String borrow_nid;

	// 项目类型
	private String borrow_name;

	// 项目还款方式
	private String borrowStyle;

	// 项目编号
	private String borrowType;

	// 项目名称
	private String borrowApr;

	// 项目年华收益率
	private String borrowPeriod;

	// 项目期限
	private String borrowAccount;

	// 项目是个人还是企业
	private String borrowSchedule;

	// 借款时间
	private String time;

	// 借款金额
	private String borrowStyleName;

	// 还款金额
	private String repayAccountAll;

	// 还款时间
	private String entrustedUserName;

	// 项目状态
	private String entrustedRelName;

	// 融通宝资产编号
	private String trusteePayTime;

    public String getBorrow_nid() {
        return borrow_nid;
    }

    public void setBorrow_nid(String borrow_nid) {
        this.borrow_nid = borrow_nid;
    }

    public String getBorrow_name() {
        return borrow_name;
    }

    public void setBorrow_name(String borrow_name) {
        this.borrow_name = borrow_name;
    }

    public String getBorrowStyle() {
        return borrowStyle;
    }

    public void setBorrowStyle(String borrowStyle) {
        this.borrowStyle = borrowStyle;
    }

    public String getBorrowType() {
        return borrowType;
    }

    public void setBorrowType(String borrowType) {
        this.borrowType = borrowType;
    }

    public String getBorrowApr() {
        return borrowApr;
    }

    public void setBorrowApr(String borrowApr) {
        this.borrowApr = borrowApr;
    }

    public String getBorrowPeriod() {
        return borrowPeriod;
    }

    public void setBorrowPeriod(String borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }

    public String getBorrowAccount() {
        return borrowAccount;
    }

    public void setBorrowAccount(String borrowAccount) {
        this.borrowAccount = borrowAccount;
    }

    public String getBorrowSchedule() {
        return borrowSchedule;
    }

    public void setBorrowSchedule(String borrowSchedule) {
        this.borrowSchedule = borrowSchedule;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBorrowStyleName() {
        return borrowStyleName;
    }

    public void setBorrowStyleName(String borrowStyleName) {
        this.borrowStyleName = borrowStyleName;
    }

    public String getRepayAccountAll() {
        return repayAccountAll;
    }

    public void setRepayAccountAll(String repayAccountAll) {
        this.repayAccountAll = repayAccountAll;
    }

    public String getEntrustedUserName() {
        return entrustedUserName;
    }

    public void setEntrustedUserName(String entrustedUserName) {
        this.entrustedUserName = entrustedUserName;
    }

    public String getEntrustedRelName() {
        return entrustedRelName;
    }

    public void setEntrustedRelName(String entrustedRelName) {
        this.entrustedRelName = entrustedRelName;
    }

    public String getTrusteePayTime() {
        return trusteePayTime;
    }

    public void setTrusteePayTime(String trusteePayTime) {
        this.trusteePayTime = trusteePayTime;
    }

}
