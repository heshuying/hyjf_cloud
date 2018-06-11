package com.hyjf.callcenter.beans.customizebean;

import java.math.BigDecimal;

/**
 * 
 * @author HBZ
 */
public class CallCenterAccountManageCustomize {

	/**
     * 
     */
	private int id;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 资金总额
	 */
	private BigDecimal total;
	/**
	 * 收入
	 */
	private BigDecimal income;
	/**
	 * 支出
	 */
	private BigDecimal expend;
	/**
	 * 可用金额
	 */
	private BigDecimal balance;
	/**
	 * 可提现
	 */
	private BigDecimal balanceCash;
	/**
	 * 不可提现
	 */
	private BigDecimal balanceFrost;
	/**
	 * 冻结金额
	 */
	private BigDecimal frost;
	/**
	 * 待收金额
	 */
	private BigDecimal await;
	/**
	 * 待还金额
	 */
	private BigDecimal repay;
	/**
	 * 提现冻结金额
	 */
	private BigDecimal frostCash;
	/**
     * 
     */
	private int isUpdate;
	/**
     * 
     */
	private int isok;
	/**
	 * 待返金额汇总
	 */
	private BigDecimal recMoney;
	/**
	 * 待返手续费汇总
	 */
	private BigDecimal fee;
	/**
	 * 新投资金额汇总
	 */
	private BigDecimal inMoney;
	/**
     * 
     */
	private int inMoneyFlag;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 真实姓名
	 */
	private String truename;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 用户属性（当前）
	 */
	private String userAttribute;
	/**
	 * 角色
	 */
	private String roleid;
	/**
	 * 用户所属一级分部（当前）
	 */
	private String userRegionName;
	/**
	 * 用户所属二级分部（当前）
	 */
	private String userBranchName;
	/**
	 * 用户所属三级分部（当前）
	 */
	private String userDepartmentName;
	/**
	 * 推荐人用户名（当前）
	 */
	private String referrerName;
	/**
	 * 推荐人姓名（当前）
	 */
	private String referrerTrueName;
	/**
	 * 推荐人所属一级分部（当前）
	 */
	private String referrerRegionName;
	/**
	 * 推荐人所属二级分部（当前）
	 */
	private String referrerBranchName;
	/**
	 * 推荐人所属三级分部（当前）
	 */
	private String referrerDepartmentName;

	// 用户类型 0：全部账户 ； 1：异常账户
	private int userTypeSearche;
	private String startDate; // 创建时间 起始
	private String endDate; // 创建时间 结束

	private int limitStart = -1;
	private int limitEnd = -1;

	/** 大区 */
	private String regionName;
	/** 分公司 */
	private String branchName;
	/** 部门 */
	private String departmentName;
	// 查询用变量
	/** 部门 */
	private String combotreeSrch;
	/** 部门 */
	private String[] combotreeListSrch;

	/**
	 * 计划可用余额
	 */
	private BigDecimal planBalance;

	/**
	 * 计划冻结金额
	 */
	private BigDecimal planFrost;

	/**
	 * 总的冻结金额
	 */
	private BigDecimal frostTotal;

	/**
	 * 总的可用金额
	 */
	private BigDecimal balanceTotal;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public int getUserTypeSearche() {
		return userTypeSearche;
	}

	public void setUserTypeSearche(int userTypeSearche) {
		this.userTypeSearche = userTypeSearche;
	}

	public BigDecimal getIncome() {
		return income;
	}

	public void setIncome(BigDecimal income) {
		this.income = income;
	}

	public BigDecimal getExpend() {
		return expend;
	}

	public void setExpend(BigDecimal expend) {
		this.expend = expend;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getBalanceCash() {
		return balanceCash;
	}

	public void setBalanceCash(BigDecimal balanceCash) {
		this.balanceCash = balanceCash;
	}

	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	public BigDecimal getBalanceFrost() {
		return balanceFrost;
	}

	public void setBalanceFrost(BigDecimal balanceFrost) {
		this.balanceFrost = balanceFrost;
	}

	public BigDecimal getFrost() {
		return frost;
	}

	public void setFrost(BigDecimal frost) {
		this.frost = frost;
	}

	public BigDecimal getAwait() {
		return await;
	}

	public void setAwait(BigDecimal await) {
		this.await = await;
	}

	public BigDecimal getRepay() {
		return repay;
	}

	public void setRepay(BigDecimal repay) {
		this.repay = repay;
	}

	public BigDecimal getFrostCash() {
		return frostCash;
	}

	public void setFrostCash(BigDecimal frostCash) {
		this.frostCash = frostCash;
	}

	public int getIsUpdate() {
		return isUpdate;
	}

	public void setIsUpdate(int isUpdate) {
		this.isUpdate = isUpdate;
	}

	public int getIsok() {
		return isok;
	}

	public void setIsok(int isok) {
		this.isok = isok;
	}

	public BigDecimal getRecMoney() {
		return recMoney;
	}

	public void setRecMoney(BigDecimal recMoney) {
		this.recMoney = recMoney;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public BigDecimal getInMoney() {
		return inMoney;
	}

	public void setInMoney(BigDecimal inMoney) {
		this.inMoney = inMoney;
	}

	public int getInMoneyFlag() {
		return inMoneyFlag;
	}

	public void setInMoneyFlag(int inMoneyFlag) {
		this.inMoneyFlag = inMoneyFlag;
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

	/**
	 * regionName
	 * 
	 * @return the regionName
	 */

	public String getRegionName() {
		return regionName;
	}

	/**
	 * @param regionName
	 *            the regionName to set
	 */

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	/**
	 * branchName
	 * 
	 * @return the branchName
	 */

	public String getBranchName() {
		return branchName;
	}

	/**
	 * @param branchName
	 *            the branchName to set
	 */

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	/**
	 * departmentName
	 * 
	 * @return the departmentName
	 */

	public String getDepartmentName() {
		return departmentName;
	}

	/**
	 * @param departmentName
	 *            the departmentName to set
	 */

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	/**
	 * combotreeSrch
	 * 
	 * @return the combotreeSrch
	 */

	public String getCombotreeSrch() {
		return combotreeSrch;
	}

	/**
	 * @param combotreeSrch
	 *            the combotreeSrch to set
	 */

	public void setCombotreeSrch(String combotreeSrch) {
		this.combotreeSrch = combotreeSrch;
	}

	/**
	 * combotreeListSrch
	 * 
	 * @return the combotreeListSrch
	 */

	public String[] getCombotreeListSrch() {
		return combotreeListSrch;
	}

	/**
	 * @param combotreeListSrch
	 *            the combotreeListSrch to set
	 */

	public void setCombotreeListSrch(String[] combotreeListSrch) {
		this.combotreeListSrch = combotreeListSrch;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUserAttribute() {
		return userAttribute;
	}

	public void setUserAttribute(String userAttribute) {
		this.userAttribute = userAttribute;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String getUserRegionName() {
		return userRegionName;
	}

	public void setUserRegionName(String userRegionName) {
		this.userRegionName = userRegionName;
	}

	public String getUserBranchName() {
		return userBranchName;
	}

	public void setUserBranchName(String userBranchName) {
		this.userBranchName = userBranchName;
	}

	public String getReferrerName() {
		return referrerName;
	}

	public void setReferrerName(String referrerName) {
		this.referrerName = referrerName;
	}

	public String getReferrerTrueName() {
		return referrerTrueName;
	}

	public void setReferrerTrueName(String referrerTrueName) {
		this.referrerTrueName = referrerTrueName;
	}

	public String getReferrerRegionName() {
		return referrerRegionName;
	}

	public void setReferrerRegionName(String referrerRegionName) {
		this.referrerRegionName = referrerRegionName;
	}

	public String getReferrerBranchName() {
		return referrerBranchName;
	}

	public void setReferrerBranchName(String referrerBranchName) {
		this.referrerBranchName = referrerBranchName;
	}

	public String getUserDepartmentName() {
		return userDepartmentName;
	}

	public void setUserDepartmentName(String userDepartmentName) {
		this.userDepartmentName = userDepartmentName;
	}

	public String getReferrerDepartmentName() {
		return referrerDepartmentName;
	}

	public void setReferrerDepartmentName(String referrerDepartmentName) {
		this.referrerDepartmentName = referrerDepartmentName;
	}

	public BigDecimal getPlanBalance() {
		return planBalance;
	}

	public void setPlanBalance(BigDecimal planBalance) {
		this.planBalance = planBalance;
	}

	public BigDecimal getPlanFrost() {
		return planFrost;
	}

	public void setPlanFrost(BigDecimal planFrost) {
		this.planFrost = planFrost;
	}

	public BigDecimal getFrostTotal() {
		return frostTotal;
	}

	public void setFrostTotal(BigDecimal frostTotal) {
		this.frostTotal = frostTotal;
	}

	public BigDecimal getBalanceTotal() {
		return balanceTotal;
	}

	public void setBalanceTotal(BigDecimal balanceTotal) {
		this.balanceTotal = balanceTotal;
	}

}
