package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class BorrowLog implements Serializable {
    private Integer id;

    /**
     * 借款编号
     *
     * @mbggenerated
     */
    private String borrowNid;

    /**
     * 项目状态
     *
     * @mbggenerated
     */
    private Integer borrowStatusCd;

    /**
     * 项目状态名称
     *
     * @mbggenerated
     */
    private String borrowStatus;

    /**
     * 修改类型-创建标的-新增-修改-删除
     *
     * @mbggenerated
     */
    private String type;

    /**
     * 备注
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * 创建用户Name
     *
     * @mbggenerated
     */
    private String createUserName;

    /**
     * 创建用户
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
     * 删除标识
     *
     * @mbggenerated
     */
    private Integer delFlag;

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

    public Integer getBorrowStatusCd() {
        return borrowStatusCd;
    }

    public void setBorrowStatusCd(Integer borrowStatusCd) {
        this.borrowStatusCd = borrowStatusCd;
    }

    public String getBorrowStatus() {
        return borrowStatus;
    }

    public void setBorrowStatus(String borrowStatus) {
        this.borrowStatus = borrowStatus == null ? null : borrowStatus.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName == null ? null : createUserName.trim();
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

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}