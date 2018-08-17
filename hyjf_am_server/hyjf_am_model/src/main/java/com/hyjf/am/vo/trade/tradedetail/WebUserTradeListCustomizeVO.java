

package com.hyjf.am.vo.trade.tradedetail;

/**
 * @Description
 * @Author pangchengchao
 * @Version v0.1
 * @Date
 */

public class WebUserTradeListCustomizeVO {

	// 主键
	private String id;
	// 交易日期
	private String time;
	// 交易类型
	private String trade;
	// 交易类型 +-
	private String type;
	// 交易金额(交易类型名称)
	private String typeName;
	// 交易金额
	private String money;
	// 可用余额
	private String balance;
	// 备注
	private String remark;
	//收支类型：1收入2支出3冻结
	private String revuAndExpType;
	//状态 :交易状态0: 失败 1：成功 2:冲正
	private String tradeStatus;
	//操作用户  是否是银行的交易记录(0:否,1:是)
	private String isBank;
	
	/** * new added */
	// 用户角色:1投资人2借款人3担保机构
	private String roleId;
	// 标的号
	private String borrowNid;
	
	/**
	 * 构造方法
	 */
	public WebUserTradeListCustomizeVO() {
		super();
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTrade() {
		return trade;
	}

	public void setTrade(String trade) {
		this.trade = trade;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRevuAndExpType() {
		return revuAndExpType;
	}

	public void setRevuAndExpType(String revuAndExpType) {
		this.revuAndExpType = revuAndExpType;
	}

	public String getIsBank() {
		return isBank;
	}

	public void setIsBank(String isBank) {
		this.isBank = isBank;
	}

	public String getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getBorrowNid() {
		return borrowNid;
	}

	public void setBorrowNid(String borrowNid) {
		this.borrowNid = borrowNid;
	}
	
	
   
}
