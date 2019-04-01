package com.hyjf.am.trade.bean.repay;

import com.hyjf.am.vo.trade.borrow.BorrowRepayVO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RepayBean extends BorrowRepayVO implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 4073070104153180850L;
	// 当前还款期数
	public String borrowPeriod;
	// 用户的ip地址
	private String ip;
	/** 垫付机构ID*/
	public Integer repayUserId;
	/**
	 * 校验标示 0:成功 1:失败
	 */
	public int flag;
	/**
	 * 所悟描述
	 */
	public String message;

	/** 用户还款详情 */
	private List<RepayRecoverBean> recoverList = new ArrayList<RepayRecoverBean>();
	
	/** 用户还款详情 */
	private List<RepayDetailBean> repayPlanList = new ArrayList<RepayDetailBean>();

	// 还款方式(1逾期还款0其他)
	private String lateStatus;
	
	public RepayBean() {
		super();
	}
	public List<RepayRecoverBean> getRecoverList() {
		return recoverList;
	}

	public void setRecoverList(List<RepayRecoverBean> recoverList) {
		this.recoverList = recoverList;
	}

	public String getBorrowPeriod() {
		return borrowPeriod;
	}

	public void setBorrowPeriod(String borrowPeriod) {
		this.borrowPeriod = borrowPeriod;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Override
    public Integer getRepayUserId() {
		return repayUserId;
	}

	@Override
    public void setRepayUserId(Integer repayUserId) {
		this.repayUserId = repayUserId;
	}

	public List<RepayDetailBean> getRepayPlanList() {
		return repayPlanList;
	}

	public void setRepayPlanList(List<RepayDetailBean> repayPlanList) {
		this.repayPlanList = repayPlanList;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public String getLateStatus() {
		return lateStatus;
	}

	public void setLateStatus(String lateStatus) {
		this.lateStatus = lateStatus;
	}
}
