/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.config;

import com.hyjf.am.vo.BasePage;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author fuqiang
 * @version STZHWhiteListRequestBean, v0.1 2018/7/10 9:59
 */
public class STZHWhiteListRequestBean extends BasePage implements Serializable {
	private Integer id;

	@ApiModelProperty(value = "机构/个人ID")
	private Integer userId;

	@ApiModelProperty(value = "机构/个人用户名")
	private String userName;

	@ApiModelProperty(value = "电子账号")
	private String accountId;

	@ApiModelProperty(value = "手机号")
	private String mobile;

	@ApiModelProperty(value = "姓名")
	private String customerName;

	@ApiModelProperty(value = "受托支付收款人ID")
	private Integer stUserId;

	@ApiModelProperty(value = "受托支付收款人用户名")
	private String stUserName;

	@ApiModelProperty(value = "收款人电子账号")
	private String stAccountId;

	@ApiModelProperty(value = "收款人手机号")
	private String stMobile;

	@ApiModelProperty(value = "收款人名称")
	private String stCustomerName;

	@ApiModelProperty(value = "状态")
	private Integer state;

	private String createtime;

	private String createuser;

	private String updatetime;

	private String updateuser;

	private Integer delFlag;

	@ApiModelProperty(value = "审批人")
	private String approvalName;

	@ApiModelProperty(value = "审批时间")
	private String approvalTime;

	@ApiModelProperty(value = "备注")
	private String remark;

	@ApiModelProperty(value = "机构编号")
	private String instcode;

	@ApiModelProperty(value = "机构名称")
	private String instName;

	private String stateName;

	private List<HjhInstConfigVO> regionList;

	public List<HjhInstConfigVO> getRegionList() {
		return regionList;
	}

	public void setRegionList(List<HjhInstConfigVO> regionList) {
		this.regionList = regionList;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	private static final long serialVersionUID = 1L;

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName == null ? null : userName.trim();
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId == null ? null : accountId.trim();
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile == null ? null : mobile.trim();
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName == null ? null : customerName.trim();
	}

	public Integer getStUserId() {
		return stUserId;
	}

	public void setStUserId(Integer stUserId) {
		this.stUserId = stUserId;
	}

	public String getStUserName() {
		return stUserName;
	}

	public void setStUserName(String stUserName) {
		this.stUserName = stUserName == null ? null : stUserName.trim();
	}

	public String getStAccountId() {
		return stAccountId;
	}

	public void setStAccountId(String stAccountId) {
		this.stAccountId = stAccountId == null ? null : stAccountId.trim();
	}

	public String getStMobile() {
		return stMobile;
	}

	public void setStMobile(String stMobile) {
		this.stMobile = stMobile == null ? null : stMobile.trim();
	}

	public String getStCustomerName() {
		return stCustomerName;
	}

	public void setStCustomerName(String stCustomerName) {
		this.stCustomerName = stCustomerName == null ? null : stCustomerName.trim();
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime == null ? null : createtime.trim();
	}

	public String getCreateuser() {
		return createuser;
	}

	public void setCreateuser(String createuser) {
		this.createuser = createuser == null ? null : createuser.trim();
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime == null ? null : updatetime.trim();
	}

	public String getUpdateuser() {
		return updateuser;
	}

	public void setUpdateuser(String updateuser) {
		this.updateuser = updateuser == null ? null : updateuser.trim();
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlg) {
		this.delFlag = delFlg;
	}

	public String getApprovalName() {
		return approvalName;
	}

	public void setApprovalName(String approvalName) {
		this.approvalName = approvalName == null ? null : approvalName.trim();
	}

	public String getApprovalTime() {
		return approvalTime;
	}

	public void setApprovalTime(String approvalTime) {
		this.approvalTime = approvalTime == null ? null : approvalTime.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getInstcode() {
		return instcode;
	}

	public void setInstcode(String instcode) {
		this.instcode = instcode == null ? null : instcode.trim();
	}

	public String getInstName() {
		return instName;
	}

	public void setInstName(String instName) {
		this.instName = instName == null ? null : instName.trim();
	}
}
