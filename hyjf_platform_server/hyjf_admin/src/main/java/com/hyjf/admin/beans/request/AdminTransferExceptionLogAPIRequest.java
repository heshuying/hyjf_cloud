package com.hyjf.admin.beans.request;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.hyjf.admin.beans.BaseRequest;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author jijun
 * @version AdminTransferExceptionLogRequest, v0.1 2018/07/10
 */
public class AdminTransferExceptionLogAPIRequest extends BaseRequest implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "转账订单号")
	private String orderId;
	
	@ApiModelProperty(value = "用户名")
	private String username;
	
	@ApiModelProperty(value = "交易类型")
	private String tradeType;
	
	@ApiModelProperty(value = "检索条件 有效时间开始")
	private String timeStartSrch;
	
	@ApiModelProperty(value = "检索条件 有效时间结束")
	private String timeEndSrch;

	@ApiModelProperty(value = "状态")
	private Integer status;

	/**
	 * uuid
	 */
	private String uuid;

	@ApiModelProperty(value = "请求内容")
	private String content;

	@ApiModelProperty(value = "返回结果")
	private String result;

	@ApiModelProperty(value = "投资编号")
	private Integer recoverId;

	@ApiModelProperty(value = "0:体验金收益回款，1：加息券收益回款，2：其他")
	private Integer type;

	@ApiModelProperty(value = "汇付返回码")
	private String respcode;

	@ApiModelProperty(value = "交易金额")
	private BigDecimal transAmt;

	@ApiModelProperty(value = "用户客户号")
	private String accountId;

	@ApiModelProperty(value = "交易流水号")
	private String seqNo;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Integer getRecoverId() {
		return recoverId;
	}

	public void setRecoverId(Integer recoverId) {
		this.recoverId = recoverId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getRespcode() {
		return respcode;
	}

	public void setRespcode(String respcode) {
		this.respcode = respcode;
	}

	public BigDecimal getTransAmt() {
		return transAmt;
	}

	public void setTransAmt(BigDecimal transAmt) {
		this.transAmt = transAmt;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public String getCusId() {
		return cusId;
	}

	public void setCusId(String cusId) {
		this.cusId = cusId;
	}

	public String getAcctId() {
		return acctId;
	}

	public void setAcctId(String acctId) {
		this.acctId = acctId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	public String getAddUser() {
		return addUser;
	}

	public void setAddUser(String addUser) {
		this.addUser = addUser;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@ApiModelProperty(value = "入账商户号")
	private String cusId;

	@ApiModelProperty(value = "入账子账户号")
	private String acctId;

	@ApiModelProperty(value = "用户编号")
	private Integer userId;

	@ApiModelProperty(value = "备注")
	private String remark;

	@ApiModelProperty(value = "删除标识 0：未删除，1：已删除")
	private Integer delFlag;

	@ApiModelProperty(value = "创建人")
	private String addUser;

	@ApiModelProperty(value = "修改人")
	private String updateUser;

	@ApiModelProperty(value = "创建时间")
	private Date addTime;

	@ApiModelProperty(value = "修改时间")
	private Date updateTime;

	/**
	 * 分页功能
	 */
	public int limit;

	private int paginatorPage = 1;
	public int getPaginatorPage() {
		if (paginatorPage == 0) {
			paginatorPage = 1;
		}
		return paginatorPage;
	}


	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getTimeStartSrch() {
		return timeStartSrch;
	}

	public void setTimeStartSrch(String timeStartSrch) {
		this.timeStartSrch = timeStartSrch;
	}

	public String getTimeEndSrch() {
		return timeEndSrch;
	}

	public void setTimeEndSrch(String timeEndSrch) {
		this.timeEndSrch = timeEndSrch;
	}

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
