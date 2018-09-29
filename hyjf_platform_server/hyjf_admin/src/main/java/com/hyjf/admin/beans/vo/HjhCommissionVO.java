/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.vo;

import com.hyjf.admin.beans.BaseRequest;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Albert
 * @version HjhCommissionVO.java, v0.1 2018年8月7日 下午2:56:36
 * 这里结果VO需要继承请求基类(特殊情况)，此结果实体需要被请求实体继承
 */
public class HjhCommissionVO extends BaseRequest implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "IP地址")
    private String ip;
	
	@ApiModelProperty(value = "项目标题查询")
    private String borrowNameSearch;
	
	@ApiModelProperty(value = "投资人查询")
    private String usernameSearch;
	
	@ApiModelProperty(value = "客户端类型查询")
    private String clientTypeSearch;
	
	@ApiModelProperty(value = "提成人查询")
    private String referernameSearch;
	
	@ApiModelProperty(value = "0:否，1:是， 9：all")
    private String is51Search;
	
	@ApiModelProperty(value = "项目编号查询")
    private String borrowNidSearch;
	
	@ApiModelProperty(value = "0,未发放；1，已发放；9，all")
    private String statusSearch;
	
	@ApiModelProperty(value = "创建时间 起始")
    private String startDate; 
	
	@ApiModelProperty(value = "创建时间 结束")
    private String endDate;
	
	@ApiModelProperty(value = "发放时间 起始")
    private String startDateSend;
	
	@ApiModelProperty(value = "发放时间 结束")
    private String endDateSend; 
	
	@ApiModelProperty(value = "放款时间 起始")
    private String recoverDateStart;
	
	@ApiModelProperty(value = "放款时间 结束")
    private String recoverDateEnd;
	
	@ApiModelProperty(value = "部门")
    private String combotreeSrch;
	
	@ApiModelProperty(value = "部门")
    private String[] combotreeListSrch;
	
	@ApiModelProperty(value = "加入订单号")
    private String accedeOrderIdSearch;
	
    // 导出用，直接显示编译后文字
	@ApiModelProperty(value = "is51Name")
    private String is51Name;
	
	@ApiModelProperty(value = "rzqx")
    private String rzqx;
	
	@ApiModelProperty(value = "statusName")
    private String statusName;
	
	@ApiModelProperty(value = "0：提成管理， 1：提成明细")
    private String exportType; 
	
	@ApiModelProperty(value = "计划锁定期")
    private String lockPeriod;
	
	@ApiModelProperty(value = "还款方式-汇计划")
    private String borrowStyleHjh;
	
	@ApiModelProperty(value = "预期年化收益率")
    private BigDecimal expectApr;
	
	@ApiModelProperty(value = "计划订单锁定时间 开始")
    private String startDatePlan;
	
	@ApiModelProperty(value = "计划订单锁定时间 结束")
    private String endDatePlan;
	
	@ApiModelProperty(value = "计划订单加入时间")
    private String addTime;
	
	@ApiModelProperty(value = "计息时间(计划订单锁定期)")
    private String countInterestTime;
	
	@ApiModelProperty(value = "推荐人")
    private String referrerUserName;

	@ApiModelProperty(value = "webStatus")
    private Integer webStatus;

	@ApiModelProperty(value = "放款时间")
    private String recoverLastTime;

	@ApiModelProperty(value = "融资金额")
    private BigDecimal account;

	@ApiModelProperty(value = "项目标题")
    private String borrowName;

	@ApiModelProperty(value = "借款期限")
    private int borrowPeriod;

	@ApiModelProperty(value = "还款方式")
    private String borrowStyle;

	@ApiModelProperty(value = "推荐人用户名")
    private String referername;

	@ApiModelProperty(value = "投资人用户名")
    private String username;

	@ApiModelProperty(value = "推荐人用户属性 0=>无主单 1=>有主单 2=>线下员工 3=>线上员工")
    private String attribute;

	@ApiModelProperty(value = "推荐人用户属性")
    private String attributeName;

	@ApiModelProperty(value = "是否51老用户，1：是 0：否")
    private Integer is51;
    
	@ApiModelProperty(value = "提成人姓名")
    private String usernameTender;

	@ApiModelProperty(value = "提成人真实姓名")
    private String trueNameTender;
    
	@ApiModelProperty(value = "提成人用户属性")
    private String attributeTender;

	@ApiModelProperty(value = "投资时间")
    private String tenderTimeView;

	@ApiModelProperty(value = "提成发放时间")
    private String sendTimeView;

	@ApiModelProperty(value = "订单锁定时间")
    private String countInterestTimeView;
    
	@ApiModelProperty(value = "月/日 区分")
    private String isMonth;
	
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

}
