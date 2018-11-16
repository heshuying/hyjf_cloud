package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class BorrowProjectType implements Serializable {
    private Integer id;

    /**
     * 汇直投 汇消费
     *
     * @mbggenerated
     */
    private String borrowProjectType;

    /**
     * 参数唯一标识
     *
     * @mbggenerated
     */
    private Integer borrowCd;

    /**
     * 名称
     *
     * @mbggenerated
     */
    private String borrowName;

    /**
     * 项目编号
     *
     * @mbggenerated
     */
    private String borrowClass;

    /**
     * 投资用户类型0:51老用户 1:新用户 2: 全部
     *
     * @mbggenerated
     */
    private Integer investUserType;

    /**
     * 状态
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 投资起始值
     *
     * @mbggenerated
     */
    private String investStart;

    /**
     * 投资最大值
     *
     * @mbggenerated
     */
    private String investEnd;

    /**
     * 备注
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * 建立用户id
     *
     * @mbggenerated
     */
    private Integer createUserId;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 建立用户id
     *
     * @mbggenerated
     */
    private Integer updateUserId;

    /**
     * 更新时间
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * 递增金额
     *
     * @mbggenerated
     */
    private Long increaseMoney;

    /**
     * 优惠券
     *
     * @mbggenerated
     */
    private Integer interestCoupon;

    /**
     * 体验金
     *
     * @mbggenerated
     */
    private Integer tasteMoney;

    /**
     * 是否加息标志位(0:否,1:是)
     *
     * @mbggenerated
     */
    private Integer increaseInterestFlag;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBorrowProjectType() {
        return borrowProjectType;
    }

    public void setBorrowProjectType(String borrowProjectType) {
        this.borrowProjectType = borrowProjectType == null ? null : borrowProjectType.trim();
    }

    public Integer getBorrowCd() {
        return borrowCd;
    }

    public void setBorrowCd(Integer borrowCd) {
        this.borrowCd = borrowCd;
    }

    public String getBorrowName() {
        return borrowName;
    }

    public void setBorrowName(String borrowName) {
        this.borrowName = borrowName == null ? null : borrowName.trim();
    }

    public String getBorrowClass() {
        return borrowClass;
    }

    public void setBorrowClass(String borrowClass) {
        this.borrowClass = borrowClass == null ? null : borrowClass.trim();
    }

    public Integer getInvestUserType() {
        return investUserType;
    }

    public void setInvestUserType(Integer investUserType) {
        this.investUserType = investUserType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getInvestStart() {
        return investStart;
    }

    public void setInvestStart(String investStart) {
        this.investStart = investStart == null ? null : investStart.trim();
    }

    public String getInvestEnd() {
        return investEnd;
    }

    public void setInvestEnd(String investEnd) {
        this.investEnd = investEnd == null ? null : investEnd.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getIncreaseMoney() {
        return increaseMoney;
    }

    public void setIncreaseMoney(Long increaseMoney) {
        this.increaseMoney = increaseMoney;
    }

    public Integer getInterestCoupon() {
        return interestCoupon;
    }

    public void setInterestCoupon(Integer interestCoupon) {
        this.interestCoupon = interestCoupon;
    }

    public Integer getTasteMoney() {
        return tasteMoney;
    }

    public void setTasteMoney(Integer tasteMoney) {
        this.tasteMoney = tasteMoney;
    }

    public Integer getIncreaseInterestFlag() {
        return increaseInterestFlag;
    }

    public void setIncreaseInterestFlag(Integer increaseInterestFlag) {
        this.increaseInterestFlag = increaseInterestFlag;
    }
}