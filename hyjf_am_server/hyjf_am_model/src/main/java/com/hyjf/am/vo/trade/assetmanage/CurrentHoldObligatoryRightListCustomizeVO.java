package com.hyjf.am.vo.trade.assetmanage;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * web端当前持有散标列表
 */
public class CurrentHoldObligatoryRightListCustomizeVO extends BaseVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 358190081082338992L;
	/**项目名称*/
	private String projectName;
	/**项目编号*/
	private String borrowNid;
	/**项目期限*/
	private String borrowPeriod;
	/**项目还款方式*/
    private String borrowStyle;
    /**项目类别*/
    private String borrowClass;
    /**项目类别*/
    private String projectType;
	/**项目年化收益率*/
	private String borrowApr;
	/**用户id*/
	private String tenUserId;
	/**投资订单号*/
	private String nid;
	/**投资时间*/
	private String addtime;
	/**预计还款时间*/
	private String investDate;
	/**待还本金*/
	private String capital;
	/**项目编号*/
	private String type;
	/**说明*/
	private String data;
	/**优惠券编号*/
	private String couponType;
	/**待收总额*/
	private String totalWait;
	/** 债权承接下载投资协议使用   start*/
	/**债转标号*/
    private String creditNid;
    /**债转投标单号*/
    private String creditTenderNid;
    /** 债权承接下载投资协议使用   end*/
    
	/**出让人userid*/
	private String creditUserId;
	/**法大大协议生成状态：0:初始,1:成功,2:失败，3下载成功*/
	private int fddStatus;
    /*产品加息收益率*/
    private String borrowExtraYield;
    
    public int getFddStatus() {
        return fddStatus;
    }
    public void setFddStatus(int fddStatus) {
        this.fddStatus = fddStatus;
    }
    public String getBorrowNid() {
        return borrowNid;
    }
    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }
    public String getBorrowPeriod() {
        return borrowPeriod;
    }
    public void setBorrowPeriod(String borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }
    public String getBorrowApr() {
        return borrowApr;
    }
    public void setBorrowApr(String borrowApr) {
        this.borrowApr = borrowApr;
    }
    public String getTenUserId() {
        return tenUserId;
    }
    public void setTenUserId(String tenUserId) {
        this.tenUserId = tenUserId;
    }
    public String getNid() {
        return nid;
    }
    public void setNid(String nid) {
        this.nid = nid;
    }
    public String getAddtime() {
        return addtime;
    }
    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }
    public String getInvestDate() {
        if("投资中".equals(this.data)){
            return "--";
        }else{
            return investDate;
        }
        
    }
    public void setInvestDate(String investDate) {
        this.investDate = investDate;
    }
    public String getCapital() {
        return capital;
    }
    public void setCapital(String capital) {
        this.capital = capital;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }
    public String getCouponType() {
        return couponType;
    }
    public void setCouponType(String couponType) {
        this.couponType = couponType;
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
    public String getBorrowStyle() {
        return borrowStyle;
    }
    public void setBorrowStyle(String borrowStyle) {
        this.borrowStyle = borrowStyle;
    }
    public String getBorrowClass() {
        return borrowClass;
    }
    public void setBorrowClass(String borrowClass) {
        this.borrowClass = borrowClass;
    }
    public String getProjectType() {
        return projectType;
    }
    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }
    public String getCreditNid() {
        return creditNid;
    }
    public void setCreditNid(String creditNid) {
        this.creditNid = creditNid;
    }
    public String getCreditTenderNid() {
        return creditTenderNid;
    }
    public void setCreditTenderNid(String creditTenderNid) {
        this.creditTenderNid = creditTenderNid;
    }
	public String getCreditUserId() {
		return creditUserId;
	}
	public void setCreditUserId(String creditUserId) {
		this.creditUserId = creditUserId;
	}

    public String getBorrowExtraYield() {
        return borrowExtraYield;
    }

    public void setBorrowExtraYield(String borrowExtraYield) {
        this.borrowExtraYield = borrowExtraYield;
    }
}