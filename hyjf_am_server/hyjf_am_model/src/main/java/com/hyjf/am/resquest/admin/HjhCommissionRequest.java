/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author libin
 * @version HjhCommissionRequest.java, v0.1 2018年8月7日 下午3:39:19
 */
public class HjhCommissionRequest extends BasePage implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer ids;
	
	private String planOrderId;

    private String depIds;
    
    private String planNid;
    
	public int limit;
    
    /** IP地址 */
    private String ip;

    // 查询用
    private String borrowNameSearch; // 项目标题
    private String usernameSearch; // 投资人
    private String clientTypeSearch; // 客户端类型
    private String referernameSearch; // 提成人
    private String is51Search; // 0:否，1:是， 9：all
    private String borrowNidSearch; // x项目编号
    private String statusSearch; // 0,未发放；1，已发放；9，all
    private int limitStart = -1;
    private int limitEnd = -1;
    private String startDate; // 创建时间 起始
    private String endDate; // 创建时间 结束
    private String startDateSend; // 发放时间 起始
    private String endDateSend; // 发放时间 结束
    private String recoverDateStart; // 放款时间 起始
    private String recoverDateEnd; // 放款时间 结束
    private String combotreeSrch; // 部门
    private String[] combotreeListSrch; // 部门
    private String accedeOrderIdSearch; // 加入订单号
    // 导出用，直接显示编译后文字
    private String is51Name;
    private String rzqx;
    private String statusName;
    private String exportType; // 0：提成管理， 1：提成明细
    private String lockPeriod; // 计划锁定期
    private String borrowStyleHjh; //还款方式-汇计划
    
    private BigDecimal expectApr;//预期年化收益率
    private String startDatePlan;//计划订单锁定时间 开始
    private String endDatePlan;//计划订单锁定时间 结束
    private String addTime;//计划订单加入时间
    private String countInterestTime;//计息时间(计划订单锁定期)
    private String referrerUserName;//推荐人

	/**
     * 放款时间
     */
    private Integer webStatus;
    /**
     * 放款时间
     */
    private String recoverLastTime;
    /**
     * 融资金额
     */
    private BigDecimal account;
    /**
     * 项目标题
     */
    private String borrowName;
    /**
     * 借款期限
     */
    private int borrowPeriod;
    /**
     * 还款方式
     */
    private String borrowStyle;
    /**
     * 推荐人用户名
     */
    private String referername;
    /**
     * 投资人用户名
     */
    private String username;
    /**
     * 推荐人用户属性 0=>无主单 1=>有主单 2=>线下员工 3=>线上员工
     */
    private String attribute;

    /**
     * 推荐人用户属性
     */
    private String attributeName;
    /**
     * 是否51老用户，1：是 0：否
     */
    private Integer is51;
    
    /**
     * 提成人姓名
     */
    private String usernameTender;
    /**
     * 提成人真实姓名
     */
    private String trueNameTender;
    
    /**
     * 提成人用户属性
     */
    private String attributeTender;
    /**
     * 投资时间
     */
    private String tenderTimeView;
    /**
     * 提成发放时间
     */
    private String sendTimeView;
    /**
     * 订单锁定时间
     */
    private String countInterestTimeView;
    
    /**
     * 月/日 区分
     */
    private String isMonth;
    
    /**
     * 投资类别 1：直投类，2：汇计划
     */
    private Integer tenderType;
    
	public String getBorrowStyle() {
        return borrowStyle;
    }

    public void setBorrowStyle(String borrowStyle) {
        this.borrowStyle = borrowStyle;
    }

    public String getReferername() {
        return referername;
    }

    public void setReferername(String referername) {
        this.referername = referername;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public Integer getIs51() {
        return is51;
    }

    public void setIs51(Integer is51) {
        this.is51 = is51;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public int getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    public int getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(int limitEnd) {
        this.limitEnd = limitEnd;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getClientTypeSearch() {
        return clientTypeSearch;
    }

    public void setClientTypeSearch(String clientTypeSearch) {
        this.clientTypeSearch = clientTypeSearch;
    }

    public String getUsernameSearch() {
        return usernameSearch;
    }

    public void setUsernameSearch(String usernameSearch) {
        this.usernameSearch = usernameSearch;
    }

    public String getReferernameSearch() {
        return referernameSearch;
    }

    public void setReferernameSearch(String referernameSearch) {
        this.referernameSearch = referernameSearch;
    }

    public String getBorrowNidSearch() {
        return borrowNidSearch;
    }

    public void setBorrowNidSearch(String borrowNidSearch) {
        this.borrowNidSearch = borrowNidSearch;
    }

    public int getBorrowPeriod() {
        return borrowPeriod;
    }

    public void setBorrowPeriod(int borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }

    public String getIs51Search() {
        return is51Search;
    }

    public void setIs51Search(String is51Search) {
        this.is51Search = is51Search;
    }

    public String getStatusSearch() {
        return statusSearch;
    }

    public void setStatusSearch(String statusSearch) {
        this.statusSearch = statusSearch;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIs51Name() {
        return is51Name;
    }

    public void setIs51Name(String is51Name) {
        this.is51Name = is51Name;
    }

    public String getRzqx() {
        return rzqx;
    }

    public void setRzqx(String rzqx) {
        this.rzqx = rzqx;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getBorrowName() {
        return borrowName;
    }

    public void setBorrowName(String borrowName) {
        this.borrowName = borrowName;
    }

    public String getBorrowNameSearch() {
        return borrowNameSearch;
    }

    public void setBorrowNameSearch(String borrowNameSearch) {
        this.borrowNameSearch = borrowNameSearch;
    }

    public String getRecoverLastTime() {
        return recoverLastTime;
    }

    public void setRecoverLastTime(String recoverLastTime) {
        this.recoverLastTime = recoverLastTime;
    }

    public BigDecimal getAccount() {
        return account;
    }

    public void setAccount(BigDecimal account) {
        this.account = account;
    }

    public Integer getWebStatus() {
        return webStatus;
    }

    public void setWebStatus(Integer webStatus) {
        this.webStatus = webStatus;
    }

    public String getRecoverDateStart() {
        return recoverDateStart;
    }

    public void setRecoverDateStart(String recoverDateStart) {
        this.recoverDateStart = recoverDateStart;
    }

    public String getRecoverDateEnd() {
        return recoverDateEnd;
    }

    public void setRecoverDateEnd(String recoverDateEnd) {
        this.recoverDateEnd = recoverDateEnd;
    }

    public String getExportType() {
        return exportType;
    }

    public void setExportType(String exportType) {
        this.exportType = exportType;
    }

    public String getCombotreeSrch() {
        return combotreeSrch;
    }

    public void setCombotreeSrch(String combotreeSrch) {
        this.combotreeSrch = combotreeSrch;
    }

    public String[] getCombotreeListSrch() {
        return combotreeListSrch;
    }

    public void setCombotreeListSrch(String[] combotreeListSrch) {
        this.combotreeListSrch = combotreeListSrch;
    }

    public String getStartDateSend() {
        return startDateSend;
    }

    public void setStartDateSend(String startDateSend) {
        this.startDateSend = startDateSend;
    }

    public String getEndDateSend() {
        return endDateSend;
    }

    public void setEndDateSend(String endDateSend) {
        this.endDateSend = endDateSend;
    }

    public String getLockPeriod() {
        return lockPeriod;
    }

    public void setLockPeriod(String lockPeriod) {
        this.lockPeriod = lockPeriod;
    }

    public String getBorrowStyleHjh() {
        return borrowStyleHjh;
    }

    public void setBorrowStyleHjh(String borrowStyleHjh) {
        this.borrowStyleHjh = borrowStyleHjh;
    }

    public String getUsernameTender() {
        return usernameTender;
    }

    public void setUsernameTender(String usernameTender) {
        this.usernameTender = usernameTender;
    }

	public String getAttributeTender() {
		return attributeTender;
	}

	public void setAttributeTender(String attributeTender) {
		this.attributeTender = attributeTender;
	}

	public String getTenderTimeView() {
		return tenderTimeView;
	}

	public void setTenderTimeView(String tenderTimeView) {
		this.tenderTimeView = tenderTimeView;
	}

	public String getSendTimeView() {
		return sendTimeView;
	}

	public void setSendTimeView(String sendTimeView) {
		this.sendTimeView = sendTimeView;
	}

	public String getAccedeOrderIdSearch() {
		return accedeOrderIdSearch;
	}

	public void setAccedeOrderIdSearch(String accedeOrderIdSearch) {
		this.accedeOrderIdSearch = accedeOrderIdSearch;
	}

	public String getStartDatePlan() {
		return startDatePlan;
	}

	public void setStartDatePlan(String startDatePlan) {
		this.startDatePlan = startDatePlan;
	}

	public String getEndDatePlan() {
		return endDatePlan;
	}

	public void setEndDatePlan(String endDatePlan) {
		this.endDatePlan = endDatePlan;
	}


    public String getCountInterestTimeView() {
		return countInterestTimeView;
	}

	public void setCountInterestTimeView(String countInterestTimeView) {
		this.countInterestTimeView = countInterestTimeView;
	}

	public String getCountInterestTime() {
		return countInterestTime;
	}

	public void setCountInterestTime(String countInterestTime) {
		this.countInterestTime = countInterestTime;
	}

	public BigDecimal getExpectApr() {
		return expectApr;
	}

	public void setExpectApr(BigDecimal expectApr) {
		this.expectApr = expectApr;
	}

	public String getReferrerUserName() {
		return referrerUserName;
	}

	public void setReferrerUserName(String referrerUserName) {
		this.referrerUserName = referrerUserName;
	}

	public String getTrueNameTender() {
		return trueNameTender;
	}

	public void setTrueNameTender(String trueNameTender) {
		this.trueNameTender = trueNameTender;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getIsMonth() {
		return isMonth;
	}

	public void setIsMonth(String isMonth) {
		this.isMonth = isMonth;
	}

	public Integer getIds() {
		return ids;
	}

	public void setIds(Integer ids) {
		this.ids = ids;
	}

	public String getPlanOrderId() {
		return planOrderId;
	}

	public void setPlanOrderId(String planOrderId) {
		this.planOrderId = planOrderId;
	}

	public String getDepIds() {
		return depIds;
	}

	public void setDepIds(String depIds) {
		this.depIds = depIds;
	}

	public String getPlanNid() {
		return planNid;
	}

	public void setPlanNid(String planNid) {
		this.planNid = planNid;
	}

	public Integer getTenderType() {
		return tenderType;
	}

	public void setTenderType(Integer tenderType) {
		this.tenderType = tenderType;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
}
