package com.hyjf.am.vo.trade.repay;

import com.hyjf.am.vo.BaseVO;

/**
 * 用户待还款列表
 * @author hesy
 * @version RepayListCustomizeVO, v0.1 2018/6/27 15:46
 */
public class SponsorLogCustomizeVO extends BaseVO {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String status;

    // 项目还款方式
    private String borrowStyle;

    // 项目编号
    private String borrowNid;

    // 项目名称
    private String account;

    // 项目年华收益率
    private String borrowApr;

    // 项目期限
    private String borrowPeriod;

    private String createTime;
    
    private String oldBailAccountId;

    private String id;
    
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOldBailAccountId() {
		return oldBailAccountId;
	}

	public void setOldBailAccountId(String oldBailAccountId) {
		this.oldBailAccountId = oldBailAccountId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBorrowStyle() {
		return borrowStyle;
	}

	public void setBorrowStyle(String borrowStyle) {
		this.borrowStyle = borrowStyle;
	}

	public String getBorrowNid() {
		return borrowNid;
	}

	public void setBorrowNid(String borrowNid) {
		this.borrowNid = borrowNid;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
    
 
}
