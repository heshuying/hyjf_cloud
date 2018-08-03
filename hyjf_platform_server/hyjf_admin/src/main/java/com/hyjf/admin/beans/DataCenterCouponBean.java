package com.hyjf.admin.beans;

import com.hyjf.am.vo.admin.coupon.CouponBackMoneyCustomize;
import com.hyjf.common.paginator.Paginator;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author fq
 */
public class DataCenterCouponBean implements Serializable {

	/**
	 * serialVersionUID
	 */

	private static final long serialVersionUID = 387630498860089653L;

	/**
	 * 翻页机能用的隐藏变量
	 */
	private int paginatorPage = 1;

	/**
	 * 列表画面自定义标签上的用翻页对象：paginator
	 */
	private Paginator paginator;
	/**
	 * 列表
	 */
	private List<CouponBackMoneyCustomize> recordList;
	
	private String orderId;
	private String username;
	private String couponCode;
	private String borrowNid;
	private String couponReciveStatus;

    /**
     * 检索条件 时间开始
     */
    private String timeStartSrch;

	/**
	 * 检索条件 时间结束
	 */
	private String timeEndSrch;
	
	
	
	


	public int getPaginatorPage() {
		if (paginatorPage == 0) {
			paginatorPage = 1;
		}
		return paginatorPage;
	}

	public void setPaginatorPage(int paginatorPage) {
		this.paginatorPage = paginatorPage;
	}

	public Paginator getPaginator() {
		return paginator;
	}

	public void setPaginator(Paginator paginator) {
		this.paginator = paginator;
	}
	

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getCouponReciveStatus() {
        return couponReciveStatus;
    }

    public void setCouponReciveStatus(String couponReciveStatus) {
        this.couponReciveStatus = couponReciveStatus;
    }

    /**
	 * timeStartSrch
	 * 
	 * @return the timeStartSrch
	 */

	public String getTimeStartSrch() {
		return timeStartSrch;
	}

	/**
	 * @param timeStartSrch
	 *            the timeStartSrch to set
	 */

	public void setTimeStartSrch(String timeStartSrch) {
		this.timeStartSrch = timeStartSrch;
	}


    /**
	 * timeEndSrch
	 * 
	 * @return the timeEndSrch
	 */

	public String getTimeEndSrch() {
		return timeEndSrch;
	}

	/**
	 * @param timeEndSrch
	 *            the timeEndSrch to set
	 */

	public void setTimeEndSrch(String timeEndSrch) {
		this.timeEndSrch = timeEndSrch;
	}

    public List<CouponBackMoneyCustomize> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<CouponBackMoneyCustomize> recordList) {
        this.recordList = recordList;
    }


}
