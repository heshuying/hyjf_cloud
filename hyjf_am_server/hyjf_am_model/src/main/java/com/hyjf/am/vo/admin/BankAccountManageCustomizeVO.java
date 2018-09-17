/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author PC-LIUSHOUYI
 * @version BankAccountManageCustomizeVO, v0.1 2018/6/29 11:52
 */
public class BankAccountManageCustomizeVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 8232003216593277079L;

   /* @ApiModelProperty(value = "部门名称")
    private String departmentName;

    @ApiModelProperty(value = "创建时间 起始")
    private String startDate;

    @ApiModelProperty(value = "创建时间 结束")
    private String endDate;

    private Integer limitStart;

    private Integer limitEnd;

    @ApiModelProperty(value = "account_list 主键")
    private Integer id;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "推荐人")
    private String referrerName;
    *//**
     * 推荐组
     *//*
    @ApiModelProperty(value = "推荐组")
    private String referrerGroup;

    @ApiModelProperty(value = "订单号")
    private String nid;

    @ApiModelProperty(value = "操作类型")
    private String type;

    @ApiModelProperty(value = "交易类型")
    private String tradeType;

    @ApiModelProperty(value = "操作金额")
    private BigDecimal amount;

    *//**
     * 可用余额
     *//*
    @ApiModelProperty(value = "可用余额")
    private BigDecimal balance;
    *//**
     * 冻结金额
     *//*
    @ApiModelProperty(value = "冻结金额")
    private BigDecimal frost;

    *//**
     * 汇添金可用余额
     *//*
    @ApiModelProperty(value = "可用余额")
    private BigDecimal planBalance;

    *//**
     * 汇添金冻结金额
     *//*
    @ApiModelProperty(value = "冻结金额")
    private BigDecimal planFrost;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "交易时间")
    private String createTime;

    @ApiModelProperty(value = "账户类型是否银行存管")
    private String isBank;

    @ApiModelProperty(value = "电子账号")
    private String account;

    @ApiModelProperty(value = "交易流水号")
    private String seqNo;

    @ApiModelProperty(value = "资产总额")
    private BigDecimal bankTotal;

    @ApiModelProperty(value = "银行可用金额")
    private BigDecimal bankBalance;

    @ApiModelProperty(value = "银行存管冻结金额")
    private BigDecimal bankFrost;

    @ApiModelProperty(value = "对账状态")
    private String checkStatus;

    @ApiModelProperty(value = "交易状态")
    private String tradeStatus;

    @ApiModelProperty(value = "对账开始时间")
    private String startTime;

    @ApiModelProperty(value = "对账结束时间")
    private String endTime;

    @ApiModelProperty(value = "银行待收")
    private BigDecimal bankAwait;

    @ApiModelProperty(value = "计划代收金额")
    private BigDecimal planAccountWait;

    @ApiModelProperty(value = "江西银行可提现金额")
    private BigDecimal bankBalanceCash;

    @ApiModelProperty(value = "江西银行冻结金额")
    private BigDecimal bankFrostCash;

    @ApiModelProperty(value = "银行待还")
    private BigDecimal bankWaitRepay;

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
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

    public Integer getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(Integer limitStart) {
        this.limitStart = limitStart;
    }

    public Integer getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(Integer limitEnd) {
        this.limitEnd = limitEnd;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getReferrerName() {
        return referrerName;
    }

    public void setReferrerName(String referrerName) {
        this.referrerName = referrerName;
    }

    public String getReferrerGroup() {
        return referrerGroup;
    }

    public void setReferrerGroup(String referrerGroup) {
        this.referrerGroup = referrerGroup;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getFrost() {
        return frost;
    }

    public void setFrost(BigDecimal frost) {
        this.frost = frost;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getIsBank() {
        return isBank;
    }

    public void setIsBank(String isBank) {
        this.isBank = isBank;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public BigDecimal getBankTotal() {
        return bankTotal;
    }

    public void setBankTotal(BigDecimal bankTotal) {
        this.bankTotal = bankTotal;
    }

    public BigDecimal getBankBalance() {
        return bankBalance;
    }

    public void setBankBalance(BigDecimal bankBalance) {
        this.bankBalance = bankBalance;
    }

    public BigDecimal getBankFrost() {
        return bankFrost;
    }

    public void setBankFrost(BigDecimal bankFrost) {
        this.bankFrost = bankFrost;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public BigDecimal getBankAwait() {
        return bankAwait;
    }

    public void setBankAwait(BigDecimal bankAwait) {
        this.bankAwait = bankAwait;
    }

    public BigDecimal getPlanAccountWait() {
        return planAccountWait;
    }

    public void setPlanAccountWait(BigDecimal planAccountWait) {
        this.planAccountWait = planAccountWait;
    }

    public BigDecimal getBankBalanceCash() {
        return bankBalanceCash;
    }

    public void setBankBalanceCash(BigDecimal bankBalanceCash) {
        this.bankBalanceCash = bankBalanceCash;
    }

    public BigDecimal getBankFrostCash() {
        return bankFrostCash;
    }

    public void setBankFrostCash(BigDecimal bankFrostCash) {
        this.bankFrostCash = bankFrostCash;
    }

    public BigDecimal getBankWaitRepay() {
        return bankWaitRepay;
    }

    public void setBankWaitRepay(BigDecimal bankWaitRepay) {
        this.bankWaitRepay = bankWaitRepay;
    }*/
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
   private BigDecimal bankTotal;
   /**
    * 银行可用金额
    */
   private BigDecimal bankBalance;

   /**
    * 银行冻结金额
    */
   private BigDecimal bankFrost;

   /**
    * 银行待收
    */
   private BigDecimal bankAwait;

   /**
    * 银行待还
    */
   private BigDecimal bankWaitRepay;

   /**
    * 银行累计投资
    */
   private BigDecimal bankInvestSum;
   /**
    * 江西银行可提现金额
    */
   private BigDecimal bankBalanceCash;

   /**
    * 江西银行冻结金额
    */
   private BigDecimal bankFrostCash;

   /**
    * 银行待还本金
    */
   private BigDecimal bankWaitCapital;

   /**
    * 银行待还利息
    */
   private BigDecimal bankWaitInterest;

   /**
    * 银行累计收益
    */
   private BigDecimal bankInterestSum;

   /**
    * 电子账号
    */
   private String account;

   /**
    * 姓名
    */
   private String truename;

   /**
    * 用户名
    */
   private String username;
   /**
    * 会员等级
    */
   private String vipName;

   /**
    * 手机号
    */
   private String mobile;

   /**
    * 用户属性（当前）
    */
   private String userAttribute;

   /**
    * 用户角色
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
    * 推荐人所属一级分部（当前
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

   /**
    * 大区
    */
   private String regionName;

   /**
    * 分公司
    */
   private String branchName;
   /**
    * 部门
    */
   private String departmentName;

   /** 部门 */
   private String combotreeSrch;
   /** 部门 */
   private String[] combotreeListSrch;

   /**
    * 电子账号(检索用)
    */
   private String accountSrch;

   /**
    * 会员等级(检索用)
    */
   private String vipSrch;

   /**
    * 计划可用余额
    */
   private BigDecimal planBalance;

   /**
    * 计划冻结金额
    */
   private BigDecimal planFrost;

   /**
    * 计划代收金额
    */
   private BigDecimal planAccountWait;

   private int limitStart = -1;
   private int limitEnd = -1;

   private int initQuery;

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

   public BigDecimal getBankTotal() {
       return bankTotal;
   }

   public void setBankTotal(BigDecimal bankTotal) {
       this.bankTotal = bankTotal;
   }

   public BigDecimal getBankBalance() {
       return bankBalance;
   }

   public void setBankBalance(BigDecimal bankBalance) {
       this.bankBalance = bankBalance;
   }

   public BigDecimal getBankFrost() {
       return bankFrost;
   }

   public void setBankFrost(BigDecimal bankFrost) {
       this.bankFrost = bankFrost;
   }

   public BigDecimal getBankAwait() {
       return bankAwait;
   }

   public void setBankAwait(BigDecimal bankAwait) {
       this.bankAwait = bankAwait;
   }

   public BigDecimal getBankWaitRepay() {
       return bankWaitRepay;
   }

   public void setBankWaitRepay(BigDecimal bankWaitRepay) {
       this.bankWaitRepay = bankWaitRepay;
   }

   public BigDecimal getBankInvestSum() {
       return bankInvestSum;
   }

   public void setBankInvestSum(BigDecimal bankInvestSum) {
       this.bankInvestSum = bankInvestSum;
   }

   public BigDecimal getBankBalanceCash() {
       return bankBalanceCash;
   }

   public void setBankBalanceCash(BigDecimal bankBalanceCash) {
       this.bankBalanceCash = bankBalanceCash;
   }

   public BigDecimal getBankFrostCash() {
       return bankFrostCash;
   }

   public void setBankFrostCash(BigDecimal bankFrostCash) {
       this.bankFrostCash = bankFrostCash;
   }

   public BigDecimal getBankWaitCapital() {
       return bankWaitCapital;
   }

   public void setBankWaitCapital(BigDecimal bankWaitCapital) {
       this.bankWaitCapital = bankWaitCapital;
   }

   public BigDecimal getBankWaitInterest() {
       return bankWaitInterest;
   }

   public void setBankWaitInterest(BigDecimal bankWaitInterest) {
       this.bankWaitInterest = bankWaitInterest;
   }

   public BigDecimal getBankInterestSum() {
       return bankInterestSum;
   }

   public void setBankInterestSum(BigDecimal bankInterestSum) {
       this.bankInterestSum = bankInterestSum;
   }

   public String getAccount() {
       return account;
   }

   public void setAccount(String account) {
       this.account = account;
   }

   public String getTruename() {
       return truename;
   }

   public void setTruename(String truename) {
       this.truename = truename;
   }

   public String getUsername() {
       return username;
   }

   public void setUsername(String username) {
       this.username = username;
   }

   public String getVipName() {
       return vipName;
   }

   public void setVipName(String vipName) {
       this.vipName = vipName;
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

   public String getUserDepartmentName() {
       return userDepartmentName;
   }

   public void setUserDepartmentName(String userDepartmentName) {
       this.userDepartmentName = userDepartmentName;
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

   public String getReferrerDepartmentName() {
       return referrerDepartmentName;
   }

   public void setReferrerDepartmentName(String referrerDepartmentName) {
       this.referrerDepartmentName = referrerDepartmentName;
   }

   public String getRegionName() {
       return regionName;
   }

   public void setRegionName(String regionName) {
       this.regionName = regionName;
   }

   public String getBranchName() {
       return branchName;
   }

   public void setBranchName(String branchName) {
       this.branchName = branchName;
   }

   public String getDepartmentName() {
       return departmentName;
   }

   public void setDepartmentName(String departmentName) {
       this.departmentName = departmentName;
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

   public String getAccountSrch() {
       return accountSrch;
   }

   public void setAccountSrch(String accountSrch) {
       this.accountSrch = accountSrch;
   }

   public String getVipSrch() {
       return vipSrch;
   }

   public void setVipSrch(String vipSrch) {
       this.vipSrch = vipSrch;
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

   public BigDecimal getPlanAccountWait() {
       return planAccountWait;
   }

   public void setPlanAccountWait(BigDecimal planAccountWait) {
       this.planAccountWait = planAccountWait;
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

   public int getInitQuery() {
       return initQuery;
   }

   public void setInitQuery(int initQuery) {
       this.initQuery = initQuery;
   }
   @ApiModelProperty(value = "对账开始时间")
   private String startTime;

   @ApiModelProperty(value = "对账结束时间")
   private String endTime;

public String getStartTime() {
	return startTime;
}

public void setStartTime(String startTime) {
	this.startTime = startTime;
}

public String getEndTime() {
	return endTime;
}

public void setEndTime(String endTime) {
	this.endTime = endTime;
}
   
}
