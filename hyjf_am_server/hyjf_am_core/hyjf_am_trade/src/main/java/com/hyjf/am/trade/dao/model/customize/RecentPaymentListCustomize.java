package com.hyjf.am.trade.dao.model.customize;

import java.io.Serializable;
/**
 * 
 * 优惠券实体
 * @author hsy
 * @version hyjf 1.0
 * @since hyjf 1.0 2016年6月12日
 * @see
 */
public class RecentPaymentListCustomize implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 358190081082338992L;
	/**项目名称*/
	private String projectName;
	/**项目年化收益率*/
	private String borrowApr;
	/**预计还款时间*/
	private String investDate;
	/**待收总额*/
	private String totalWait;
	/**项目编号*/
    private String type;
    /**优惠券编号*/
    private String couponType;
	/**项目编号*/
	private String projectNid;

    public String getBorrowApr() {
        return borrowApr;
    }
    public void setBorrowApr(String borrowApr) {
        this.borrowApr = borrowApr;
    }
	public String getInvestDate() {
        return investDate;
    }
    public void setInvestDate(String investDate) {
        this.investDate = investDate;
    }
    public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
    public String getTotalWait() {
        return totalWait;
    }
    public void setTotalWait(String totalWait) {
        this.totalWait = totalWait;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getCouponType() {
        return couponType;
    }
    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }
	public String getProjectNid() {
		return projectNid;
	}
	public void setProjectNid(String projectNid) {
		this.projectNid = projectNid;
	}
}