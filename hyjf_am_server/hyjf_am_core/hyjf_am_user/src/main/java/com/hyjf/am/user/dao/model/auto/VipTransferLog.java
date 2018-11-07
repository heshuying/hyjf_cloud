package com.hyjf.am.user.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class VipTransferLog implements Serializable {
    private Integer id;

    /**
     * 用户编号
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * vip编号
     *
     * @mbggenerated
     */
    private Integer vipId;

    /**
     * 订单编号
     *
     * @mbggenerated
     */
    private String ordId;

    /**
     * 转入账户
     *
     * @mbggenerated
     */
    private String transferInCustid;

    /**
     * 转出账户
     *
     * @mbggenerated
     */
    private String transferOutCustid;

    /**
     * 转账金额
     *
     * @mbggenerated
     */
    private Long transAmt;

    /**
     * 转账类别 1:转入，2：转出
     *
     * @mbggenerated
     */
    private Integer transType;

    /**
     * 转账状态，0：成功，1：失败
     *
     * @mbggenerated
     */
    private Integer transStatus;

    /**
     * 转账时间
     *
     * @mbggenerated
     */
    private Integer transTime;

    /**
     * 备注
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * 删除标识 0：未删除，1：已删除
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

    public Integer getVipId() {
        return vipId;
    }

    public void setVipId(Integer vipId) {
        this.vipId = vipId;
    }

    public String getOrdId() {
        return ordId;
    }

    public void setOrdId(String ordId) {
        this.ordId = ordId == null ? null : ordId.trim();
    }

    public String getTransferInCustid() {
        return transferInCustid;
    }

    public void setTransferInCustid(String transferInCustid) {
        this.transferInCustid = transferInCustid == null ? null : transferInCustid.trim();
    }

    public String getTransferOutCustid() {
        return transferOutCustid;
    }

    public void setTransferOutCustid(String transferOutCustid) {
        this.transferOutCustid = transferOutCustid == null ? null : transferOutCustid.trim();
    }

    public Long getTransAmt() {
        return transAmt;
    }

    public void setTransAmt(Long transAmt) {
        this.transAmt = transAmt;
    }

    public Integer getTransType() {
        return transType;
    }

    public void setTransType(Integer transType) {
        this.transType = transType;
    }

    public Integer getTransStatus() {
        return transStatus;
    }

    public void setTransStatus(Integer transStatus) {
        this.transStatus = transStatus;
    }

    public Integer getTransTime() {
        return transTime;
    }

    public void setTransTime(Integer transTime) {
        this.transTime = transTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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