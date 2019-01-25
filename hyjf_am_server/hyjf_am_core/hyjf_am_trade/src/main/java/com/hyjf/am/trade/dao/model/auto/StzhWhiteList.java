package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class StzhWhiteList implements Serializable {
    private Integer id;

    /**
     * 机构/个人userid
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 机构/个人 用户名
     *
     * @mbggenerated
     */
    private String userName;

    /**
     * 电子账号
     *
     * @mbggenerated
     */
    private String accountId;

    /**
     * 手机号
     *
     * @mbggenerated
     */
    private String mobile;

    /**
     * 姓名
     *
     * @mbggenerated
     */
    private String customerName;

    /**
     * 受托支付收款人userid
     *
     * @mbggenerated
     */
    private Integer stUserId;

    /**
     * 受托支付收款人用户名
     *
     * @mbggenerated
     */
    private String stUserName;

    /**
     * 收款人电子账号
     *
     * @mbggenerated
     */
    private String stAccountId;

    /**
     * 收款人  手机号
     *
     * @mbggenerated
     */
    private String stMobile;

    /**
     * 收款人名称/姓名
     *
     * @mbggenerated
     */
    private String stCustomerName;

    /**
     * 审批人
     *
     * @mbggenerated
     */
    private String approvalName;

    /**
     * 审批时间
     *
     * @mbggenerated
     */
    private String approvalTime;

    /**
     * 备注
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * 机构编号
     *
     * @mbggenerated
     */
    private String instCode;

    /**
     * 机构名称
     *
     * @mbggenerated
     */
    private String instName;

    /**
     * 状态 1启用  0禁用
     *
     * @mbggenerated
     */
    private Integer state;

    /**
     * 删除标识
     *
     * @mbggenerated
     */
    private Integer delFlag;

    /**
     * 创建人
     *
     * @mbggenerated
     */
    private Integer createUserId;

    /**
     * 修改人
     *
     * @mbggenerated
     */
    private Integer updateUserId;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 更新时间
     *
     * @mbggenerated
     */
    private Date updateTime;

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

    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode == null ? null : instCode.trim();
    }

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName == null ? null : instName.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}