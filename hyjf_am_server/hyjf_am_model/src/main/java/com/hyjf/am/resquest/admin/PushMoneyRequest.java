/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.admin.TenderCommissionVO;
import com.hyjf.am.vo.bank.BankCallBeanVO;
import com.hyjf.am.vo.trade.PushMoneyVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.common.paginator.Paginator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author fuqiang
 * @version PushMoneyRequest, v0.1 2018/7/10 19:26
 */
@ApiModel(value = "直投提成请求参数")
public class PushMoneyRequest extends PushMoneyVO implements Serializable {

	@ApiModelProperty(value = "项目编号(检索用)")
	public String borrowNid;

	@ApiModelProperty(value = "项目标题(检索用)")
	public String borrowName;

	@ApiModelProperty(value = "项目还款方式 = endday 天   !=endday 个月(检索用)")
	public String borrowStyle;

	@ApiModelProperty(value = "融资期限(检索用)")
	public String borrowPeriod;

	@ApiModelProperty(value = "融资金额(检索用)")
	public String account;

	@ApiModelProperty(value = "提成总额(检索用)")
	public String commission;

	@ApiModelProperty(value = "放款开始时间(检索用)")
	public String recoverLastTimeStart;

	@ApiModelProperty(value = "放款结束时间(检索用)")
	public String recoverLastTimeEnd;

	@ApiModelProperty(value = "项目类型(检索用)")
	public Integer projectType;

	@ApiModelProperty(value = "投资人(检索用)")
	private String usernameSearch;

	@ApiModelProperty(value = "提成人(检索用)")
	private String referernameSearch;

	@ApiModelProperty(value = "部门(检索用)")
	private String combotreeSrch;

	@ApiModelProperty(value = "部门(检索用)")
	private String[] combotreeListSrch;

	@ApiModelProperty(value = "电子账号(检索用)")
	private String accountId;

	@ApiModelProperty(value = "状态0,未发放；1，已发放；9，all(检索用)")
	private String statusSearch;

	@ApiModelProperty(value = "创建时间 起始")
	private String startDate;

	@ApiModelProperty(value = "创建时间 结束")
	private String endDate;

	@ApiModelProperty(value = "发放时间 起始")
	private String startDateSend;

	@ApiModelProperty(value = "发放时间 结束")
	private String endDateSend;
	@ApiModelProperty(value = "计划订单锁定时间 开始")
	private String startDatePlan;
	@ApiModelProperty(value = "计划订单锁定时间 结束")
	private String endDatePlan;
	@ApiModelProperty(value = "放款时间 起始")
	private String recoverDateStart;
	@ApiModelProperty(value = "放款时间 结束")
	private String recoverDateEnd;

	@ApiModelProperty(value = "提成id(发提成用)")
	private Integer id;
	@ApiModelProperty(value = "登录用户id(后台用)")
	private Integer loginUserId;
	@ApiModelProperty(value = "登录用户名(后台用)")
	private String loginUserName;
	private TenderCommissionVO tenderCommissionVO;
	private BankCallBeanVO bankCallBeanVO;
	private BankOpenAccountVO bankOpenAccountVO;

	public int limit;

	public int limitStart;

	public int limitEnd;

	/**
	 * 当前页码
	 */
	@ApiModelProperty(value = "当前页")
	private int currPage;

	/**
	 * 当前页条数
	 */
	@ApiModelProperty(value = "当前页条数")
	private int pageSize = 10;

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
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

	private static final long serialVersionUID = 1L;

	public String getBorrowNid() {
		return borrowNid;
	}

	public void setBorrowNid(String borrowNid) {
		this.borrowNid = borrowNid;
	}

	public String getBorrowName() {
		return borrowName;
	}

	public void setBorrowName(String borrowName) {
		this.borrowName = borrowName;
	}

	public String getBorrowStyle() {
		return borrowStyle;
	}

	public void setBorrowStyle(String borrowStyle) {
		this.borrowStyle = borrowStyle;
	}

	public String getBorrowPeriod() {
		return borrowPeriod;
	}

	public void setBorrowPeriod(String borrowPeriod) {
		this.borrowPeriod = borrowPeriod;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getCommission() {
		return commission;
	}

	public void setCommission(String commission) {
		this.commission = commission;
	}

	public String getRecoverLastTimeStart() {
		return recoverLastTimeStart;
	}

	public void setRecoverLastTimeStart(String recoverLastTimeStart) {
		this.recoverLastTimeStart = recoverLastTimeStart;
	}

	public String getRecoverLastTimeEnd() {
		return recoverLastTimeEnd;
	}

	public void setRecoverLastTimeEnd(String recoverLastTimeEnd) {
		this.recoverLastTimeEnd = recoverLastTimeEnd;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Paginator getPaginator() {
		return paginator;
	}

	public void setPaginator(Paginator paginator) {
		this.paginator = paginator;
	}

	public Integer getProjectType() {
		return projectType;
	}

	public void setProjectType(Integer projectType) {
		this.projectType = projectType;
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

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getStatusSearch() {
		return statusSearch;
	}

	public void setStatusSearch(String statusSearch) {
		this.statusSearch = statusSearch;
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

	public Integer getLoginUserId() {
		return loginUserId;
	}

	public void setLoginUserId(Integer loginUserId) {
		this.loginUserId = loginUserId;
	}

	public String getLoginUserName() {
		return loginUserName;
	}

	public void setLoginUserName(String loginUserName) {
		this.loginUserName = loginUserName;
	}

	public TenderCommissionVO getTenderCommissionVO() {
		return tenderCommissionVO;
	}

	public void setTenderCommissionVO(TenderCommissionVO tenderCommissionVO) {
		this.tenderCommissionVO = tenderCommissionVO;
	}

	public BankCallBeanVO getBankCallBeanVO() {
		return bankCallBeanVO;
	}

	public void setBankCallBeanVO(BankCallBeanVO bankCallBeanVO) {
		this.bankCallBeanVO = bankCallBeanVO;
	}

    public BankOpenAccountVO getBankOpenAccountVO() {
        return bankOpenAccountVO;
    }

    public void setBankOpenAccountVO(BankOpenAccountVO bankOpenAccountVO) {
        this.bankOpenAccountVO = bankOpenAccountVO;
    }

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 翻页机能用的隐藏变量
	 */
	private int paginatorPage = 1;

	/**
	 * 列表画面自定义标签上的用翻页对象：paginator
	 */
	private Paginator paginator;

	public int getPaginatorPage() {
		if (paginatorPage == 0) {
			paginatorPage = 1;
		}
		return paginatorPage;
	}

	public void setPaginatorPage(int paginatorPage) {
		this.paginatorPage = paginatorPage;
	}

}
