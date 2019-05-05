package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class ApplyBorrowAgreement implements Serializable {
    /**
     * id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 借款编号
     *
     * @mbggenerated
     */
    private String borrowNid;

    /**
     * 申请人id
     *
     * @mbggenerated
     */
    private Integer applyUserId;

    /**
     * 申请人
     *
     * @mbggenerated
     */
    private String applyUserName;

    /**
     * 订单份数(应下载协议数量=出借订单数+承接订单数)
     *
     * @mbggenerated
     */
    private Integer orderNumber;

    /**
     * 协议份数(出借订单数+承接订单数,下载成功状态的协议份数)
     *
     * @mbggenerated
     */
    private Integer agreementNumber;

    /**
     * 申请状态 0 初始；1成功
     *
     * @mbggenerated
     */
    private Integer status;

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
     * 最后修改时间
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

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid == null ? null : borrowNid.trim();
    }

    public Integer getApplyUserId() {
        return applyUserId;
    }

    public void setApplyUserId(Integer applyUserId) {
        this.applyUserId = applyUserId;
    }

    public String getApplyUserName() {
        return applyUserName;
    }

    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName == null ? null : applyUserName.trim();
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getAgreementNumber() {
        return agreementNumber;
    }

    public void setAgreementNumber(Integer agreementNumber) {
        this.agreementNumber = agreementNumber;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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